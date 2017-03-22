package com.sinacloud.storage;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;


public class StroageClientTest{
	
	public static StorageClient getClient(){
		return new StorageClient("testnero3","n44mkooxxj","04my02l404wzi031j1zlxji2xz1m3h4yx1zy13y3");
	}
	@Test
	public void test(){
//		Map<String,String> map = new HashMap<String,String>();
//		map.put("Content-Type", "image/jpg");
//		getClient().putObjectFile("testbucket	", "1.jpg", new File("/root/1.jpg"), map);
//		System.out.println(getClient().getTempUrl("testbucket", "GET", "1.jpg", 5000));
//		getClient().createBucket("testbucket");
//		getClient().putObjectFile("testbucket", "xxx.txt", "abcdefg".getBytes(), null);
//		getClient().deleteObject("testbucket", "xx.txt");
//		getClient().deleteBucket("testbucket");
	}
	
}
