package com.sinacloud.storage;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;


import org.apache.log4j.Logger;

import com.sinacloud.storage.model.Bucket;
import com.sinacloud.storage.model.BucketList;
import com.sinacloud.storage.model.BucketResponseProcesser;
import com.sinacloud.storage.model.ObjectFile;
import com.sinacloud.storage.model.ObjectFileList;
import com.sinacloud.storage.model.ObjectResponseProcesser;

/**
 *  新浪云 容器应用 Storage 服务接口。
 *  <p>
 *  Storage是新浪云为开发者提供的分布式对象存储服务，旨在利用新浪云在分布式以及网络技术方面的优势为开发者提供安全、简单、高效的存储服务。<br>
 *  Storage支持文本、多媒体、二进制等任何类型的数据的存储。
 *  仅限容器环境中<br>
 *  <p style="font-size:14px"> <b><i>
 *  使用样例：<br>
 *  StroageClient sc = new StroageClient(); // 这里是默认构造，如果不使用当前应用下的storage可以调用传参数构造器new StroageClient(appname,ak,sk)<br>
 *  //创建bucket <br>
 *  sc.createBucket("example_bucket"');<br>
 *  //删除buckete <br>
 *  sc.deleteBucket("example_bucket");<br>
 *  //获取相应bucket信息<br>
 *  Bucket bucket = sc.getBucketInfo();<br>
 *  //列出所有的bucket信息<br>
 *  BucketList bucketList = sc.listBuckets();<br>
 *  //获取bucket下所有的文件列表<br>
 *  ObjectFileList objectFileList = sc.getBucket("example_bucket");<br>
 *  //获取bucket下所有以example_prefix为前缀的文件列表 <br>
 *  ObjectFileList objectFileList = sc.getBucket("example_bucket","example_prefix");<br>
 *  //在storage上写一个文件，最后的参数不需要可以传null<br>
 *  sc.putObjectFile("example_bucket","filename","content".getBytes(),metaMap);<br>
 *  //上传一个文件，最后的参数不需要可以传null <br>
 *  sc.putObjectFile("example_bucket","filename",File,metaMap);<br>
 *  //获取一个文件<br>
 *  ObjectFile objcetFile = sc.getObject("example_bucket","filename");<br>
 *  //删除一个文件<br>
 *  sc.deleteObject("example_bucket","filename");<br>
 *  </i></b></p>
 * @author nero
 */
public class StorageClient extends StorageBase implements Storage {
	
	private static final String ERROR_MSG_101 = "the value of parameter  can not be empty!";
	private static final int ERROR_NUM_101 = -101;
	
	private static final String ERROR_MSG_102 = "URL exception";
	private static final int ERROR_NUM_102 = -102;
	
	private static final String ERROR_MSG_103 = "IOException";
	private static final int ERROR_NUM_103 = -103;
	
	private static final String ERROR_MSG_104 = "file does not exist";
	private static final int ERROR_NUM_104 = -104;
	
	private static final String ERROR_MSG_0 = "success!";
	private static final int ERROR_NUM_0 = 0;
	
	private static final String ERROR_MSG_201 = "the bucket has created";
	private static final int ERROR_NUM_201 = -201;
	
	private static final String ERROR_MSG_202 = "the bucket not found or has remove";
	private static final int ERROR_NUM_202 = -202;
	
	private static final String ERROR_MSG_203 = "the bucket is not null";
	private static final int ERROR_NUM_203 = -203;
	
	private static final String ERROR_MSG_204 = "reach the upper limit of creating buckets";
	private static final int  ERROR_NUM_204 = -204;
	
	private static final String ERROR_MSG_301 = "object file does not exist in the bucket or has removed";
	private static final int ERROR_NUM_301 = -301;
	
	private static Logger logger = Logger.getLogger(StorageClient.class.getName());
	
