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
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

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
 *  使用方法，在容器环境中<br>
 *  StroageClient sc = new StroageClient();<br>
 *  sc.createBucket("example_bucket"');<br>
 *  sc.getErrmsg(); 获取错误的概要信息<br>
 *  sc.getErrno(); 获取错误码<br>
 *  errno： 0  成功<br>
 *  errno：-101 传入的参数为空 检查参数传入是否正确<br>
 *  errno：-102 URL访问异常，检查bucket和objectname是否正确，或者网络是否有异常<br>
 *  errno：-103 IO异常，检查下网络是否有问题，或者bucket和object是否存在<br>
 *  errno：-104 上传的文件不存在，检查下需要上传到storage的文件是否存在<br>
 *  errno：-201 bucket已经创建，请勿重复创建<br>
 *  errno：-202 bucket未找到，或者bucket已经被删除了，检查bucket是否正确<br>
 *  errno：-203 bucket不为空，如果要删除bucket请先删除bucket中的object，保证bucket为空<br>
 *  errno：-204 达到bucket创建数量的上限，不能在创建bucket<br>
 *  errno：-301 object不存在这个bucket中，或者已经被删除了<br>
 * 
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
	 * @return
	 */
	protected HttpsURLConnection getConnection(String url , int timeout, String method,String path,Map<String,String> propMap){
		HttpsURLConnection conn = null;
		URL _url = null;
		int code = 0;
		try {
			_url = new URL(url);
			conn = (HttpsURLConnection)_url.openConnection();
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
			e.printStackTrace();
//			logger.error(ERROR_MSG_102, e);
//			throw new RuntimeException(ERROR_MSG_102);
		} catch (IOException e) {
			e.printStackTrace();
//			logger.error(ERROR_MSG_103, e);
//			throw new RuntimeException(ERROR_MSG_103);
		}
		return conn;
	} 
	
	/**
	 * bucket相关操作
	 * @param conn
	 * @return
	 * @throws IOException
	 */
	private BucketResponseProcesser doBucketOperation(HttpsURLConnection conn) throws IOException{
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
	 * @return
	 * @throws IOException
	 */
	private BucketResponseProcesser doBucketOperation(HttpsURLConnection conn,String bucketName) throws IOException{
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
	 * @return
	 * @throws IOException
	 */
	private ObjectResponseProcesser doObjectOperation(HttpURLConnection conn) throws IOException{
		return doObjectOperation(conn,null);
	}
	
	/**
	 * 处理object相关的操作
	 * @param conn
	 * @param content
	 * @return
	 * @throws IOException
	 */
	private ObjectResponseProcesser doObjectOperation(HttpURLConnection conn,byte[] content) throws IOException{
		ObjectResponseProcesser objectResponseProcesser = new ObjectResponseProcesser();
		if(content != null){
			String BOUNDARY = Long.toHexString(System.currentTimeMillis());
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setRequestProperty("Content-Type",
					"multipart/form-data; boundary=" + BOUNDARY);
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
	 * @return
	 */
	@Override
	public boolean createBucket(String bucketName) {
		if(!checkParameter(bucketName)){
			throw new RuntimeException(ERROR_MSG_101);
		}
		String url = baseurl + this.appName + "/" + bucketName + "/";
		HttpsURLConnection conn = getConnection(url, internal_timeout, PUT, getPath(url), null);
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
	 * @return bucket
	 */
	@Override
	public Bucket getBucketInfo(String bucketName){
		if(!checkParameter(bucketName)){
			throw new RuntimeException(ERROR_MSG_101);
		}
		String url = baseurl + this.appName + "/" + bucketName + "/";
		HttpsURLConnection conn = getConnection(url, internal_timeout,HEAD, getPath(url), null);
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
	 */
	@Override
	public BucketList listBuckets() {
		String url = baseurl+this.appName + "/";
		HttpsURLConnection conn = getConnection(url, internal_timeout, GET, getPath(url), null);
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
//			logger.error(ERROR_MSG_103, e);
//			throw new RuntimeException(ERROR_MSG_103);
			e.printStackTrace();
		}
		return bl;
	}

	/**
	 * 列出提供的bucket下所有的object列表
	 * @param bucketName
	 * @return
	 */
	@Override
	public ObjectFileList getBucket(String bucketName) {
		return getBucket(bucketName,null);
	}

	/**
	 * 列出提供的bucket下所有以 prefix 为前缀的object列表
	 * @param bucketName
	 * @param prefix
	 * @return
	 */
	@Override
	public ObjectFileList getBucket(String bucketName, String prefix) {
		if(!checkParameter(bucketName)){
			throw new RuntimeException(ERROR_MSG_101);
		}
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
	 */
	@Override
	public boolean deleteBucket(String bucketName) {
		if(!checkParameter(bucketName)){
			throw new RuntimeException(ERROR_MSG_101);
		}
		String url  = baseurl+this.appName+"/"+bucketName+"/";
		BucketResponseProcesser bucketResponseProcesser = null;
		HttpsURLConnection conn = getConnection(url, internal_timeout, DELETE, getPath(url), null);
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
	 * 上传文件到指定的bucket下
	 * @param bucketName
	 * @param fileName
	 * @param content
	 * @param map
	 * @return
	 */
	@Override
	public boolean putObjectFile(String bucketName, String fileName, byte[] content, Map<String, String> map) {
		if(!checkParameter(bucketName,fileName)){
			throw new RuntimeException(ERROR_MSG_101);
		}
		String url = baseurl+this.appName+"/"+bucketName+"/"+fileName;
		HttpURLConnection conn = getConnection(url, internal_timeout, PUT, getPath(url), map);
		ObjectResponseProcesser objectResponseProcesser = null;
		try {
			objectResponseProcesser = doObjectOperation(conn,content);
			if(objectResponseProcesser.getResponseCode() == 201){
				return true;
			}
		} catch (IOException e) {
			logger.error(ERROR_MSG_202, e);
			throw new RuntimeException(ERROR_MSG_202);
		}
		return false;
	}

	/**
	 * 上传文件到指定的bucket下
	 * @param bucketName
	 * @param file
	 * @param map
	 * @return
	 */
	@Override
	public boolean putObjectFile(String bucketName, String fileName, File file, Map<String, String> map) {
		if(!checkParameter(bucketName,fileName)){
			throw new RuntimeException(ERROR_MSG_101);
		}
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
			return putObjectFile(bucketName,fileName,content,map);
		} catch (FileNotFoundException e) {
			logger.error(ERROR_MSG_301);
			throw new RuntimeException(ERROR_MSG_301);
		} catch (IOException e) {
			logger.error(ERROR_MSG_202, e);
			throw new RuntimeException(ERROR_MSG_202);
		}
	}

	/**
	 * 获取提供的bucket下对应文件名的内容
	 * @param bucketName
	 * @param objectFileName
	 * @return
	 */
	@Override
	public ObjectFile getObject(String bucketName, String objectFileName) {
		ObjectFile of = new ObjectFile();
		if(!checkParameter(bucketName, objectFileName)){
			return of;
		}
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
	 * @return
	 */
	@Override
	public boolean deleteObject(String bucketName, String objectFileName) {
		if(!checkParameter(bucketName, objectFileName)){
			throw new RuntimeException(ERROR_MSG_101);
		}
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

}
