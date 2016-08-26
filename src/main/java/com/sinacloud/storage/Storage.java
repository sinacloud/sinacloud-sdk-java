package com.sinacloud.storage;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.sinacloud.storage.model.Bucket;
import com.sinacloud.storage.model.BucketList;
import com.sinacloud.storage.model.ObjectFile;
import com.sinacloud.storage.model.ObjectFileList;

/**
 *  SinaCloud 容器应用 storage 服务接口。
 *  <p>
 *  Storage是新浪云为开发者提供的分布式对象存储服务，旨在利用新浪云在分布式以及网络技术方面的优势为开发者提供安全、简单、高效的存储服务。<br>
 *  Storage支持文本、多媒体、二进制等任何类型的数据的存储。
 * 
 * @author nero
 */
public interface Storage {
	
	/**
	 * 创建一个新的bucket
	 * @param bucketName
	 * @return
	 */
	public boolean createBucket(String bucketName);
	
	/**
	 * 获取bucket相关的信息
	 * @param bucketName
	 * @return bucket
	 */
	public Bucket getBucketInfo(String bucketName);
	
	/**
	 * 列出当前用户下所有的bucket
	 * @return BucketList
	 */
	public BucketList listBuckets();
	
	/**
	 * 列出提供的bucket下所有的object列表
	 * @param bucketName
	 * @return
	 */
	public ObjectFileList getBucket(String bucketName);
	
	/**
	 * 列出提供的bucket下所有以 prefix 为前缀的object列表
	 * @param bucketName
	 * @param prefix
	 * @return
	 */
	public ObjectFileList getBucket(String bucketName,String prefix);
	
	/**
	 * 删除指定的bucket
	 * @param bucketName
	 */
	public boolean deleteBucket(String bucketName);
	
	
	/**
	 * 上传文件到指定的bucket下
	 * @param bucketName
	 * @param fileName
	 * @param content
	 * @param map
	 * @return
	 */
	public boolean putObjectFile(String bucketName,String fileName,byte[] content,Map<String,String> map);
	
	/**
	 * 上传文件到指定的bucket下
	 * @param bucketName
	 * @param file
	 * @param map
	 * @return
	 */
	public boolean putObjectFile(String bucketName,String fileName,File file,Map<String,String> map);
	
	/**
	 * 获取提供的bucket下对应文件名的内容
	 * @param bucketName
	 * @param objectFileName
	 * @return
	 */
	public ObjectFile getObject(String bucketName,String objectFileName);
	
	/**
	 * 删除指定的object
	 * @param bucketName
	 * @param objectFileName
	 * @return
	 */
	public boolean deleteObject(String bucketName,String objectFileName);
	
	
}