	public StorageClient(){
		appName = System.getenv("APPNAME");
		accessKey = System.getenv("ACCESSKEY");
		secretKey = System.getenv("SECRETKEY");
		if (secretKey == null || "".equals(secretKey)) {
			throw new RuntimeException(
					"secretkey is null,please invoke "
							+ "public StorageClient(String appName,String accessKey,String secertKey) method");
		}
		if(appName == null || "".equals(appName)){
			throw new RuntimeException(
					"appname is null,please invoke "
							+ "public StorageClient(String appName,String accessKey,String secertKey) method");
		}
		if(secretKey == null || "".equals(secretKey)){
			throw new RuntimeException(
					"secretKey is null,please invoke "
							+ "public StorageClient(String appName,String accessKey,String secertKey) method");
		}
	}
	
	public StorageClient(String appName,String accessKey,String secertKey){
		if(!StorageUtils.isEmpty(appName) || !StorageUtils.isEmpty(accessKey) || !StorageUtils.isEmpty(secertKey)){
			throw new RuntimeException("please check your param, at least one param,At least one parameter is missing!");
		}
		this.appName = appName;
		this.accessKey = accessKey;
		this.secretKey = secertKey;
	}

	
	
	/**
	 * 检查必须的参数是否为空
	 * 
	 * @param parm1
	 *            需要检查的参数1
	 * @param parm2
	 *            需要检查的参数2
	 * @return 是否为空
	 */
	private boolean checkParameter(String parm1, String parm2) {
		// 不用短路 或 ，因为需要对两个参数都检查是否为null
		if (parm1 == null | parm2 == null) {
			throw new RuntimeException(ERROR_MSG_101);
		}
		return true;
	}

	/**
	 * 检查必须的参数是否为空
	 * 
	 * @param parm
	 *            需要检查的参数
	 * @return 是否为空
	 */
	private boolean checkParameter(String parm) {
		if (parm == null) {
			throw new RuntimeException(ERROR_MSG_101);
		}
		return true;
	}

	/**
	 * 根据URL 计算对应的path
	 * 
	 * @param url
	 * @return path
	 */
	private String getPath(String url) {
		int _index = url.indexOf("_");
		return "/" + url.substring(_index + 1);
	}
	
	/**
	 * 
	 * 返回构造好的HttpURLConnection
	 * @param url 构造的链接地址
	 * @param timeout 超时时间
	 * @param method 方法
	 * @param path storage的domain路径 url的"_"之后的部分
	 * @param propMap 需要额外在头部添加的属性
	 * @return HttpURLConnection
	 * @throws MalformedURLException IOException
	 */
	private HttpURLConnection getConnection(String url , int timeout, String method,String path,Map<String,String> propMap){
		HttpURLConnection conn = null;
		URL _url = null;
		int code = 0;
		try {
			_url = new URL(url);
			conn = (HttpURLConnection)_url.openConnection();
			conn.setConnectTimeout(timeout);
			conn.setRequestMethod(method);
			if(propMap != null && propMap.size() != 0){
				for(String k : propMap.keySet()){
					conn.setRequestProperty(k, propMap.get(k).toString());
				}
			}
			String date = StorageUtils.getTimeStamp();
			conn.setRequestProperty("Date", date);
			conn.setRequestProperty("Accept", "*/*");
			Map<String, List<String>> map = conn.getRequestProperties();
			StringBuffer headerbuf = new StringBuffer();
			Iterator<String> iterator = map.keySet().iterator();
			while (iterator.hasNext()) {
				String key = (String) iterator.next();
				if (key != null) {
					if (key.toLowerCase().startsWith("x-sws")) {
						headerbuf.append(key.toLowerCase()).append(":")
								.append(map.get(key)).append("\n");
					}
				}
			}
			String headerstr = headerbuf.toString();
			String signheader = method + "\n" + date + "\n" + headerstr + URLDecoder.decode(path,"utf-8");  //这里进行URLDecoder是为了将中文的文件名还原，因为swift服务端的校验逻辑是decode之后在做签名校验
			String ssig = StorageUtils.calcSignature(signheader, secretKey);
			conn.setRequestProperty("Authorization", "SWS " + accessKey+ ":" + ssig);
		} catch (MalformedURLException e) {
			logger.error(ERROR_MSG_102, e);
			throw new RuntimeException(ERROR_MSG_102);
		} catch (IOException e) {
			logger.error(ERROR_MSG_103, e);
			throw new RuntimeException(ERROR_MSG_103);
		}
		return conn;
	} 
	
