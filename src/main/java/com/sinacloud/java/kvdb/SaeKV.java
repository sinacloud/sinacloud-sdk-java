package com.sinacloud.java.kvdb;

//import inner.sina.sae.util.SaeSocketToken;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.hadoop.hbase.thrift.generated.IOError;
import org.apache.hadoop.hbase.thrift.generated.IllegalArgument;
import org.apache.log4j.Logger;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import com.sinacloud.java.exception.NotInitializeException;
import com.sinacloud.java.kvdb.util.AuthExpire;
import com.sinacloud.java.kvdb.util.KeyPair;
import com.sinacloud.java.kvdb.util.TIOError;
import com.sinacloud.java.kvdb.util.TItem;
import com.sinacloud.java.kvdb.util.TKvdbService;
import com.sinacloud.java.kvdb.util.TKvdbService.Client;
import com.sinacloud.java.kvdb.util.TransformUtil;

/**
 * kv客户端
 * 
 * <p>处于兼容性的考虑，可能会在未来提供java，php，python以及其他语言的互操作，
 * 由于各种语言的基本类型不一致，导致语言的互操作可能会出现问题，因此java平台的
 * kv客户端并没有提供所有java基本类型的保存方式，取而代之的是只有boolean，
 * long，double，byte[]四种类型。
 * 
 * <p>因为java语言的基本类型都是有符号的，我们提供了short-->long，int-->long等基本
 * 类型相互转化的工具类，以及对象序列化和反序列化的方法。
 * 
 * <p>假设您需要保存一个Person对象到服务端，您可以使用如下操作:
 * <pre>
 * Person person = new Person();
 * SaeKV kv = new SaeKV();
 * kv.init();
 * kv.set("p", SaeKVUtil.serializable(person));
 * </pre>
 * 
 * 当需要获取Person值时可以使用如下操作:
 * <pre>
 * Person person = SaeKVUtil.deserializable(kv.get("p"));
 * </pre>
 * 
 * java语言中int转换为long如果是有符号数，会执行一个符号位扩展，所以当您在保持
 * int类型值的时候(其他类型值，如short,byte也会有这种问题)如果需要忽略符号位扩展，
 * 可以使用我们提供的方法，使用如下操作:
 * <pre>
 * SaeKV kv = new SaeKV();
 * kv.set("aa", SaeKVUtil.intToLong(9527));
 * </pre>
 * 
 * 当需要获取一个int类型可以使用如下操作:
 * <pre>
 * int x = SaeKVUtil.longToInt(kv.get("aa"));
 * </pre>
 * <p/>
 * <strong>注意</strong>，对象类型最终是以byte[]作为参数传给SaeKV的，而String类型，图片，也是以byte[]作为参数的。<br/>
 * 所以这里会有一个问题，如果将String或者图片的byte[]反序列化则会抛出异常，因为它不是一个合法的java序列化内容<br/>
 * 考虑如下内容:<br/>
 * <pre>
 * String s = "abc";
 * byte[] buf = SaeKVUtil.StringToByte(s); 
 * </pre>
 * SaeKVUtil.StringToByte()返回的是一个byte[]内容，如果查看这个byte[]数组，内容是:[97, 98, 99]<br/>
 * 也就是a,b,c的ascii码。<br/>
 * 如果是调用SaeKVUtil.serializable()保存一个String类型，它的返回结果也是byte[]类型，但是结果跟之前就完全不对了:<br/>
 * [-84, -19, 0, 5, 116, 0, 3, 97, 98, 99]<br/>
 * 这个byte[]里面包含了一些java对象特有的信息，自然如果反序列化SaeKVUtil.deserializable()结果肯定就不对了。
 * 所以当保存一个String类型，图片，或者是其他二进制格式文件时，请和java对象类型区分开，SaeKV在保存它们时会
 * 统一当做byte[]保存，从KVDB中获取到的也是byte[]类型，需由用户自行确认保存的是对象类型还是byte[]类型。
 * 
 * @author SAE
 *
 */
public class SaeKV {
	
	/**
	 * 一个空对象，有些操作不需要value，于是封装键值对的时候就使用一个空对象
	 */
	private static final Object EMPTY_VALUE = new Object();
	
	/**
	 * 默认的rget查询的数量
	 */
	private static final int DEFAULT_RGET_QUERY_COUNT = 10;
	
	/**
	 * rget查询的最大数量
	 */
	private static final int MAX_RGET_QUERY_COUNT = 512;
	
	/**
	 * 默认的rdel 删除的数量
	 */
	private static final int DEFAULT_RDEL_DELETE_COUNT = 10;
	
	/**
	 * rdel删除的最大数量
	 */
	private static final int MAX_RDEL_DELETE_COUNT = 512;
	
	/**
	 * 默认的pkrget查询数量
	 */
	private static final int DEFAULT_PKRGET_QUERY_COUNT = 10;
	
	/**
	 * pkrget查询的最大数量
	 */
	private static final int MAX_PKRGET_QUERY_COUNT = 100;	
	
	/**
	 * 最大的value大小为4M
	 */
	private static final int MAX_VALUE_SIZE = 4 * 1024 * 1024;
	
	/**
	 * 日志
	 */
	private static final Logger logger = Logger.getLogger(SaeKV.class.getName());
	
	/**
	 * 默认的kv 初始化地址
	 */
//	private static final String KV_ADDR_LIST = SaeKvdbHbaseConfig.getKvdbAddress();
	private static final String KVDB_SERVERS = System.getenv("KVDB_SERVERS");
	
	/**
	 * 默认的kv 初始化端口
	 */
//	private static final int KV_PORT = Integer.parseInt(SaeKvdbHbaseConfig.getKvdbPort());
//	private static final int KV_PORT = Integer.parseInt(System.getenv("KVDB_PORT"));
	private static int KV_PORT ;
	
	/**
	 * 最终要使用的thrift服务器的地址列表，这个列表将会被随机打散
	 */
	private static final List<String> KV_ADDRS;
	
	/**
	 * 用于遍历KV_ADDRS的下标
	 */
	private static final AtomicInteger KV_ADDRS_INDEX = new AtomicInteger(0);
	
