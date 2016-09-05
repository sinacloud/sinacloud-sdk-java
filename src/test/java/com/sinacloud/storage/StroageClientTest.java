package com.sinacloud.storage;

import java.io.File;

import org.junit.Test;


public class StroageClientTest{
	
	public static StorageClient getClient(){
		return new StorageClient();
	}
	@Test
	public void test(){
		getClient().createBucket("testbucket");
		getClient().putObjectFile("testbucket", "xx.txt", "abcdefg".getBytes(), null);
		getClient().deleteObject("testbucket", "xx.txt");
		getClient().deleteBucket("testbucket");
	}
	
}