	/**
	 * bucket相关操作
	 * @param conn
	 * @return BucketResponseProcesser
	 * @throws IOException
	 */
	private BucketResponseProcesser doBucketOperation(HttpURLConnection conn) throws IOException{
		return doBucketOperation(conn,null);
	}
	
//	private void printHeader(Map<String,List<String>> map){
//		for(String k : map.keySet()){
//			System.out.print(k+" : ");
//			for(String v : map.get(k)){
//				System.out.print(v + "  ");
//			}
//			System.out.println();
//		}
//	}
	
	/**
	 * bucket相关操作
	 * @param conn
	 * @return BucketResponseProcesser
	 * @throws IOException
	 */
	private BucketResponseProcesser doBucketOperation(HttpURLConnection conn,String bucketName) throws IOException{
		BucketResponseProcesser bucketResponseProcesser = new BucketResponseProcesser();
		conn.connect();
		if(conn.getRequestMethod() != PUT && conn.getRequestMethod() != DELETE){
			bucketResponseProcesser.setBodyContent(StorageUtils.toByteArray(conn.getInputStream()));
		}
		bucketResponseProcesser.setResponseCode(conn.getResponseCode());
		bucketResponseProcesser.setResponseMessage(conn.getResponseMessage());
		bucketResponseProcesser.setResponseHeaders(conn.getHeaderFields());
		if(bucketName != null){
			Bucket bucket = new Bucket();
			bucket.setBucketName(bucketName);
			bucket.setBucketSize(Integer.parseInt(bucketResponseProcesser.getResponseHeaders().containsKey(MetaHeaders.USEDSIZE)?bucketResponseProcesser.getResponseHeaders().get(MetaHeaders.USEDSIZE).get(0).toString():"0"));
			bucket.setObjectFilecount(Integer.parseInt(bucketResponseProcesser.getResponseHeaders().containsKey(MetaHeaders.OBJECTCOUNT)?bucketResponseProcesser.getResponseHeaders().get(MetaHeaders.OBJECTCOUNT).get(0).toString():"0"));
			bucket.setTimestamp(bucketResponseProcesser.getResponseHeaders().containsKey(MetaHeaders.TIMESTEMP)?bucketResponseProcesser.getResponseHeaders().get(MetaHeaders.TIMESTEMP).get(0).toString():"0");
			List<Bucket> list = new ArrayList<Bucket>();
			list.add(bucket);
			bucketResponseProcesser.setBucketList(list);
		}
		return bucketResponseProcesser;
	}
	
	/**
	 * object 相关的操作
	 * @param conn
	 * @return ObjectResponseProcesser
	 * @throws IOException
	 */
	private ObjectResponseProcesser doObjectOperation(HttpURLConnection conn) throws IOException{
		return doObjectOperation(conn,null);
	}
	
	/**
	 * 处理object相关的操作
	 * @param conn
	 * @param content
	 * @return ObjectResponseProcesser
	 * @throws IOException
	 */
	private ObjectResponseProcesser doObjectOperation(HttpURLConnection conn,byte[] content) throws IOException{
		ObjectResponseProcesser objectResponseProcesser = new ObjectResponseProcesser();
		if(content != null){
			String BOUNDARY = Long.toHexString(System.currentTimeMillis());
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
//			conn.setRequestProperty("Content-Type",
//					"multipart/form-data; boundary=" + BOUNDARY);
			OutputStream out = new DataOutputStream(conn.getOutputStream());
			out.write(content);
			out.close();
			conn.disconnect();
		}
		conn.connect();
		if(conn.getRequestMethod() != PUT && conn.getRequestMethod() != DELETE){
			objectResponseProcesser.setBodyContent(StorageUtils.toByteArray(conn.getInputStream()));
		}
		objectResponseProcesser.setResponseCode(conn.getResponseCode());
		objectResponseProcesser.setResponseMessage(conn.getResponseMessage());
		objectResponseProcesser.setResponseHeaders(conn.getHeaderFields());
		return objectResponseProcesser;
	}
	
