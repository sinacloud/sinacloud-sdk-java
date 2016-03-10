package com.sinacloud.java.kvdb.util;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.sinacloud.java.exception.BanCallException;


/**
 * 内部使用的转换工具
 * @author SAE
 *
 */
public class TransformUtil {

	/**
	 * 服务端保存的flag的值，1表示 long
	 */
	private static final byte[] LONG_FLAG = "1".getBytes();
	
	/**
	 * 服务端保存的flag的值，2表示 double
	 */
	private static final byte[] DOUBLE_FLAG = "2".getBytes();
	
	/**
	 * 服务端保存的flag的值，3表示boolean
	 */
	private static final byte[] BOOLEAN_FLAG = "3".getBytes();
	
	/**
	 * 服务端保存的flag的值，4表示 启用压缩
	 */
	private static final byte[] SERIALIZED_FLAG = "0".getBytes();	
	
	/**
	 * 默认的编码类型
	 */
	private static final String DEFAULT_ENCODE = "UTF-8";
	
	/**
	 * 最大的key长度为111字节(ak10字节，下划线一字节，用户key100字节)
	 */
	private static final int MAX_KEY_LENGTH = 100 + 1;
	
	/**
	 * 私有构造函数，禁止直接创建TransformUtil对象
	 */
	private TransformUtil() {
		throw new BanCallException("can not invoke TransformUtil constructor.");
	}
	
	/**
	 * 对指定的key进行编码，默认为UTF-8编码
	 * @param key 需要编码的key
	 * @return 编码后的key
	 * @throws UnsupportedEncodingException 不支持的编码类型异常
	 */
	public static String encodeKey(String key)throws UnsupportedEncodingException {
		return URLEncoder.encode( key, DEFAULT_ENCODE );
	}

	/**
	 * 对指定的key进行编码，默认为UTF-8编码
	 * @param key 需要编码的key
	 * @return 编码后的key
	 * @throws UnsupportedEncodingException 不支持的编码类型异常
	 */
	public static String encodeKey(byte[] key) throws UnsupportedEncodingException {
		return encodeKey( new String(key,DEFAULT_ENCODE) );
	}
	
	/**
	 * 对指定的key进行解码，默认为UTF-8编码
	 * @param key 需要解码的key
	 * @return 解码后的key
	 * @throws UnsupportedEncodingException 不支持的编码类型异常
	 */
	public static String decodeKey(String key)throws UnsupportedEncodingException {
		return URLDecoder.decode( key, DEFAULT_ENCODE );
	}	
	
	/**
	 * 根据qualifier的类型，返回一个具体的值
	 * @param type 类型
	 * @param content 需要转换的值
	 * @return 具体的值
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getContent(byte[] type, byte[] content) {
		if(type == null) {
			throw new RuntimeException("kvdb type error,type is null");
		}
		if(content == null) {
			throw new RuntimeException("kvdb type error,value is null");
		}
		
		byte b = type[0];		
		T result = null;
		if(b == LONG_FLAG[0]) {
			String str = new String(content);
			Long v = Long.parseLong(str);
			result = (T)v;
		}
		else if(b == DOUBLE_FLAG[0]) {
			String str = new String(content);
			Double v = Double.parseDouble(str);
			result = (T)v;
		}
		else if(b == BOOLEAN_FLAG[0]) {
			String str = new String(content);
			Boolean v = Boolean.parseBoolean(str);
			result = (T)v;
		}
		else if(b == SERIALIZED_FLAG[0]) {
			result = (T)content;
		}
		else {
			throw new RuntimeException("unsupported type");
		}
		return result;
	}
	
	/**
	 * 根据值的类型，返回一个相对应的type
	 * @param value 值
	 * @return 对应的类型
	 */
	public static byte[] getType(Object value) {
		if(value instanceof Long) {
			return LONG_FLAG;
		}
		else if(value instanceof Double) {
			return DOUBLE_FLAG;
		}
		else if(value instanceof Boolean) {
			return BOOLEAN_FLAG;
		}
		else if(value instanceof byte[]) {
			return SERIALIZED_FLAG ;
		}
		else {
			throw new RuntimeException("unsupported type");
		}
	}
	
	/**
	 * 将对应的值转换为byte[]类型
	 * @param value 需要转换的值
	 * @return 转换后的byte[]
	 */
	public static byte[] toBytes(Object value) {
		if(value instanceof Long) {
			Long longObject = (Long)value;
			return longObject.toString().getBytes();
		}
		else if(value instanceof Double) {
			Double doubleObject =(Double)value;
			return doubleObject.toString().getBytes();
		}
		else if(value instanceof Boolean) {
			Boolean booleanObject = (Boolean)value;
			return booleanObject.toString().getBytes();
		}
		else if(value instanceof byte[]) {
			byte[] buf = (byte[])value;
			return buf;
		}
		else {
			throw new RuntimeException("unsupported value type");
		}
	}
	
	/**
	 * 将字符串包装成ByteBuffer类型
	 * @param value
	 * @return bb
	 * @throws UnsupportedEncodingException 
	 */
	public static ByteBuffer wrap(String value) throws UnsupportedEncodingException {
		ByteBuffer bb = null;
		bb = ByteBuffer.wrap(value.getBytes("UTF-8"));
		return bb;
	}
	
