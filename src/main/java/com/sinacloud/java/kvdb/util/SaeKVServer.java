package com.sinacloud.java.kvdb.util;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import net.spy.memcached.MemcachedClient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.KeyOnlyFilter;
import org.apache.hadoop.hbase.filter.PageFilter;
import org.apache.hadoop.hbase.filter.PrefixFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.thrift.TException;



public class SaeKVServer implements TKvdbService.Iface {
	
	private static final Configuration cfg = new Configuration();
	
	
	public static final Log logger = LogFactory.getLog(SaeKVServer.class);
	
	
	private static final byte[] QUALIFIER_FLAG = Bytes.toBytes("f");
	
	/**
	 * 对应value的qualifier
	 */
	private static final byte[] QUALIFIER_VALUE = Bytes.toBytes("v");
	
	/**
	 * 10分钟，换算成了毫秒
	 */
	private static final int DELAY_TIME_SECOND;
	
	private ThreadLocal<HTable> local = new ThreadLocal<HTable>();
	
	private static final Map<String,String> tokens = new ConcurrentHashMap<String, String>();
	
	
	private static final String MYSQL_ADDR;
	private static final String MYSQL_USER;
	private static final String MYSQL_PASS;
	
	private static final String MC_ADDR;
	private static MemcachedClient mc;
	
	
	static {
		URL u1 = SaeKVServer.class.getClassLoader().getResource("sae-site.xml");
		URL u2 = SaeKVServer.class.getClassLoader().getResource("hbase-site.xml");
		cfg.addResource(u1);
		cfg.addResource(u2);
		
		DELAY_TIME_SECOND = Integer.parseInt( cfg.get("com.sina.sae.thrift.delay_time_second") ) * 1000;
		
		MYSQL_ADDR = cfg.get("com.sina.sae.common.sae_db_url");
		MYSQL_USER = cfg.get("com.sina.sae.common.sae_db_name");
		MYSQL_PASS = cfg.get("com.sina.sae.common.sae_db_pass");
		
		MC_ADDR = cfg.get("com.sina.sae.thrift.mc_addr");
		if(MC_ADDR!=null && !"".equals(MC_ADDR)) {
			String[] serverList = MC_ADDR.split(",");
			InetSocketAddress[] isa = new InetSocketAddress[serverList.length];
			for(int i=0;i<isa.length;i++) {
				String[] ips = serverList[i].split(":");
				try {
					InetAddress ia = InetAddress.getByName(ips[0]);
					int port = Integer.parseInt(ips[1]);
					isa[i] = new InetSocketAddress(ia,port);
				} catch (UnknownHostException e) {					
					throw new RuntimeException(e.getMessage());
				}
			}
			try {
				mc = new MemcachedClient(isa);
			} catch (IOException e) {
				throw new RuntimeException(e.getMessage());
			}
		}
	}
	