	/**
	 * 创建一个新的bucket
	 * @param bucketName
	 * @return 是否创建成功
	 * @throws RuntimeException {"the value of parameter  can not be empty!","the bucket has created","reach the upper limit of creating buckets"}
	 * @throws IOException
	 */
	@Override
	public boolean createBucket(String bucketName) {
		checkParameter(bucketName);
		String url = baseurl + this.appName + "/" + bucketName + "/";
		HttpURLConnection conn = getConnection(url, internal_timeout, PUT, getPath(url), null);
		BucketResponseProcesser responseProcesser = null;
		try {
			responseProcesser = doBucketOperation(conn,bucketName);
			if (responseProcesser.getResponseCode() == 201) {
				return true;
			}
			if (responseProcesser.getResponseCode() == 202) {
				throw new RuntimeException(ERROR_MSG_201);
			}
			if (responseProcesser.getResponseCode() == 413) {
				throw new RuntimeException(ERROR_MSG_204);
			}
		} catch (IOException e) {
			logger.error(ERROR_MSG_103, e);
			throw new RuntimeException(ERROR_MSG_103);
		}
		return false;
	}
	
	/**
	 * 获取bucket相关的信息
	 * @param bucketName
	 * @return Bucket
	 * @throws IOException
	 * @throws RuntimeException "the value of parameter  can not be empty!"
	 */
	@Override
	public Bucket getBucketInfo(String bucketName){
		checkParameter(bucketName);
		String url = baseurl + this.appName + "/" + bucketName + "/";
		HttpURLConnection conn = getConnection(url, internal_timeout,HEAD, getPath(url), null);
		BucketResponseProcesser bucketResponseProcesser = null;
		try {
			bucketResponseProcesser = doBucketOperation(conn,bucketName);
			return bucketResponseProcesser.getBucketList().get(0);
		} catch (IOException e) {
			logger.error(ERROR_MSG_202, e);
			throw new RuntimeException(ERROR_MSG_202);
		}
	}
	
	/**
	 * 列出当前用户下所有的bucket
	 * @return BucketList
	 * @throws IOException
	 */
	@Override
	public BucketList listBuckets() {
		String url = baseurl+this.appName + "/";
		HttpURLConnection conn = getConnection(url, internal_timeout, GET, getPath(url), null);
		BucketResponseProcesser bucketResponseProcesser = null;
		BucketList bl = new BucketList();
		try{
			bucketResponseProcesser = doBucketOperation(conn);
			List<String> list = Arrays.asList(new String(bucketResponseProcesser.getBodyContent()).split("\n"));
			bl.setBuckets(list);
			bl.setBucketsCount(Integer.parseInt(bucketResponseProcesser.getResponseHeaders().get(MetaHeaders.CONTAINERCOUNT).get(0).toString()));
			bl.setBucketsTotalObjectCount(Integer.parseInt(bucketResponseProcesser.getResponseHeaders().get(MetaHeaders.ALLOBJECTCOUNT).get(0).toString()));
			bl.setBucketsUsedSize(Integer.parseInt(bucketResponseProcesser.getResponseHeaders().get(MetaHeaders.ALLACCOUNTUSEDSIZE).get(0).toString()));
			bl.setTimestamp(bucketResponseProcesser.getResponseHeaders().get(MetaHeaders.TIMESTEMP).get(0).toString().trim());
		}catch(IOException e){
			logger.error(ERROR_MSG_103, e);
			throw new RuntimeException(ERROR_MSG_103);
		}
		return bl;
	}

	/**
	 * 列出提供的bucket下所有的object列表
	 * @param bucketName
	 * @return BucketList
	 * @throws RuntimeException "the value of parameter  can not be empty!"
	 * @throws IOException
	 */
	@Override
	public ObjectFileList getBucket(String bucketName) {
		return getBucket(bucketName,null);
	}