	/**
	 * 将Map<String,String>包装成 Map<ByteBuffer,ByteBuffer>类型
	 * @param attributes
	 * @return wrappedAttributes
	 * @throws UnsupportedEncodingException 
	 */
	public static Map<ByteBuffer, ByteBuffer> encodeAttributes(Map<String, String> attributes) throws UnsupportedEncodingException {
		Map<ByteBuffer, ByteBuffer> wrappedAttributes = null;
		if (attributes != null && !attributes.isEmpty()) {
			wrappedAttributes = new HashMap<ByteBuffer, ByteBuffer>(1);
			for (Map.Entry<String, String> entry : attributes.entrySet()) {
				wrappedAttributes.put(wrap(entry.getKey()),
						wrap(entry.getValue()));
			}
		}
		return wrappedAttributes;
	}
	
	/**
	 * 返回一个空的属性包装
	 * @return map
	 * @throws UnsupportedEncodingException 
	 */
	public static Map<ByteBuffer,ByteBuffer> emptyAttributes() throws UnsupportedEncodingException {
		return encodeAttributes(new HashMap<String, String>());
	}
	
	/**
	 * 将ByteBuffer对象转换为byte[]
	 * @param buffer
	 * @return bytes
	 */
	public static byte[] decodeBuffer(ByteBuffer buffer) {
		byte[] bytes = new byte[buffer.limit()];
		for (int i = 0; i < buffer.limit(); i++) {
			bytes[i] = buffer.get();
		}
		return bytes;
	}
	
	/**
	 * 返回当前value的大小<br/>
	 * boolean 算作1个字节<br/>
	 * long 算作8个字节<br/>
	 * double 算作8个字节<br/>
	 * byte[]根据具体大小返回对于的字节数
	 * @param value
	 * @return int
	 */
	@SuppressWarnings("unused")
	private static int getValueSize(Object value) {
		if(value == null) {
			throw new RuntimeException("vlaue is null");
		}
		if(value instanceof Boolean) {
			return 4;
		}
		else if(value instanceof Double) {
			return 8;
		}
		else if(value instanceof Long) {
			return 8;
		}
		else if(value instanceof byte[]) {
			byte[] buf = (byte[])value;
			return buf.length;
		}
		else {
			throw new RuntimeException("unkonwn value type "+value.getClass().getName());
		}		
	}
	
	/**
	 * 获取当前key的大小(不包含accesskey前缀)
	 * @param key
	 * @return int
	 */
	@SuppressWarnings("unused")
	private static int getKeySize(String key) {
		if(key == null) {
			throw new RuntimeException("key is null");
		}
		return key.getBytes().length;
	}
	
	/**
	 * 根据start key生成stop key
	 * @param startKey
	 * @return keyBytes
	 */
	public static byte[] generateStopKey(ByteBuffer startKey) {
		byte[] keyBytes = new byte[MAX_KEY_LENGTH];
		int index = 0;
		byte[] startKeyBuf = decodeBuffer(startKey);
		for(;index<startKeyBuf.length;index++) {
			keyBytes[index] = startKeyBuf[index];
		}
		for(;index<keyBytes.length;index++) {
			keyBytes[index] = (byte) 0xFF;
		}
		return keyBytes;
	}
	
	/**
	 * 生成stop key，假设起始key为aaa,则stopkey是aab
	 * @param startKey
	 * @return bytes
	 */
	public static byte[] caclStopKey(String startKey) {
		try {
			return caclStopKey( TransformUtil.wrap(startKey) );
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	/**
	 * 生成stop key，假设起始key为aaa,则stopkey是aab
	 * @param startKey
	 * @return bytes
	 */
	public static byte[] caclStopKey(ByteBuffer startKey) {
		if(startKey==null) {
			throw new RuntimeException("start key is null");
		}
		byte[] keyBytes = decodeBuffer(startKey);
		java.math.BigInteger bi = new BigInteger(keyBytes);
		BigInteger value = bi.add(new BigInteger("1"));
		byte[] stopKey = value.toByteArray();
		return stopKey;
	}
	
	
	
	/**
	 * 将指定的内容进行加密，默认采用HmacSHA256方式加密，加密后再对内容进行Base64编码
	 * @param cryptoType 加密的类型
	 * @param content 需要加密的内容
	 * @param secretKey 加密所需的Key
	 * @return 加密后的数据
	 */
	public static String calcSignature(String content,String secretKey) {
        try {
			Mac mac = Mac.getInstance("HmacSHA256");
	        SecretKeySpec secret = new SecretKeySpec(secretKey.getBytes(),"HmacSHA256");
	        mac.init(secret);
	        byte[] digest = mac.doFinal(content.getBytes());
	        sun.misc.BASE64Encoder encode = new sun.misc.BASE64Encoder();
			return encode.encode(digest);
        } catch(Exception e) {
        	throw new RuntimeException(e.getMessage());  
        }
	}
	

	
}