	/**
	 * 成功的返回码
	 */
	private static final int SUCCESS_CODE = 0;
	
	/**
	 * 错误的返回码
	 */
	private static final int ERR_CODE = -1;
	
	/**
	 * 成功的返回消息
	 */
	private static final String SUCCESS_MSG = "success";
	
	/**
	 * 用户的ak
	 */
	private final String accessKey;
	
	/**
	 * 用户的sk
	 */
	private final String secretKey;
	
	/**
	 * 是否初始化
	 */
	private boolean initialized = false;
	
	/**
	 * 返回给用户的错误码
	 */
	private int errCode;
	
	/**
	 * 返回给用户的错误信息
	 */
	private String errMsg;	
	
	/**
	 * 对KeyPair token的简单封装
	 */
	private final Token token;

	
	/**
	 * 静态代码块内初始化表名，value:v，value:f的ByteBuffer值，以及kvdb的远端ip和端口等信息
	 * 一旦初始化出错则直接抛错，正常情况下是不会出现错误的，除非配置文件有误
	 */
	static {
		try {
			String[] addrs = KVDB_SERVERS.split(",");
			List<String> list = new ArrayList<String>();
			for(String s : addrs) {
				list.add(s.split(":")[0]);
				KV_PORT = Integer.parseInt(s.split(":")[1]);
			}
			java.util.Collections.shuffle(list);
			KV_ADDRS = new CopyOnWriteArrayList<String>();
			KV_ADDRS.addAll(list);
			Random rand = new Random();
			KV_ADDRS_INDEX.addAndGet(rand.nextInt(addrs.length));
		} catch(Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		
	}
		
	/**
	 * 默认构造函数自动设置accesskey无需手动传入
	 */
	public SaeKV() {
//		this.accessKey = SaeUserInfo.getAccessKey();
//		this.secretKey = SaeUserInfo.getSecretKey();
		this.accessKey = System.getenv("ACCESSKEY");
		this.secretKey = System.getenv("SECRETKEY");
		token = new Token(accessKey, secretKey);
	}
	

	/**
	 * 提供一个可以自定义ak和sk的构造函数
	 * @param accesskey
	 * @param secretkey
	 */
	public SaeKV(String accesskey, String secretkey) {
		this.accessKey = accesskey;
		this.secretKey = secretkey;
		token = new Token(accesskey, secretkey);
	}
	
	
	/**
	 * 初始化kv客户端
	 */
	public synchronized void init() {
		initialized = true;
		auth();
	}

	/**
	 * 用户检查是否初始化
	 */
	private void checkInitialized() {
		if(!initialized) {
			throw new NotInitializeException("please invoke init() method.");
		}
	}
	

	/**
	 * 对用户传入的指定的key增加accesskey_ 前缀
	 * @param key
	 * @return
	 */
	private String addAccessKeyPrefix(String key) {
		return key;
	}

	/**
	 * 将accesskey_前缀去掉
	 * @param key
	 * @return
	 */
	private String removeAccessKeyPrefix(String key) {
		return key;
	}
	
	/**
	 * 认证
	 */
	private void auth() {
		execute(new AuthExecutor());
	}
	
	/**
	 * key value的简单封装
	 * @author SAE
	 *
	 */
	private class KeyValue {
		final String key;
		final Object value;
		public KeyValue(String key,Object value) {
			if(key == null) {
				throw new RuntimeException("key is null");
			}
			if(value == null) {
				throw new RuntimeException("value is null");
			}			
			this.key = key;
			this.value = value;
		}
		@Override
		public String toString() {
			return "key="+key+"\tvalue="+value.toString();
		}
	}
	
	/**
	 * 对KeyPair token的简单封装
	 * @author SAE
	 *
	 */
	@SuppressWarnings("unused")
	private class Token {
		private final String accesskey;
		private final String secretkey;
		private String token;
		private String ip;
		
		public Token(String accesskey,String secretkey) {
			this.accesskey = accesskey;
			this.secretkey = secretkey;
		}

		public String getToken() {
			return token;
		}

		public void setToken(String token) {
			this.token = token;
		}

		public String getIp() {
			return ip;
		}

		public void setIp(String ip) {
			this.ip = ip;
		}
		
	}
	
	/**
	 * 几种复杂查询的类型，如pkrget，rget，rdel
	 * @author SAE
	 *
	 */
	private enum ScanType {
		PKRGET,
		RGET,
		RDEL;
	}
	
	/**
	 * 内部接口，用于执行回调函数，各种操作如set，get，delete，pkrget等函数会在其函数内部创建一个匿名内部类，
	 * 然后执行executor()函数
	 * 
	 * @author SAE
	 *
	 */
	private interface ThriftExecutor {
		/**
		 * 执行回调的函数，由继承的类执行TKvdbService.Client对象，这个函数主要用于返回或者执行单个KeyValue
		 * @param kp 
		 * @param client
		 * @return
		 * @throws IllegalArgument 
		 * @throws AuthExpire 
		 * @throws TIOError 
		 */
		public abstract <T> T executor(KeyPair kp,TKvdbService.Client client, ByteBuffer row, Object value)
		throws IOError,TException, IllegalArgument, TIOError, AuthExpire;
		
		/**
		 * 执行回调的函数，由继承的类执行TKvdbService.Client对象，这个函数主要用于执行或者返回多个KeyValue对象
		 * @param kp 
		 * @param client
		 * @param values
		 * @return
		 * @throws IOError
		 * @throws TException
		 * @throws IllegalArgument
		 * @throws AuthExpire 
		 * @throws TIOError 
		 */
		public abstract <T> T executor(KeyPair kp,TKvdbService.Client client, Map<ByteBuffer, Object> values)
		throws IOError,TException, IllegalArgument, TIOError, AuthExpire;
			
		/**
		 * 执行回调的函数，由继承的类执行TKvdbService.Client对象，这个函数主要用于执行SCAN操作，设计到起始key，结束key，前缀查询等，操作的
		 * 对象也是多个KeyValue
		 * @param kp 
		 * @param client
		 * @param startAndPrefix
		 * @param stopKey
		 * @param count
		 * @param isContainValue
		 * @param isContainStartVlaue
		 * @param isContainEndValue
		 * @param compareKey
		 * @return
		 * @throws IOError
		 * @throws TException
		 * @throws IllegalArgument
		 * @throws AuthExpire 
		 * @throws TIOError 
		 */
		public abstract <T> T executor(KeyPair kp,TKvdbService.Client client, ByteBuffer startAndPrefix, ByteBuffer stopKey,
		int count, boolean isContainValue, boolean isContainStartVlaue, boolean isContainEndValue, 
		ByteBuffer compareKey)throws IOError,TException, IllegalArgument, TIOError, AuthExpire;		
		
	}
	
	
	/**
	 * 用于执行回调函数的模板抽象类，主要用于在执行executor函数时候，做一些例行的工作，比如判空，编码key，捕获异常等
	 * 由于各种操作的返回值不同，get返回对象类型，set返回boolean类型，所以根据返回的类型有不同的实现
	 * @author SAE
	 *
	 */
	private abstract class AbstractThriftExecutor implements ThriftExecutor {
		/**
		 * 执行一些例行的操作，编码key，捕获异常等
		 * @param client
		 * @param key
		 * @return
		 */
		public abstract <T> T executorTemplate(TKvdbService.Client client);

		/**
		 * 增加一个未实现的骨架函数，这样具体实现不用所有的函数都实现了
		 * @throws AuthExpire 
		 * @throws TIOError 
		 */
		public <T> T executor(KeyPair kp,TKvdbService.Client client,ByteBuffer row,Object value)
		throws IOError,TException, IllegalArgument, TIOError, AuthExpire {			
			throw new RuntimeException("Unsupported");
		}
		
		/**
		 * 增加一个未实现的骨架函数，这样具体实现不用所有的函数都实现了
		 * @throws AuthExpire 
		 * @throws TIOError 
		 */
		public <T> T executor(KeyPair kp,TKvdbService.Client client, Map<ByteBuffer,Object> values)
		throws IOError,TException, IllegalArgument, TIOError, AuthExpire {
			throw new RuntimeException("Unsupported");
		}
		
		/**
		 * 增加一个未实现的骨架函数，这样具体实现不用所有的函数都实现了
		 * @throws AuthExpire 
		 * @throws TIOError 
		 */
		public <T> T executor(KeyPair kp,TKvdbService.Client client, ByteBuffer startAndPrefix, ByteBuffer stopKey,
		int count, boolean isContainValue, boolean isContainStartVlaue, boolean isContainEndValue, 
		ByteBuffer compareKey)throws IOError,TException, IllegalArgument, TIOError, AuthExpire {
			throw new RuntimeException("Unsupported");
		}	
		
	}
	
	/**
	 * 认证类，通过ak和sk以及时间戳生成签名并封装KeyPair发送给服务端
	 * 签名的内容是 accesskey_[timestamp]
	 * 最后服务端会返回一个token，10分钟后这个token会自动失效需要重现链接，这个链接过程由客户端自己完成用户无需手动认证
	 * @author SAE
	 *
	 */
	private class AuthExecutor extends AbstractThriftExecutor {
		@Override
		public <T> T executorTemplate(Client client) {
			KeyPair kp = new KeyPair();
			long timestamp = System.currentTimeMillis();
			String content = accessKey +"-"+ timestamp;
			String signature = TransformUtil.calcSignature(content, secretKey);
			kp.setAccesskey(accessKey);
			kp.setTimestamp(timestamp);
			kp.setSignature(signature);
			
			try {
				kp = client.auth(kp);
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
			token.setToken(kp.getToken());
			return null;
		}
		
	}
	
	/**
	 * 返回单个结果的操作，用于get，delete函数
	 * @author SAE
	 *
	 */
	private abstract class SingleExecutor extends AbstractThriftExecutor {
		final KeyValue kv;
		
		public SingleExecutor(KeyValue kv) {
			this.kv = kv;
		}

		/**
		 * 执行一个具体的操作，会由SaeKV的execut函数调用到
		 */
		@Override
		public <T> T executorTemplate(TKvdbService.Client client) {
			T result = null;
			ByteBuffer row = null;
			try {
				String encodeKey = TransformUtil.encodeKey(kv.key);
				row = TransformUtil.wrap( addAccessKeyPrefix(encodeKey) );
			} catch (UnsupportedEncodingException e) {
				logger.error("encode key failure.",e);
				recordErrCodeAndMsg(ERR_CODE,e.getMessage());
				return null;
			}
			
			try {
				try {
					KeyPair kp = new KeyPair();
					kp.setToken(token.getToken());
					result = executor(kp,client,row,kv.value);
				} catch(AuthExpire e) {
					auth();
					KeyPair kp = new KeyPair();
					kp.setToken(token.getToken());
					result = executor(kp,client,row,kv.value);
				}
				recordErrCodeAndMsg(SUCCESS_CODE, SUCCESS_MSG);
			} catch (IOError e) {
				logger.error(e.getMessage(),e);
				recordErrCodeAndMsg(ERR_CODE,e.getMessage());
			} catch (TException e) {
				logger.error(e.getMessage(),e);
				recordErrCodeAndMsg(ERR_CODE,e.getMessage());
			} catch (IllegalArgument e) {
				logger.error(e.getMessage(),e);
				recordErrCodeAndMsg(ERR_CODE,e.getMessage());
			} catch(Exception e) {
				logger.error(e.getMessage(),e);
				recordErrCodeAndMsg(ERR_CODE,e.getMessage());
			}
			return result;
		}
	}
	
	
	/**
	 * 返回批量执行的结果，用于getMulti函数
	 * @author SAE
	 *
	 */
	private abstract class BatchExecutor extends AbstractThriftExecutor {
		final List<KeyValue> kvs;
		
		public BatchExecutor(List<KeyValue> kvs) {
			if(kvs == null) {
				throw new RuntimeException("keys and values is null");
			}
			this.kvs = kvs;
		}

		/**
		 * 执行一个具体的操作，会由SaeKV的execut函数调用到
		 */
		@Override
		public <T> T executorTemplate(TKvdbService.Client client) {
			Map<ByteBuffer,Object> map = new HashMap<ByteBuffer, Object>();
			T result = null;
			ByteBuffer row = null;
			try {
				for(KeyValue k : kvs) {
					String encodeKey = TransformUtil.encodeKey(k.key);
					row = TransformUtil.wrap( addAccessKeyPrefix(encodeKey) );
					map.put(row, k.value);
				}
			} catch (UnsupportedEncodingException e) {
				logger.error("encode key failure.",e);
				recordErrCodeAndMsg(ERR_CODE,e.getMessage());
				return null;
			}
			
			try {
				try {
					KeyPair kp = new KeyPair();
					kp.setToken(token.getToken());
					result = executor(kp,client,map);
				} catch(AuthExpire e) {
					auth();
					KeyPair kp = new KeyPair();
					kp.setToken(token.getToken());
					result = executor(kp,client,map);
				}
				recordErrCodeAndMsg(SUCCESS_CODE, SUCCESS_MSG);
			} catch (IOError e) {
				logger.error(e.getMessage(),e);
				recordErrCodeAndMsg(ERR_CODE,e.getMessage());
			} catch (TException e) {
				logger.error(e.getMessage(),e);
				recordErrCodeAndMsg(ERR_CODE,e.getMessage());
			} catch (IllegalArgument e) {
				logger.error(e.getMessage(),e);
				recordErrCodeAndMsg(ERR_CODE,e.getMessage());
			} catch(Exception e) {
				logger.error(e.getMessage(),e);
				recordErrCodeAndMsg(ERR_CODE,e.getMessage());
			}
			return result;
		}
	}
	
				
		
	/**
	 * 执行范围查询，用户rget，pkrget等操作
	 * @author SAE
	 *
	 */
	private abstract class ScanExecutor extends AbstractThriftExecutor {
		final String prefixKey;
		final int count;
		final boolean isContainValue;
		final String compareKey;
		final String startKey;
		final String endKey;
		final boolean isContainStartVlaue;
		final boolean isContainEndValue;
		final ScanType type;
		
		/**
		 * 构造函数会根据传递的ScanType类型判断，分别初始化rget，pkrget，或者rdel相关的参数
		 * @param prefixKey
		 * @param count
		 * @param isContainValue
		 * @param compareKey
		 * @param startKey
		 * @param endKey
		 * @param isContainStartVlaue
		 * @param isContainEndValue
		 * @param type
		 */
		public ScanExecutor(String prefixKey,int count,boolean isContainValue,String compareKey,
		String startKey,String endKey,boolean isContainStartVlaue,boolean isContainEndValue,ScanType type) {
			this.type = type;
			switch(type) {
				case PKRGET : {
					if(prefixKey==null || "".equals(prefixKey)) {
						throw new IllegalArgumentException("prefixkey is null");
					}
					this.prefixKey = prefixKey;
					if(count > MAX_PKRGET_QUERY_COUNT) {
						this.count = MAX_PKRGET_QUERY_COUNT;
					} else {
						this.count = count;
					}
					if(compareKey==null || "".equals(compareKey)) {
						throw new IllegalArgumentException("compare key is null");
					}
					this.compareKey = compareKey;
					this.isContainValue = isContainValue;
					this.startKey=startKey;
					this.endKey = endKey;
					this.isContainStartVlaue=isContainStartVlaue;
					this.isContainEndValue=isContainEndValue;
					break;
				}
				case RGET : {
					if(startKey==null || "".equals(startKey)) {
						throw new IllegalArgumentException("start key is null");
					}
					this.startKey = startKey;
					if(endKey==null || "".equals(endKey)) {
						throw new IllegalArgumentException("end key is null");
					}
					this.endKey = endKey;
					this.isContainStartVlaue = isContainStartVlaue;
					this.isContainEndValue = isContainEndValue;
					if(count > MAX_RGET_QUERY_COUNT) {
						this.count = MAX_RGET_QUERY_COUNT;
					} else {
						this.count = count;
					}
					this.prefixKey=prefixKey;
					this.compareKey=compareKey;
					this.isContainValue=isContainValue;				
					break;
				}
				case RDEL : {
					if(startKey==null || "".equals(startKey)) {
						throw new IllegalArgumentException("start key is null");
					} 
					this.startKey = startKey;
					if(endKey==null || "".equals(endKey)) {
						throw new IllegalArgumentException("end key is null");
					}
					this.endKey = endKey;
					this.isContainStartVlaue = isContainStartVlaue;
					this.isContainEndValue = isContainEndValue;
					if(count > MAX_RDEL_DELETE_COUNT) {
						this.count = MAX_RDEL_DELETE_COUNT;
					} else {
						this.count = count;
					}
					this.prefixKey=prefixKey;
					this.compareKey=compareKey;
					this.isContainValue=isContainValue;
					break;
				}
				default : {
					throw new RuntimeException("unkonw scan type");
				}
			}//end switch
		}
				
		/**
		 * 执行一个具体的操作，会由SaeKV的execut函数调用到
		 */
		@Override
		public <T> T executorTemplate(Client client) {
			T result = null;
			ByteBuffer startAndPrefix = null;
			ByteBuffer stopKey = null;
			ByteBuffer compKey = null;
			
			try {		
				switch(type) {
					case PKRGET: {
						String encodeKey = TransformUtil.encodeKey(this.prefixKey);
						startAndPrefix = TransformUtil.wrap( addAccessKeyPrefix(encodeKey) );
						String compKeyStr = TransformUtil.encodeKey( this.compareKey );
						compKey = TransformUtil.wrap( addAccessKeyPrefix(compKeyStr) );
						break;
					}
					case RGET: {
						String encodeKey = TransformUtil.encodeKey(this.startKey);
						startAndPrefix = TransformUtil.wrap( addAccessKeyPrefix(encodeKey) );
						encodeKey = TransformUtil.encodeKey(this.endKey);
						stopKey = TransformUtil.wrap( addAccessKeyPrefix(encodeKey) );
						break;
					}
					case RDEL: {
						String encodeKey = TransformUtil.encodeKey(this.startKey);
						startAndPrefix = TransformUtil.wrap( addAccessKeyPrefix(encodeKey) );
						encodeKey = TransformUtil.encodeKey(this.endKey);
						stopKey = TransformUtil.wrap( addAccessKeyPrefix(encodeKey) );
					}
					default: {
						break;
					}
				}	
			} catch (UnsupportedEncodingException e) {
				logger.error("encode key failure.",e);
				recordErrCodeAndMsg(ERR_CODE,e.getMessage());
				return null;
			}
			
			try {
				try {
					KeyPair kp = new KeyPair();
					kp.setToken(token.getToken());
					result = executor(kp,client, startAndPrefix, stopKey, count, isContainValue, 
						isContainStartVlaue, isContainEndValue, compKey);
				} catch(AuthExpire e) {
					auth();
					KeyPair kp = new KeyPair();
					kp.setToken(token.getToken());
					result = executor(kp,client, startAndPrefix, stopKey, count, isContainValue, 
						isContainStartVlaue, isContainEndValue, compKey);
				}
				recordErrCodeAndMsg(SUCCESS_CODE, SUCCESS_MSG);
			} catch (IOError e) {
				logger.error(e.getMessage(),e);
				recordErrCodeAndMsg(ERR_CODE,e.getMessage());
			} catch (TException e) {
				logger.error(e.getMessage(),e);
				recordErrCodeAndMsg(ERR_CODE,e.getMessage());
			} catch (IllegalArgument e) {
				logger.error(e.getMessage(),e);
				recordErrCodeAndMsg(ERR_CODE,e.getMessage());
			} catch(Exception e) {
				logger.error(e.getMessage(),e);
				recordErrCodeAndMsg(ERR_CODE,e.getMessage());				
			}
			return result;
		}		
	}
	
	
	/**
	 * 核心的执行函数，用于初始化，关闭连接等，并回调传入的ThriftExecutor对象<br/>
	 * 这个函数会被get，set，rget,pkrget,rdel等所有执行操作的函数调用
	 * 比如set或者get函数，会传入一个ThriftExecutor接口的实现，一个匿名内部类，和一个key<br/>
	 * 这个函数中会根据传入的AbstractThriftExecutor实现的不同，调用到不同的类
	 * 对于返回boolean类型的操作，比如delete，set等操作，会调用到SingleExecutor这个抽象类
	 * <p/>
	 * 调用顺序如下:
	 * 1.set()或者get()函数<br/>
	 *       |<br/>
	 *       |<br/>
	 * 2.execute(ThriftExecutor executor,String key)函数<br/>
	 *       |<br/>
	 *       |<br/>
	 * 3.AbstractThriftExecutor的具体实现类,set和set会调用SingleExecutor，getMulit会调用到 BatchExecutor<br/>
	 *       |<br/>
	 *       |<br/>
	 * 4.AbstractThriftExecutor的具体实现类，执行一些初始判断的操作和异常捕获的工作<br/>
	 *       |<br/>
	 *       |<br/>
	 * 5.get或者set函数中的executor()执行内部类操作逻辑<br/>            
	 *            
	 * 
	 * @param executor
	 * @return
	 */
	private <T> T execute(AbstractThriftExecutor executor)  {
		checkInitialized();
		//inner.sina.sae.util.SaeSocketToken.setSocketPrivillegeToken(true); 
		String ip = token.getIp();
		if(ip == null) {
			connectWithAuth();
			ip = token.getIp();
		}
		TTransport transport = new TSocket(ip, KV_PORT);
		try {
			transport.open();
		} catch (TTransportException e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e.getMessage());
		}
		TProtocol protocol = new TBinaryProtocol(transport);
		TKvdbService.Client client = new TKvdbService.Client(protocol);
		T t= executor.executorTemplate(client);
		transport.close();
		//inner.sina.sae.util.SaeSocketToken.removeSocketPrivillegeToken();
		return t;
	}
	
	/**
	 * 定时器中存放的是某个暂时不可用的ip地址，如果超过30分钟则将这个ip地址恢复重新放到ip列表中
	 * @author SAE
	 *
	 */
	private class UnconnAddrTimer extends TimerTask {
		private final String ip;
		
		public UnconnAddrTimer(String ip) {
			this.ip = ip;
		}
		
		public void run() {
			KV_ADDRS.add(ip);
		}
	}
	
	/**
	 * 创建链接，会在ip中便利所有可用的ip直到找到一个合适的ip并创建连接，如果所有的ip都不可用则抛出异常</br>
	 * 对于一个ip会尝试连接三次，如果不成功则将这个ip删除并放到一个定时器中，定时器的执行时间是30分钟</br>
	 * 如果所有步骤执行成功则返回TTransport对象
	 * @return
	 */
	public KeyValue createConn() {
		TTransport transport = null;
		String ip = null;
		for(int i=0;i<KV_ADDRS.size();i++) {
			ip = KV_ADDRS.get(Math.abs(KV_ADDRS_INDEX.incrementAndGet()) % KV_ADDRS.size());
			transport = new TSocket(ip, KV_PORT);
			for(int retry=0;retry<3;retry++) {
				try {
					transport.open();
					break;
				} catch (TTransportException e) {
					String unConnIp = KV_ADDRS.remove(i);
					new Timer().schedule(new UnconnAddrTimer(unConnIp), 30*60*1000);
					logger.error(e.getMessage(), e);				
				}
			}
			if(transport.isOpen()) {
				break;
			}
		}
		if(transport==null || ip==null) {
			throw new RuntimeException("cannot create connection");
		}
		KeyValue kv = new KeyValue(ip, transport);
		return kv;
	}
	
	/**
	 * 带认证的连接，连接之后会将token和服务端的ip关联，下次用token获取ip直到这个token失效。
	 */
	private void connectWithAuth() {
		KeyValue kv = createConn();
		String ip = kv.key;
		TTransport transport = (TTransport)kv.value;
		TProtocol protocol = new TBinaryProtocol(transport);
		TKvdbService.Client client = new TKvdbService.Client(protocol);
		
		KeyPair kp = new KeyPair();
		long timestamp = System.currentTimeMillis();
		String content = accessKey +"-"+ timestamp;
		String signature = TransformUtil.calcSignature(content, secretKey);
		kp.setAccesskey(accessKey);
		kp.setTimestamp(timestamp);
		kp.setSignature(signature);
		
		try {
			kp = client.auth(kp);			
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		token.setToken(kp.getToken());
		transport.close();
		token.setIp(ip);
	}
	
	
	/**
	 * 删除一个指定的值
	 * @param key 需要删除的数据key
	 * @return 是否删除成功
	 */
	@SuppressWarnings("unchecked")
	public boolean delete(final String key) {
		Object result = execute(new SingleExecutor(new KeyValue(key,EMPTY_VALUE)) {			
			@Override
			public Boolean executor(KeyPair kp,Client client, ByteBuffer row, Object value) throws IOError,TException, TIOError, AuthExpire {
				client.deleteItem(kp, row);
				return Boolean.TRUE;
			}
		});
		if(result == null) {
			return false;
		}
		//TransformUtil.caclInputOutputSize(key, result);
		return (Boolean)result;
	}

	/**
	 * 删除一个指定的值，如果指定了过期时间，则在指定的过期时间内，客户端无法对指定的
	 * key调用get，add，replace操作，但是get操作是可以的。
	 * @param key 需要删除的数据key
	 * @param expiry 过期时间
	 * @deprecated 这个方法已经过时，建议直接使用delete(String key)
	 * @return 是否删除成功
	 */
	@Deprecated
	public boolean delete(String key, Date expiry) {
		return delete(key);
	}

	
	/**
	 * 获取一个指定的值，如果指定的值不存在则返回null
	 * @param key 需要获取值的key
	 * @return 返回的值
	 */
	public <T> T get(String key) {
		T result = execute(new SingleExecutor(new KeyValue(key,EMPTY_VALUE)) {
			@SuppressWarnings({ "hiding", "unchecked" })
			@Override
			public <T> T executor(KeyPair kp,Client client, ByteBuffer row, Object value)
			throws IOError, TException, IllegalArgument, TIOError, AuthExpire {
				TItem item = client.getItem(kp, row);
				byte[] flagBytes = item.getFlag();
				byte[] content = item.getValue();
				return (T)TransformUtil.getContent(flagBytes, content);			
			}
		});
		if(result == null) {
			return null;
		}
		//TransformUtil.caclInputOutputSize(key, result);		
		return result;
	}
	
	
	
	
	
	/**
	 * 保存一个指定的值，不管指定的key在服务端是否存在都会被保存
	 * @param key 指定的key
	 * @param value 需要保存的值
	 * @return 是否保存成功
	 */
	public boolean set(String key, boolean value) {
		return storeVlaue(key,value);
	}

	/**
	 * 保存一个指定的值，不管指定的key在服务端是否存在都会被保存，
	 * 过期时间是一个以当前时间为准的偏移量，如果设置为10，表示经过
	 * 10秒后客户端无法获取此数据。
	 * @param key 指定的key
	 * @param value 需要保存的值
	 * @param expiry 过期时间
	 * @deprecated 此方法已过时，建议不要使用过期时间
	 * @return 是否保存成功
	 */
	@Deprecated
	public boolean set(String key, boolean value, long expiry) {
		return set(key,value);
	}

	/**
	 * 保存一个指定的值，不管指定的key在服务端是否存在都会被保存
	 * @param key 指定的key
	 * @param value 需要保存的值
	 * @return 是否保存成功
	 */
	public boolean set(String key, byte[] value) {
		return storeVlaue(key,value);
	}

	/**
	 * 保存一个指定的值，不管指定的key在服务端是否存在都会被保存，
	 * 过期时间是一个以当前时间为准的偏移量，如果设置为10，表示经过
	 * 10秒后客户端无法获取此数据。
	 * @param key 指定的key
	 * @param value 需要保存的值
	 * @param expiry 过期时间
	 * @deprecated 此方法已过时，建议不要使用过期时间
	 * @return 是否保存成功
	 */
	@Deprecated
	public boolean set(String key, byte[] value, long expiry) {
		return set(key,value);
	}

	/**
	 * 保存一个指定的值，不管指定的key在服务端是否存在都会被保存
	 * @param key 指定的key
	 * @param value 需要保存的值
	 * @return 是否保存成功
	 */
	public boolean set(String key, double value) {
		return storeVlaue(key,value);
	}

	/**
	 * 保存一个指定的值，不管指定的key在服务端是否存在都会被保存，
	 * 过期时间是一个以当前时间为准的偏移量，如果设置为10，表示经过
	 * 10秒后客户端无法获取此数据。
	 * @param key 指定的key
	 * @param value 需要保存的值
	 * @param expiry 过期时间
	 * @deprecated 此方法已过时，建议不要使用过期时间
	 * @return 是否保存成功
	 */
	@Deprecated
	public boolean set(String key, double value, long expiry) {
		return set(key,value);
	}

	/**
	 * 保存一个指定的值，不管指定的key在服务端是否存在都会被保存
	 * @param key 指定的key
	 * @param value 需要保存的值
	 * @return 是否保存成功
	 */
	public boolean set(String key, long value) {
		return storeVlaue(key,value);
	}

	/**
	 * 保存一个指定的值，不管指定的key在服务端是否存在都会被保存，
	 * 过期时间是一个以当前时间为准的偏移量，如果设置为10，表示经过
	 * 10秒后客户端无法获取此数据。
	 * @param key 指定的key
	 * @param value 需要保存的值
	 * @param expiry 过期时间
	 * @deprecated 此方法已过时，建议不要使用过期时间
	 * @return 是否保存成功
	 */
	@Deprecated
	public boolean set(String key, long value, long expiry) {
		return set(key,value);
	}
	
	
	private void checkValueSize(byte[] value) {
		if(value!=null && value.length>MAX_VALUE_SIZE) {
			throw new RuntimeException("value size must be less than 4M");
		}
	}
	
	
	/**
	 * 保存一个指定的值，支持long,double,byte[],boolean四种类型
	 * @param key
	 * @param value
	 * @return
	 */
	private boolean storeVlaue(String key,Object value) {
		Object result = execute(new SingleExecutor(new KeyValue(key,value)) {			
			@SuppressWarnings("unchecked")
			@Override
			public Boolean executor(KeyPair kp ,Client client, ByteBuffer row, Object value)
			throws IOError,TException, IllegalArgument, TIOError, AuthExpire {		
				byte[] flagContent = TransformUtil.getType(value);
				byte[] valueContent = TransformUtil.toBytes(value);
				checkValueSize(valueContent);
				TItem item = new TItem();
				item.setFlag(flagContent);
				item.setValue(valueContent);
				client.setItem(kp, row, item);
				return Boolean.TRUE;
			}
		});
		//TransformUtil.caclInputOutputSize(key, value);
		if(result == null) {
			return false;
		}
		return (Boolean)result;
	}
	
	
	/**
	 * 查找多个key，如果传入的key集合为空则返回一个空Map，如果找不到对应的值也会返回一个空Map
	 * @param keys 需要查找的key集合
	 * @return 一个Map集合，键为查找的key，值为key对应的值，值的具体类型不确定
	 */	
	public Map<String,Object> getMulti(String[] keys) {
		if(keys==null || keys.length==0) {
			throw new RuntimeException("keys is empty");
		}
		List<KeyValue> list = new ArrayList<KeyValue>();
		for(String s : keys) {
			list.add(new KeyValue(s,EMPTY_VALUE));
		}
		Map<String,Object> map = execute(new BatchExecutor(list) {
			@SuppressWarnings("unchecked")
			public <T> T executor(KeyPair kp,TKvdbService.Client client, Map<ByteBuffer,Object> values)
			throws IOError,TException, IllegalArgument, TIOError, AuthExpire {
				List<ByteBuffer> rows = new ArrayList<ByteBuffer>();
				for(ByteBuffer bb : values.keySet()) {
					rows.add(bb);
				}
				Map<String, TItem> items = client.getItems(kp, rows);
				Map<String,Object> result = new TreeMap<String, Object>();
				Iterator<Entry<String, TItem>> iter = items.entrySet().iterator();
				while(iter.hasNext()) {
					Entry<String,TItem> entry = iter.next();
					String key = removeAccessKeyPrefix( entry.getKey() );
					TItem item = entry.getValue();
					byte[] flag = item.getFlag();
					byte[] content = item.getValue();
					Object obj = TransformUtil.getContent(flag, content);
					result.put(key, obj);
				}
				return (T) result;
			}
		});
		//TransformUtil.caclInputOutputSize(keys, map);	
		return map;
	}
	
	
	
	/**
	 * 根据key前缀查找，如存储的key是aa1，aa2，aa3，则前缀应为aa，不支持正则表达式。
	 * @param prefixKey key前缀
	 * @param queryCount 查询数量，最多支持100个，如果超过100个只按100个查询
	 * @param compareKey 需要比较的key，如果存储的key为aa1，aa2，aa3，aa4，输入aa2，则返回的aa3和aa4
	 * @return 包含多个key和value的map
	 */
	public Map<String, Object> pkrget(String prefixKey, int queryCount,String compareKey) {
		return pkrget(prefixKey,queryCount,true,compareKey);
	}

	/**
	 * 根据key前缀查找，如存储的key是aa1，aa2，aa3，则前缀应为aa，不支持正则表达式。
	 * @param prefixKey key前缀
	 * @param compareKey 需要比较的key，如果存储的key为aa1，aa2，aa3，aa4，输入aa2，则返回的aa3和aa4
	 * @return 包含多个key和value的map
	 */
	public Map<String, Object> pkrget(String prefixKey, String compareKey) {
		return pkrget(prefixKey,DEFAULT_PKRGET_QUERY_COUNT,true,compareKey);
	}
	
	
	/**
	 * 根据key前缀查找，如存储的key是aa1，aa2，aa3，则前缀应为aa，不支持正则表达式。
	 * @param prefixKey key前缀
	 * @param queryCount 查询数量，最多支持100个，如果超过100个只按100个查询
	 * @param isContainValue 是否包含查找的值，如果设置此值为false，则返回的map中所有的value都为null
	 * @param compareKey 需要比较的key，如果存储的key为aa1，aa2，aa3，aa4，输入aa2，则返回的aa3和aa4
	 * @return 包含多个key和value的map
	 */
	public Map<String, Object> pkrget(String prefixKey, int queryCount,
	boolean isContainValue, String compareKey) {
		Map<String,Object> result = execute(new ScanExecutor(prefixKey,queryCount,
		isContainValue,compareKey,"","",false,false,ScanType.PKRGET) {
			@SuppressWarnings("unchecked")
			public <T> T executor(KeyPair kp,Client client, ByteBuffer startAndPrefix,
			ByteBuffer stopKey, int count, boolean isContainValue,
			boolean isContainStartVlaue, boolean isContainEndValue,
			ByteBuffer compareKey) throws IOError, TException,IllegalArgument, TIOError, AuthExpire {	
				Map<String, TItem> items = client.prkget(kp, startAndPrefix, count, isContainValue, compareKey);				
				Map<String,Object> result = new TreeMap<String, Object>();
				Iterator<Entry<String, TItem>> iter = items.entrySet().iterator();
				while(iter.hasNext()) {
					Entry<String,TItem> entry = iter.next();
					String key = removeAccessKeyPrefix( entry.getKey() );
					TItem item = entry.getValue();
					byte[] flag = item.getFlag();
					byte[] content = item.getValue();
					Object obj = TransformUtil.getContent(flag, content);
					result.put(key, obj);
				}
				return (T) result;
			}
		});
		//TransformUtil.caclInputOutputSize(prefixKey, compareKey, result);
		return result;
	}
	
	
	
	/**
	 * 根据key的范围删除
	 * @param startKey 起始的key
	 * @param endKey 结束的key
	 * @return 删除操作是否成功
	 */
	public boolean rdel(String startKey,String endKey) {
		return rdel(startKey,endKey,true,true,DEFAULT_RDEL_DELETE_COUNT);
	}	
	
	/**
	 * 根据key的范围删除
	 * @param startKey 起始的key
	 * @param endKey 结束的key
	 * @param isContainStartVlaue 是否包含第一个查到的key
	 * @param isContainEndValue 是否包含最后一个查到的key
	 * @return 删除操作是否成功
	 */
	public boolean rdel(String startKey, String endKey,
	boolean isContainStartVlaue, boolean isContainEndValue) {
		return rdel(startKey,endKey,isContainStartVlaue,isContainEndValue,DEFAULT_RDEL_DELETE_COUNT);
	}

	/**
	 * 根据key的范围删除
	 * @param startKey 起始的key
	 * @param endKey 结束的key
	 * @param deleteCount 删除的数量
	 * @return 删除操作是否成功
	 */
	public boolean rdel(String startKey, String endKey, int deleteCount) {
		return rdel(startKey,endKey,true,true,deleteCount);
	}

	
	/**
	 * 根据key的范围删除
	 * @param startKey 起始的key
	 * @param endKey 结束的key
	 * @param isContainStartVlaue 是否包含第一个查到的key
	 * @param isContainEndValue 是否包含最后一个查到的key
	 * @param deleteCount 删除的数量
	 * @return 删除操作是否成功
	 */
	public boolean rdel(String startKey, String endKey,
	boolean isContainStartVlaue, boolean isContainEndValue,int deleteCount) {
		Object result = execute(new ScanExecutor("",deleteCount,
		false,"",startKey,endKey,isContainStartVlaue,isContainEndValue,ScanType.RDEL) {
			@SuppressWarnings("unchecked")
			@Override
			public Boolean executor(KeyPair kp,Client client,ByteBuffer startAndPrefix, ByteBuffer stopKey,
			int count, boolean isContainValue,boolean isContainStartVlaue,
			boolean isContainEndValue, ByteBuffer compareKey)
			throws IOError, TException, IllegalArgument, TIOError, AuthExpire {	
				client.rdel(kp, startAndPrefix, stopKey, count);							
				return Boolean.TRUE;
			}
		});
		//TransformUtil.caclInputOutputSize(startKey, endKey, result);
		return (Boolean)result;
	}
	
	
	/**
	 * 根据key的范围查找
	 * @param startKey 起始的key
	 * @param endKey 结束的key
	 * @return 包含多个key和value的map
	 */
	public Map<String, Object> rget(String startKey, String endKey) {
		return rget(startKey,endKey,true,true,DEFAULT_RGET_QUERY_COUNT);
	}

	/**
	 * 根据key的范围查找
	 * @param startKey 起始的key
	 * @param endKey 结束的key
	 * @param isContainStartVlaue 是否包含第一个查到的key
	 * @param isContainEndValue 是否包含最后一个查到的key
	 * @return 包含多个key和value的map
	 */
	public Map<String, Object> rget(String startKey, String endKey,boolean isContainStartVlaue, boolean isContainEndValue) {
		return rget(startKey,endKey,isContainStartVlaue,isContainEndValue,DEFAULT_RGET_QUERY_COUNT);
	}

	/**
	 * 根据key的范围查找
	 * @param startKey 起始的key
	 * @param endKey 结束的key
	 * @param queryCount 查询的数量
	 * @return 包含多个key和value的map
	 */
	public Map<String, Object> rget(String startKey, String endKey,int queryCount) {
		return rget(startKey,endKey,true,true,queryCount);
	}
	
	/**
	 * 根据key的范围查找
	 * @param startKey 起始的key
	 * @param endKey 结束的key
	 * @param isContainStartVlaue 是否包含第一个查到的key
	 * @param isContainEndValue 是否包含最后一个查到的key
	 * @param queryCount 查询的数量
	 * @return 包含多个key和value的map
	 */
	public Map<String, Object> rget(String startKey, String endKey,
	boolean isContainStartVlaue, boolean isContainEndValue,int queryCount) {
		Map<String,Object> result = execute(new ScanExecutor("",queryCount,
		false,"",startKey,endKey,isContainStartVlaue,isContainEndValue,ScanType.RDEL) {
			@SuppressWarnings("unchecked")
			@Override
			public <T> T executor(KeyPair kp,Client client, ByteBuffer startAndPrefix,ByteBuffer stopKey,
			int count, boolean isContainValue,boolean isContainStartVlaue, boolean isContainEndValue,
			ByteBuffer compareKey) throws IOError, TException,IllegalArgument, TIOError, AuthExpire {	
				Map<String, TItem> items = client.rget(kp, startAndPrefix, stopKey, count);
				Map<String,Object> result = new TreeMap<String, Object>();
				Iterator<Entry<String, TItem>> iter = items.entrySet().iterator();
				while(iter.hasNext()) {
					Entry<String,TItem> entry = iter.next();
					String key = removeAccessKeyPrefix( entry.getKey() );
					TItem item = entry.getValue();
					byte[] flag = item.getFlag();
					byte[] content = item.getValue();
					Object obj = TransformUtil.getContent(flag, content);
					result.put(key, obj);
				}
				return (T) result;
			}
		});
		//TransformUtil.caclInputOutputSize(startKey, endKey, result);
		return result;
	}
	
	
	
	/**
	 * 在老版本中可以设置一些特殊选项，在hbase实现中这只是一个空函数不做任何功能，为的只是兼容老的API
	 * @param option
	 */
	public void setOptions(String option) {
		//ignore
	}
	
	/**
	 * kv状态查询，返回total size和total count信息
	 * @deprecated 此方法已过时，获取的内容为空
	 * @return map键值对
	 */
	@Deprecated
	public Map<String, Long> getInfo() {
		checkInitialized();
		return new HashMap<String,Long>();
	}
	
	
	/**
	 * 返回错误码
	 * @return 错误码
	 */
	public int getErrCode() {
		return this.errCode;
	}

	/**
	 * 返回错误消息
	 * @return
	 */
	public String getErrMsg() {
		return this.errMsg;
	}

	/**
	 * 记录错误的返回码和错误消息
	 * @param code 返回码
	 * @param msg 错误消息
	 */
	private void recordErrCodeAndMsg(int code,String msg) {
		this.errCode = code;
		this.errMsg = msg;
	}
	
	
}