	/**
	 * 列出提供的bucket下所有以 prefix 为前缀的object列表
	 * @param bucketName
	 * @param prefix
	 * @return ObjectFileList
	 * @throws RuntimeException "the value of parameter  can not be empty!"
	 * @throws IOException
	 */
	@Override
	public ObjectFileList getBucket(String bucketName, String prefix) {
		checkParameter(bucketName);
		String url  = baseurl+this.appName+"/"+bucketName+"/";
		if(prefix!=null && !"".equals(prefix)){
			url = url+"?prefix="+prefix;
		}
		ObjectResponseProcesser objectResponseProcesser = null;
		HttpURLConnection conn = getConnection(url, internal_timeout, GET, getPath(url), null);
		ObjectFileList ofl = new ObjectFileList();
		try {
			objectResponseProcesser = doObjectOperation(conn);
			ofl.setObjectCount(Integer.parseInt(objectResponseProcesser.getResponseHeaders().get(MetaHeaders.OBJECTCOUNT).get(0)));
			ofl.setTimestamp(objectResponseProcesser.getResponseHeaders().get(MetaHeaders.TIMESTEMP).get(0).toString().trim());
			ofl.setTotalObjectUsedSize(Integer.parseInt(objectResponseProcesser.getResponseHeaders().get(MetaHeaders.ALLCONTAINERUSEDSIZE).get(0)));
			List<String> list = Arrays.asList(new String(objectResponseProcesser.getBodyContent()).split("\n"));
			ofl.setObjectList(list);
		} catch (IOException e) {
			logger.error(ERROR_MSG_103, e);
			throw new RuntimeException(ERROR_MSG_103);
		}
		return ofl;
	}

	/**
	 * 删除指定的bucket
	 * @param bucketName
	 * @throws RuntimeException {"the value of parameter  can not be empty!","the bucket is not null","the bucket not found or has remove"}
	 * @return 是否删除成功
	 */
	@Override
	public boolean deleteBucket(String bucketName) {
		checkParameter(bucketName);
		String url  = baseurl+this.appName+"/"+bucketName+"/";
		BucketResponseProcesser bucketResponseProcesser = null;
		HttpURLConnection conn = getConnection(url, internal_timeout, DELETE, getPath(url), null);
		try {
			bucketResponseProcesser = doBucketOperation(conn);
			if(bucketResponseProcesser.getResponseCode() == 204){
				return true;
			}
			if(bucketResponseProcesser.getResponseCode() == 404){
				throw new RuntimeException(ERROR_MSG_203);
			}
		} catch (IOException e) {
			logger.error(ERROR_MSG_202, e);
			throw new RuntimeException(ERROR_MSG_202);
		}
		return false;
	}


	/**
	 * 上传文件到指定的bucket下，content为你需要写入的内容，并且以byte[] 形式，map参数设置acl，如果没有可以直接传null<br>
	 * 如果需要put到bucket下某个文件夹下请使用"aa/bb"的形式传入文件名
	 * @param bucketName
	 * @param fileName
	 * @param content
	 * @param map
	 * @return 是否上传成功
	 * @throws RuntimeException {"the value of parameter  can not be empty!","the bucket not found or has remove"}
	 */
	@Override
	public boolean putObjectFile(String bucketName, String fileName, byte[] content, Map<String, String> map) {
		checkParameter(bucketName,fileName);
		String url = baseurl+this.appName+"/"+bucketName+"/"+fileName;
		HttpURLConnection conn = getConnection(url, internal_timeout, PUT, getPath(url), map);
		ObjectResponseProcesser objectResponseProcesser = null;
		try {
			objectResponseProcesser = doObjectOperation(conn,content);
//			System.out.println("=================");
//			printHeader(objectResponseProcesser.getResponseHeaders());
//			System.out.println("response code is : " + objectResponseProcesser.getResponseCode());
//			System.out.println("response message is : "+ objectResponseProcesser.getResponseMessage());
//			System.out.println("===================");
			if(objectResponseProcesser.getResponseCode() == 201){
				return true;
			}
			if(objectResponseProcesser.getResponseCode() == 404){
				throw new RuntimeException(ERROR_MSG_202);
			}
		} catch (IOException e) {
			logger.error(ERROR_MSG_202, e);
			throw new RuntimeException(ERROR_MSG_202);
		}
		return false;
	}