	/**
	 * 关闭指定的流
	 * @param io
	 */
	@SuppressWarnings("unused")
	private void close(Closeable io) {
		if(io != null) {
			try {
				io.close();
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
	}
	
	

	/**
	 * 对ID进行验证，如果ID为空则重新验证ak和sk，并生成一个UUID和ak关联
	 * 下次通过UUID获取到ak并返回
	 * @param kp
	 * @return pair
	 */
	@Override
	public KeyPair auth(KeyPair kp) {
		String token = null;
		if( checkAuthorization(kp) ) {
			token = UUID.randomUUID().toString();
			tokens.put(token, kp.getAccesskey());
			new Timer().schedule(new DelayDelToken(token), DELAY_TIME_SECOND);
		}
		else {
			//认证失败
			throw new RuntimeException("auth failure");
		}
		if(token == null) {
			throw new RuntimeException("auth failure token is null");
		}
		KeyPair pair = new KeyPair();
		pair.setToken(token);
		return pair;
	}
	
	private class DelayDelToken extends TimerTask {
		private final String token;
		public DelayDelToken(String token) {
			this.token = token;
		}
		
		@Override
		public void run() {
			tokens.remove(token);
		}
		
	}
	

	/**
	 * 计算签名，从KeyPair对象中获取accesskey和timestamp，用这两个值组成内容，然后再获得secretkey
	 * 最后获得签名的内容。最后从KeyPair中获得客户端签名的内容，如果相等则签名通过否则抛错
	 * @param kp
	 * @return boolean
	 */
	private boolean checkAuthorization(KeyPair kp) {
		String accesskey = kp.getAccesskey();
		long timestamp = kp.getTimestamp();
		
		//获取 secret key
		String secretkey = getSecretkey(accesskey);
		String content = accesskey+"-"+timestamp;
		String signatureContent = calcSignature(content,secretkey);
		if(signatureContent.equals(kp.getSignature())) {
			return true;
		}
		if("".equals(signatureContent)) {
			return false;
		}
		return false;
	}
	
	/**
	 * 根据ak获取sk
	 * @param accesskey
	 * @return secretkey
	 */
	private String getSecretkey(String accesskey) {
		//return connMysql(accesskey);
		return connMemcache(accesskey);
	}
	
	/**
	 * 通过mysql后去secretkey
	 * @param ak
	 * @return secretkey
	 */
	@SuppressWarnings("unused")
	private static String connMysql(String ak) {
		String secretkey = null;
		try {
			Connection conn = DriverManager.getConnection(MYSQL_ADDR, MYSQL_USER, MYSQL_PASS);
			String sql = "select secretKey from keyPair where accesskey='" +ak+"'";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				secretkey = rs.getString("secretKey");
			}
			rs.close();
			ps.close();
			conn.close();
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
		if(secretkey==null) {
			throw new RuntimeException("no secretkey");
		}
		return secretkey;
	}
	
	/**
	 * 通过memcache获取secretkey
	 * @param accesskey
	 * @return secretkey
	 */
	private static String connMemcache(String accesskey) {
		String content = (String)mc.get(accesskey);
		if(content!=null && content.length()>40) {
			return content.substring(0, 40);
		}
		throw new RuntimeException("secretkey illgeal");
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
        	logger.error("calc signature failure.",e);  
        	return "";
        }
	}
	
	
	
	/**
	 * 从线程的缓存中获取HTable实例
	 * @return table
	 * @throws IOException
	 */
	private HTable getTable() throws IOException {
		HTable table = local.get();
		if(table == null) {
			table = new HTable(cfg,"kvdb");
			local.set(table);
		}
		return table;
	}
	
	private void release(HTable t) {
		
	}
	


	/**
	 * 将accesskey_前缀去掉
	 * @param key
	 * @return key
	 */
	private String removeAccessKeyPrefix(String key) {
		if(key==null || key.length()<=11) {
			throw new RuntimeException("key illegal");
		}
		return key.substring(11,key.length());
	}
	
	/**
	 * 在每个key前面增加accesskey前缀
	 * @param accesskey
	 * @param key
	 */
	private String addAccesskeyPrefix(String accesskey, String key) {
		return (accesskey +"_"+ key);
	}
	
	
	/**
	 * 根据token获取accesskey
	 * @param kp
	 * @return accesskey
	 * @throws AuthExpire 
	 */
	private String getAccesskey(KeyPair kp) throws AuthExpire {
		String accesskey = tokens.get( kp.getToken() );
		if(accesskey==null || "".equals(accesskey)) {
			throw new AuthExpire();
		}
		return accesskey;
	}
	
	@SuppressWarnings("resource")
	@Override
	public TItem getItem(KeyPair kp, ByteBuffer buffer) throws TIOError,
	TException, AuthExpire {
		String accessKey = getAccesskey(kp);
		byte[] buf = TransformUtil.decodeBuffer(buffer);
		HTable table = null;
		TItem item = new TItem();
		
		try {
			table = getTable();
			String encodeKey = TransformUtil.encodeKey(buf);
			String key = addAccesskeyPrefix(accessKey,encodeKey);
			Get get = new Get(Bytes.toBytes(key));
			Result result = table.get(get);
			result.raw();
			
			KeyValue[] kv = result.raw();
			byte[] type = null;
			byte[] content = null;
			for (int i = 0; i < kv.length; i++) {
				if(kv[i].getQualifier()[0] == QUALIFIER_FLAG[0]) {
					type = kv[i].getValue();
				}
				else if(kv[i].getQualifier()[0] == QUALIFIER_VALUE[0]) {
					content = kv[i].getValue();
				}
				else {
					throw new RuntimeException("unknow kv type "+java.util.Arrays.toString(kv[i].getQualifier()));
				}
			}
			item.setFlag(type);
			item.setValue(content);
			return item;
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			release(table);
		}
		return item;
	}

	
	
	@SuppressWarnings({ "resource" })
	@Override
	public Map<String, TItem> getItems(KeyPair kp, List<ByteBuffer> keyBuffer)
	throws TIOError, TException,AuthExpire  {
		String accessKey = getAccesskey(kp);	
		Map<String, TItem> map = new HashMap<String, TItem>();
		HTable table = null;		
		try {
			String[] keys = new String[keyBuffer.size()];
			for(int i=0;i<keyBuffer.size();i++) {
				String key = TransformUtil.encodeKey( TransformUtil.decodeBuffer(keyBuffer.get(i)) );
				keys[i] = addAccesskeyPrefix(accessKey,key);
			}
			
			table = getTable();
			List<Get> list = new ArrayList<Get>();
			for(String k:keys) {
				Get g = new Get(Bytes.toBytes(k));
				list.add(g);
			}
			Result[] rs = table.get(list);
			for(Result r:rs) {
				KeyValue[] kv = r.raw();
				if(kv!=null && kv.length<=1) {
					continue;
				}
				byte[] type = null;
				byte[] content = null;
				String key = new String(kv[0].getRow());
				key = removeAccessKeyPrefix(key);
				for (int i = 0; i < kv.length; i++) {
					if(kv[i].getQualifier()[0] == QUALIFIER_FLAG[0]) {
						type = kv[i].getValue();
					}
					else if(kv[i].getQualifier()[0] == QUALIFIER_VALUE[0]) {
						content = kv[i].getValue();
					}
					else {
						throw new RuntimeException("unknow kv type "+java.util.Arrays.toString(kv[i].getQualifier()));
					}
				}//inner for
				TItem item = new TItem();
				item.setFlag(type);
				item.setValue(content);
				map.put(key, item);
			}//outer for
		} catch(IOException e) {
			logger.error(e.getMessage(), e);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			release(table);
		}
		return map;
	}

	
	@Override
	public Map<String, TItem> getItemsByPrefix(KeyPair kp, ByteBuffer prefix,
	int count) throws TIOError, TIllegalArgument, TException, AuthExpire {
		String accessKey = getAccesskey(kp);
		byte[] buf = TransformUtil.decodeBuffer(prefix);
		HTable table = null;
	
		Map<String,TItem> map = new LinkedHashMap<String, TItem>();
		try {
			table = getTable();
			String encodeKey = TransformUtil.encodeKey(buf);
			String key = addAccesskeyPrefix(accessKey,encodeKey);
			Scan scanner = new Scan();
			scanner.setStartRow(key.getBytes());
			scanner.setStopRow( TransformUtil.caclStopKey(key) );
			scanner.setFilter(new PageFilter(count));
			ResultScanner rs =  table.getScanner(scanner);			
			Iterator<Result> iter = rs.iterator();
			while(iter.hasNext()) {
				Result r = iter.next();
				KeyValue[] kv = r.raw();
				byte[] type = null;
				byte[] content = null;
				String row = new String(kv[0].getRow());
				row = removeAccessKeyPrefix(row);
				for (int i = 0; i < kv.length; i++) {
					if(kv[i].getQualifier()[0] == QUALIFIER_FLAG[0]) {
						type = kv[i].getValue();
					}
					else if(kv[i].getQualifier()[0] == QUALIFIER_VALUE[0]) {
						content = kv[i].getValue();
					}
					else {
						throw new RuntimeException("unknow kv type "+java.util.Arrays.toString(kv[i].getQualifier()));
					}
				}//inner for
				TItem item = new TItem();
				item.setFlag(type);
				item.setValue(content);
				map.put(row,item);
			}
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			release(table);
		}
		return map;
	}

	@Override
	public void setItem(KeyPair kp, ByteBuffer key, TItem item)
	throws TIOError, TException, AuthExpire {
		String accessKey = getAccesskey(kp);
		byte[] buf = TransformUtil.decodeBuffer(key);
		byte[] flag = item.getFlag();
		byte[] value = item.getValue();
		
		HTable table = null;
		try {
			String row = addAccesskeyPrefix(accessKey,TransformUtil.encodeKey(buf));
			table = getTable();
			Put put = new Put(Bytes.toBytes(row));
			put.add(Bytes.toBytes("value"), Bytes.toBytes("f"), flag);
			put.add(Bytes.toBytes("value"), Bytes.toBytes("v"), value);
			table.put(put);
		} catch(IOException e) {
			logger.error(e.getMessage(), e);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			release(table);
		}
		
	}

	@Override
	public void deleteItem(KeyPair kp, ByteBuffer key) throws TIOError,
	TException, AuthExpire {
		String accessKey = getAccesskey(kp);	
		HTable table = null;
		try {
			String row = addAccesskeyPrefix(accessKey, TransformUtil.encodeKey( TransformUtil.decodeBuffer(key) ));
			table = getTable();
			Delete delete = new Delete(Bytes.toBytes(row));
			table.delete(delete);
		} catch(IOException e) {
			logger.error(e.getMessage(), e);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			release(table);
		}
		
	}

	
	@Override
	public Map<String, TItem> rget(KeyPair kp, ByteBuffer startKey,
	ByteBuffer stopKey, int count) throws TIOError, TException, AuthExpire {
		String accessKey = getAccesskey(kp);
		HTable table = null;
		Map<String,TItem> map = new LinkedHashMap<String, TItem>();
		try {
			String startRow = addAccesskeyPrefix(accessKey, TransformUtil.encodeKey( TransformUtil.decodeBuffer(startKey) ));
			String stopRow = addAccesskeyPrefix(accessKey, TransformUtil.encodeKey( TransformUtil.decodeBuffer(stopKey) ));
			
			table = getTable();
			Scan scan = new Scan();
			scan.setStartRow(Bytes.toBytes(startRow));
			scan.setStopRow(Bytes.toBytes(stopRow));
			scan.setFilter(new PageFilter(count));
			ResultScanner rs =  table.getScanner(scan);
			
			Iterator<Result> iter = rs.iterator();
			while(iter.hasNext()) {
				Result r = iter.next();
				KeyValue[] kv = r.raw();
				byte[] type = null;
				byte[] content = null;
				String key = new String(kv[0].getRow());
				key = removeAccessKeyPrefix(key);
				for (int i = 0; i < kv.length; i++) {
					if(kv[i].getQualifier()[0] == QUALIFIER_FLAG[0]) {
						type = kv[i].getValue();
					}
					else if(kv[i].getQualifier()[0] == QUALIFIER_VALUE[0]) {
						content = kv[i].getValue();
					}
					else {
						throw new RuntimeException("unknow kv type "+java.util.Arrays.toString(kv[i].getQualifier()));
					}
				}//inner for
				TItem item = new TItem();
				item.setFlag(type);
				item.setValue(content);
				map.put(key,item);
			}
		} catch(IOException e) {
			logger.error(e.getMessage(), e);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			release(table);
		}
		return map;
	}

	
	@Override
	public Map<String, TItem> prkget(KeyPair kp, ByteBuffer prefixKey,
	int count, boolean isContainValue, ByteBuffer compareKey)
	throws TIOError, TException, AuthExpire {
		String accessKey = getAccesskey(kp);
		Map<String,TItem> map = new LinkedHashMap<String, TItem>();
		HTable table = null;
		try {
			String rowPrefixKey = addAccesskeyPrefix(accessKey, TransformUtil.encodeKey( TransformUtil.decodeBuffer(prefixKey) ));
			String rowCompareKey = addAccesskeyPrefix(accessKey, TransformUtil.encodeKey( TransformUtil.decodeBuffer(compareKey)));
			table = getTable();
			Scan scanner = new Scan();
			FilterList filter = new FilterList();
			filter.addFilter(new PageFilter(count));
			filter.addFilter(new PrefixFilter(Bytes.toBytes(rowPrefixKey)));
			if(!isContainValue) {
				filter.addFilter(new KeyOnlyFilter());
			}
			scanner.setFilter(filter);
			scanner.setStartRow(Bytes.toBytes(rowCompareKey));
			
			ResultScanner rs = table.getScanner(scanner);
			for (Result r : rs) {
				KeyValue[] kv = r.raw();
				byte[] type = null;
				byte[] content = null;
				String key = new String(kv[0].getRow());
				key = removeAccessKeyPrefix(key);
				for (int i = 0; i < kv.length; i++) {
					if(kv[i].getQualifier()[0] == QUALIFIER_FLAG[0]) {
						type = kv[i].getValue();
					}
					else if(kv[i].getQualifier()[0] == QUALIFIER_VALUE[0]) {
						content = kv[i].getValue();
					}
					else {
						throw new RuntimeException("unknow kv type "+java.util.Arrays.toString(kv[i].getQualifier()));
					}
				}//inner for
				TItem item = new TItem();
				item.setFlag(type);
				item.setValue(content);
				map.put(key, item);
			}
		} catch(IOException e) {
			logger.error(e.getMessage(), e);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			release(table);
		}
		return map;
	}

	@Override
	public void rdel(KeyPair kp, ByteBuffer startKey, ByteBuffer stopKey,
	int count) throws TIOError, TException, AuthExpire {
		String accessKey = getAccesskey(kp);	
		HTable table = null;
		try {
			String startRow = addAccesskeyPrefix(accessKey, TransformUtil.encodeKey( TransformUtil.decodeBuffer(startKey) ));
			String stopRow = addAccesskeyPrefix(accessKey, TransformUtil.encodeKey( TransformUtil.decodeBuffer(stopKey) ));
			table = getTable();
			Scan scanner = new Scan();
			scanner.setStartRow(Bytes.toBytes(startRow));
			scanner.setStopRow(Bytes.toBytes(stopRow));
			scanner.setFilter(new PageFilter(count));
			
			ResultScanner rs = table.getScanner(scanner);
			for (Result r : rs) {
				Delete d = new Delete(r.getRow());
				table.delete(d);
			}
		} catch(IOException e) {
			logger.error(e.getMessage(), e);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			release(table);
		}
	}
	

}