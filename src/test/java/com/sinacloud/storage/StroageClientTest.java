package com.sinacloud.storage;

import java.io.File;

import org.junit.Test;


public class StroageClientTest{
	
	public static StorageClient getClient(){
		return new StorageClient("testnero3","n44mkooxxj","04my02l404wzi031j1zlxji2xz1m3h4yx1zy13y3");
	}
	@Test
	public void test(){
		getClient().createBucket("testbucket");
		getClient().putObjectFile("testbucket", "xx.txt", "abcdefg".getBytes(), null);
		getClient().deleteObject("testbucket", "xx.txt");
		getClient().deleteBucket("testbucket");
	}
	
}