	/**
	 * 上传文件到指定的bucket下，file需要已经上传到容器的某个位置，map参数设置acl，如果没有可以直接传null<br>
	 * 如果需要put到bucket下某个文件夹下请使用"aa/bb"的形式传入文件名
	 * @param bucketName
	 * @param file
	 * @param map
	 * @return 是否上传成功
	 * @throws RuntimeException {"the value of parameter  can not be empty!","file does not exist","the bucket not found or has remove"}
	 */
	@Override
	public boolean putObjectFile(String bucketName, String fileName, File file, Map<String, String> map) {
		checkParameter(bucketName,fileName);
		if(!file.exists()){
			throw new RuntimeException(ERROR_MSG_104);
		}
		try {
			FileInputStream fis = new FileInputStream(file);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			byte[] b = new byte[1024];
			int n;
			while((n = fis.read(b)) != -1){
				bos.write(b);
			}
			fis.close();
			bos.close();
			byte[] content = bos.toByteArray();
			return putObjectFile(bucketName.trim(),fileName,content,map);
		} catch (FileNotFoundException e) {
			logger.error(ERROR_MSG_301);
			throw new RuntimeException(ERROR_MSG_301);
		} catch (IOException e) {
			logger.error(ERROR_MSG_202, e);
			throw new RuntimeException(ERROR_MSG_202);
		}
	}

	/**
	 * 获取提供的bucket下对应文件名的内容，如果需要获取bucket下某个文件夹的文件请使用"aa/bb"的形式传入文件名
	 * @param bucketName
	 * @param objectFileName
	 * @return ObjectFile
	 * @throws RuntimeException {"the value of parameter  can not be empty!","object file does not exist in the bucket or has removed"}
	 */
	@Override
	public ObjectFile getObject(String bucketName, String objectFileName) {
		ObjectFile of = new ObjectFile();
		checkParameter(bucketName, objectFileName);
		String url = baseurl+this.appName + "/" + bucketName + "/" +objectFileName;
		ObjectResponseProcesser objectResponseProcesser = null;
		HttpURLConnection conn = getConnection(url, internal_timeout, GET, getPath(url), null);
		try {
			objectResponseProcesser = doObjectOperation(conn);
			of.setFileName(objectFileName);
			of.setContent(objectResponseProcesser.getBodyContent());
			of.setFileSize(Integer.parseInt(objectResponseProcesser.getResponseHeaders().get("Content-Length").get(0)));
		} catch (IOException e) {
			logger.error(ERROR_MSG_301);
			throw new RuntimeException(ERROR_MSG_301);
		}
		return of;
	}

	/**
	 * 删除指定的object
	 * @param bucketName
	 * @param objectFileName
	 * @return ObjectFile
	 * @throws RuntimeException {"the value of parameter  can not be empty!","object file does not exist in the bucket or has removed"}
	 */
	@Override
	public boolean deleteObject(String bucketName, String objectFileName) {
		checkParameter(bucketName, objectFileName);
		String url = baseurl+this.appName + "/" + bucketName + "/" +objectFileName;
		ObjectResponseProcesser objectResponseProcesser = null;
		HttpURLConnection conn = getConnection(url, internal_timeout, DELETE, getPath(url), null);
		try {
			objectResponseProcesser = doObjectOperation(conn);
			if(objectResponseProcesser.getResponseCode() == 204){
				return true;
			}
			if(objectResponseProcesser.getResponseCode() == 404){
				throw new RuntimeException(ERROR_MSG_301);
			}
		} catch (IOException e) {
			logger.error(ERROR_MSG_301);
			throw new RuntimeException(ERROR_MSG_301);
		}
		return false;
	}

	/**
	 * 获取一个object临时访问的URL
	 * 
	 * @param bucketName bucket的名称
	 * @param method http请求方法默认GET
	 * @param objectName object的名称
	 * @param seconds 存活时间，秒数
	 * @return url
	 */
	public String getTempUrl(String bucketName,String method,String objectName,int seconds){
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
		long expires = new Date().getTime()/1000+seconds;
		String path = "/v1/SAE_"+this.appName+"/"+bucketName + "/" + objectName;
		String sig_context = method + "\n" + expires +"\n" + URLDecoder.decode(path);
		String sig = StorageUtils.calcSignatureNotBase64("HmacSHA1",sig_context, this.secretKey);
		return "http://" + this.appName + "-" + bucketName + ".stor.sinaapp.com/" + objectName + "?" +"temp_url_sig=" +sig +"&" + "temp_url_expires=" + expires;
	}
}
