package com.sinacloud.java.kvdb;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;

/**
 * 提供给客户端使用的转换工具，由于服务端的多语言兼容性限制，java平台提供的
 * kv客户端只支持long，double，boolean，byte[]四种类型。
 * 但我们也提供了 SaeKVUtil 这样的转换工具，您可以使用这个工具对基本类型
 * 进行转换，SaeKVUtil 也提供了对象序列化和反序列化的方法
 * @author SAE
 *
 */
public class SaeKVUtil {

	/**
	 * 将byte类型转换为long类型，其高位转换过程中被置为0，不会进行符号位扩展
	 * @param value 转换前的值
	 * @return 转换后的long值
	 */
	public static long byteToLong(byte value) {
		long longValue = value & 0XFFL;
		return longValue;
	}
	
	/**
	 * 将short类型转换为long类型，其高位转换过程中被置为0，不会进行符号位扩展
	 * @param value 转换前的值
	 * @return 转换后的long值
	 */
	public static long shortToLong(short value) {
		long longValue = value & 0XFFFFL;
		return longValue;
	}
	
	/**
	 * 将char类型转换为long类型，其高位转换过程中被置为0，不会进行符号位扩展
	 * @param value 转换前的值
	 * @return 转换后的long值
	 */
	public static long charToLong(char value) {
		long longValue = value & 0XFFFFL;
		return longValue;
	}
	
	/**
	 * 将char类型转换为long类型，其高位转换过程中被置为0，不会进行符号位扩展
	 * @param value 转换前的值
	 * @return 转换后的long值
	 */
	public static long intToLong(int value) {
		long longValue = value & 0XFFFFFFFFL;
		return longValue;
	}
	
	/**
	 * 将long类型转换为byte类型，转换过程相当于截取long的最低一位
	 * @param value 转换前的值
	 * @return 转换后的byte值
	 */
	public static byte longToByte(Object value) {
		if(!(value instanceof Long)) {
			return (byte)0XFF;
		}
		long newValue = (Long)value;
		byte byteValue = (byte)(newValue & 0XFF);
		return byteValue;
	}
	
	/**
	 * 将long类型转换为byte类型，转换过程相当于截取long的最低两位
	 * @param value 转换前的值
	 * @return 转换后的short值
	 */
	public static short longToShort(Object value) {
		if(!(value instanceof Long)) {
			return (short)0XFFFF;
		}
		long newValue = (Long)value;
		short shortValue = (short)(newValue & 0XFFFF);
		return shortValue;
	}
	
	/**
	 * 将long类型转换为byte类型，转换过程相当于截取long的最低两位
	 * @param value 转换前的值
	 * @return 转换后的char值
	 */
	public static char longToChar(Object value) {
		if(!(value instanceof Long)) {
			return (char)0XFFFF;
		}
		long newValue = (Long)value;
		char charValue = (char)(newValue & 0XFFFF);
		return charValue;
	}
	
	/**
	 * 将long类型转换为byte类型，转换过程相当于截取long的最低四位
	 * @param value 转换前的值
	 * @return 转换后的int值
	 */
	public static int longToInt(Object value) {
		if(!(value instanceof Long)) {
			return -1;
		}
		long newValue = (Long)value;
		int intValue = (int)(newValue & 0XFFFFFFFF);
		return intValue;
	}
	
	/**
	 * 反序列化一个byte[]，将其转换为具体的对象类型，需要反序列化的对象
	 * 必须是byte[]，若不满足则返回null
	 * @param value 反序列化的内容
	 * @return 反序列化后的具体对象
	 * @throws IOException 反序列化过程中可能会抛出异常
	 * @throws ClassNotFoundException 反序列化过程中可能会抛出找不到类异常
	 */
	@SuppressWarnings("unchecked")
	public static <T> T deserializable(Object value) throws IOException, ClassNotFoundException {
		if(!(value instanceof byte[])) {
			return null;
		}
		byte[] buf = (byte[])value;
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		DeserializableObject ois = new DeserializableObject( new ByteArrayInputStream(buf),loader);
		Object obj = ois.readObject();
		if(ois != null) {
			ois.close();
		}
		return (T)obj;
	}
	
	/**
	 * 将一个对象序列化为byte[]
	 * @param value 需要序列化的对象
	 * @return 序列化后的byte[]
	 * @throws IOException 序列化过程中可能会抛出IO异常
	 */
	public static byte[] serializable(Object value) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		(new ObjectOutputStream( bos )).writeObject(value);
		return bos.toByteArray();
	}
	
	/**
	 * 将String类型转换为二进制数组
	 * @param value String字符串
	 * @return 二进制数组
	 */
	public static byte[] StringToByte(String value) {
		return value.getBytes();
	}
	
	/**
	 * 将二进制数组转换为String类型
	 * @param value 二进制数组
	 * @return String类型
	 */
	public static String byteToString(byte[] value) {
		return new String(value);
	}
	
	/**
	 * 将一个具体对象转换为String字符串，此具体对象必须是byte[]类型，
	 * 否则返回null
	 * @param obj 需要转换的对象
	 * @return 字符串
	 */
	public static String objToString(Object obj) {
		if(obj instanceof byte[]) {
			byte[] buf = (byte[])obj;
			return new String(buf);
		} else {
			return null;
		}
	}
	
	/**
	 * 反序列化字节流，使用一个指定的classloader序列化class流。
	 * @author SAE
	 *
	 */
	private static class DeserializableObject extends ObjectInputStream {
		private ClassLoader loader;
		DeserializableObject(InputStream in, ClassLoader loader) throws IOException {
			super(in);
			this.loader = loader;
		}
		@Override
		protected Class<?> resolveClass(ObjectStreamClass desc)throws IOException, ClassNotFoundException {
			return Class.forName( desc.getName(),true,loader );
		}
	}
	
}
