package com.sinacloud.storage;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TimeZone;
import java.util.Map.Entry;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;


public class StorageUtils {
	public static final String DATE_FORMAT = "EEE',' d MMM yyyy HH:mm:ss 'GMT'";
	private static Logger logger = Logger.getLogger(StorageUtils.class.getName());

	/**
	 * 根据当前的时间戳，返回一个格式化的时间
	 * 
	 * @return 一个格式化后的时间,此处返回的是格林威治时间
	 */
	public static String getTimeStamp() {
		long time = System.currentTimeMillis();
		Date date = new Date(time);
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, Locale.US);
		sdf.setTimeZone(TimeZone.getTimeZone("GMT")); // 设置时区为GMT
		return sdf.format(date);
	}

	/**
	 * 将指定的内容进行加密，默认采用HmacSHA1方式加密
	 * 
	 * @param content
	 *            需要加密的内容
	 * @param secretKey
	 *            加密所需的Key
	 * @return 加密后的数据
	 */
	public static String calcSignature(String content, String secretKey) {
		return calcSignature("HmacSHA1", content, secretKey);
	}

	/**
	 * 将指定的内容进行加密，默认采用HmacSHA1方式加密，加密后再对内容进行Base64编码
	 * 
	 * @param cryptoType
	 *            加密的类型
	 * @param content
	 *            需要加密的内容
	 * @param secretKey
	 *            加密所需的Key
	 * @return 加密后的数据
	 */
	public static String calcSignature(String cryptoType, String content, String secretKey) {
		try {
			Mac mac = Mac.getInstance(cryptoType);
			SecretKeySpec secret = new SecretKeySpec(secretKey.getBytes(), cryptoType);
			mac.init(secret);
			byte[] digest = mac.doFinal(content.getBytes());
			sun.misc.BASE64Encoder encode = new sun.misc.BASE64Encoder();
			return encode.encode(digest);
		} catch (Exception e) {
			logger.error("calc signature failure.", e);
			return "";
		}
	}

	/**
	 * 对指定内容进行BASE64编码
	 * 
	 * @param encodeContent
	 *            需要编码的内容
	 * @return 编码后的字符串
	 */
	public static String encodeBase64(String encodeContent) {
		sun.misc.BASE64Encoder encode = new sun.misc.BASE64Encoder();
		return encode.encode(encodeContent.getBytes());
	}

	/**
	 * 对位空的url进行判断，过滤掉空字符
	 * 
	 * @param url
	 * @return
	 */
	public static String trimUrl(String url) {
		if (url == null || "".equals(url)) {
			throw new IllegalArgumentException("url is null");
		}
		return url.trim();
	}

	/**
	 * 判断指定的字符串是否为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		if (str == ""  ||str==null) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 格式化文件名，将文件名转为UTF-8编码，并对'/'进行过滤
	 * 
	 * @param fileName
	 *            需要格式化的文件名
	 * @return 格式化后的名称
	 */
	public static String formatFileName(String fileName) {
		try {
			String name = fileName.trim();
			String encodeName = new String(name.getBytes(), "UTF-8");
			encodeName = encodeName.replaceAll("/+", "/");
			// 判断'/'是否出现在第一位
			if (encodeName.indexOf("/") == 0) {
				encodeName = encodeName.substring(1, encodeName.length());
			}
			return URLEncoder.encode(encodeName, "utf-8").replaceAll("%2F", "/");
			// return encodeName.trim().replaceAll(" ", "%20");
		} catch (Exception e) {
			logger.error("format file name failure.", e);
			return "";
		}
	}

	/**
	 * 将文件写入磁盘
	 * 
	 * @param path
	 *            需要写入的临时地址
	 * @param content
	 *            文件的内容
	 * @param size
	 *            将多少内容写入磁盘
	 * @return 写入的文件路径
	 */
	public static String writeFile(String path, byte[] content, int size) {
		createDirIfNotExist(path);
		File file = new File(path);
		String childFile = Integer.toString(new Random().nextInt());
		File f = new File(file, childFile);
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			;
			if (size > -1) {
				byte[] newBuf = new byte[size];
				if (size > content.length) {
					size = content.length;
				}
				System.arraycopy(content, 0, newBuf, 0, size);
				content = newBuf;
			}
			baos.write(content);
			FileOutputStream fos = new FileOutputStream(f);
			fos.write(baos.toByteArray());
			baos.close();
			fos.close();
		} catch (Exception e) {
			logger.error("write file failure.", e);
		}
		return f.getAbsolutePath();
	}

	/**
	 * 将文件压缩后写入磁盘
	 * 
	 * @param path
	 *            需要写入的临时地址
	 * @param content
	 *            文件的内容
	 * @param size
	 *            将多少内容写入磁盘
	 * @return 写入的文件路径
	 */
	public static String writeFileWithCompress(String path, byte[] content, int size) {
		createDirIfNotExist(path);
		File file = new File(path);
		String childFile = Integer.toString(new Random().nextInt());
		File f = new File(file, childFile);
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			GZIPOutputStream gos = new GZIPOutputStream(baos);
			if (size > -1) {
				byte[] newBuf = new byte[size];
				if (size > content.length) {
					size = content.length;
				}
				System.arraycopy(content, 0, newBuf, 0, size);
				content = newBuf;
			}
			gos.write(content);
			gos.finish();
			FileOutputStream fos = new FileOutputStream(f);
			fos.write(baos.toByteArray());
			baos.close();
			gos.close();
			fos.close();
		} catch (Exception e) {
			logger.error("write file(compress) failure.", e);
		}
		return f.getAbsolutePath();
	}

	/**
	 * 如果目录不存在则创建目录
	 * 
	 * @param dir
	 *            需要检查的目录
	 */
	public static void createDirIfNotExist(String dir) {
		File file = new File(dir);
		if (!file.exists()) {
			file.mkdirs();
		}
	}

	/**
	 * 将Map<String,Object>数据转为json格式字符串
	 * 
	 * @param map
	 *            需要转换的Map<String,Object>
	 * @return json格式的字符串
	 */
	public static String jsonEncodeforObjectMap(Map<String, Object> map) {
		JSONObject json = new JSONObject(map);
		return json.toString();
	}

	public static String jsonEncode(List<String> list) {
		JSONArray result = new JSONArray(list);
		return result.toString();
	}

	/**
	 * 将caclDomainAttr 产生的map 转换成 适合post 的 适合map(K-V)
	 * 
	 * @param map
	 * @return
	 */
	public static Map<String, Object> domainAttrToHeader(Map<String, Object> map) {

		Map<String, Object> newMap = new HashMap<String, Object>();
		if (!(Boolean) map.get("private")) {
			newMap.put("X-Container-Read", ".r:*");

			newMap.put("X-Container-Meta-Web-Listings", "false");
		} else {
			// System.out.println(" map.get(private)" + map.get("private"));
			newMap.put("X-Container-Read", "0");
			newMap.put("X-Container-Meta-Web-Listings", "true");
		}

		Iterator<String> it = map.keySet().iterator();
		while (it.hasNext()) {
			String key = (String) it.next();

			if (key.equals("quotaLevel")) {
				newMap.put("X-Container-Meta-Quota", map.get(key));
			}
			if (key.equals("404Redirect")) {
				newMap.put("X-Container-Meta-Web-Error", map.get(key));
			}
			if (key.equals("expires")) {

				if (map.get(key) != null) {
					@SuppressWarnings("unchecked")
					Map<String, Object> map_expires = (Map<String, Object>) map.get("expires");

					int num = (Integer) map_expires.get("active"); // 可能有问题

					if (num == 1) {
						String expiresStr = "";
						String expire_type = (String) map_expires.get("default");
						if (expire_type != null && expire_type != "") {
							if (expire_type.equals("access")) {
								expiresStr = "";
							} else if (expire_type.equals("modified")) {
								expiresStr = "modified ";
							}
							if (map_expires.get("expires_time") != null) {
								expiresStr += map_expires.get("expires_time");
							}
						}

						newMap.put("X-Container-Meta-Expires", expiresStr);

					} else {

						newMap.put("X-Container-Meta-Expires", "off");
					}

				} else {
					newMap.put("X-Container-Meta-Expires", "off");
				}

			}
			if (key.equals("allowReferer")) {
				@SuppressWarnings("unchecked")
				Map<String, Object> map_refer = (Map<String, Object>) map.get("allowReferer");
				@SuppressWarnings("unchecked")
				List<String> hosts = (List<String>) map_refer.get("hosts");
				if (hosts != null) {
					String strtem = "";
					List<String> temlist = new ArrayList<String>();
					for (int i = 0; i < hosts.size(); i++) {
						if (hosts.get(i) != null) {
							String tem = ".r:" + hosts.get(i);
							temlist.add(tem);
						}
					}
					if (map_refer.get("redirect") != null) {
						String tem2 = ".r:" + map_refer.get("redirect");
						temlist.add(tem2);
					}

					for (int j = 0; j < temlist.size(); j++) {
						if (j < temlist.size() - 1) {
							strtem += (String) temlist.get(j) + ",";
						} else {
							strtem += (String) temlist.get(j);
						}
					}
					newMap.put("X-Container-Read", strtem);
				}
			}
			if (key.equals("tag")) {
				@SuppressWarnings("unchecked")
				List<String> list = (List<String>) map.get(key);
				newMap.put("X-Container-Meta-Tags", list.toString());
			}

		}
		return newMap;

	}

	/**
	 * 解析文件属性，文件属性只支持expires_type,expires_time,encoding,type三种类型,增加了private 属性
	 * expires_type 两种 access || modified,默认access
	 * 
	 * @param map
	 *            需要检查的键值对
	 * @return 检查后的键值对
	 */
	public static Map<String, Object> parseFileAttr(Map<String, String> map) {
		Set<Entry<String, String>> entry = map.entrySet();
		Iterator<Entry<String, String>> iter = entry.iterator();
		Map<String, Object> newMap = new HashMap<String, Object>();
		Map<String, Object> expires = new LinkedHashMap<String, Object>();
		while (iter.hasNext()) {
			Entry<String, String> entryValue = iter.next();
			String key = entryValue.getKey().toLowerCase();
			if ("expires_type".equals(key)) {
				expires.put("expires_type", entryValue.getValue());
				// newMap.put("expires_type", entryValue.getValue());
			}
			if ("expires_time".equals(key)) {
				expires.put("expires_time", entryValue.getValue());
				// newMap.put("expires_time", entryValue.getValue());
			} else if ("encoding".equals(key)) {
				newMap.put("encoding", entryValue.getValue());
			} else if ("type".equals(key)) {
				newMap.put("type", entryValue.getValue());
			} else if ("private".equals(key)) {
				newMap.put("private", entryValue.getValue());
			}
		}
		if (expires != null) {
			newMap.put("expires", expires);
		}
		return newMap;
	}

	/**
	 * 将parseFileAttr产生的map 转成 header 识别的格式
	 * 
	 * @param map
	 *            parseFileAttr产生的map格式
	 * @return 适合设置header 的map 格式
	 */
	public static Map<String, String> fileAttrToHeader(Map<String, Object> map) {

		Map<String, String> newMap = new HashMap<String, String>();
		Iterator<Entry<String, Object>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, Object> entry = (Entry<String, Object>) it.next();
			String key = entry.getKey().toLowerCase();
			if ("expires".equals(key)) {
				@SuppressWarnings("unchecked")
				Map<String, Object> expiresMap = (Map<String, Object>) entry.getValue();
				String expires = "";
				if (null != expiresMap) {
					String type = (String) expiresMap.get("expires_type");
					if (type != null && type.equals("modified")) {
						expires = "modified ";
					} else if (type != null && type.equals("access")) {
						expires = "";
					}
					if (expiresMap.get("expires_time") != null) {
						expires += expiresMap.get("expires_time");
					}
				}

				newMap.put("X-Object-Meta-Expires", expires);
			} else if ("encoding".equals(key)) {
				newMap.put("Content-Encoding", (String) entry.getValue());
			} else if ("type".equals(key)) {
				newMap.put("Content-Type", (String) entry.getValue());
			}
			// else if("private".equals(key))
			// {
			// newMap.put("private", entry.getValue());
			// }
		}

		return newMap;
	}

	/**
	 * 解压缩 (测试使用)，查看GZIP编码后文件是否正确
	 * 
	 * @param str
	 * @return
	 */
	public static byte[] uncompress(byte[] str) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteArrayInputStream in = new ByteArrayInputStream(str);
		GZIPInputStream gunzip;
		try {
			gunzip = new GZIPInputStream(in);
			byte[] buffer = new byte[256];
			int n;
			while ((n = gunzip.read(buffer)) >= 0) {
				out.write(buffer, 0, n);
			}
			return out.toByteArray();
		} catch (IOException e) {

			logger.error("uncompress failure", e);
			return new byte[10];
		}

	}

	/**
	 * 将需要上传的文件进行压缩，压缩编码采用GZIP格式
	 * 
	 * @param parentFile
	 *            压缩文件时使用的临时路径
	 * @param srcFile
	 *            需要压缩的源文件
	 * @return 压缩成功后的路径地址
	 */
	public static String uploadFileWithCompress(String parentFile, String srcFile) {
		File file = new File(parentFile);
		String childFile = Integer.toString(new Random().nextInt());
		File f = new File(file, childFile);
		try {
			File src = new File(srcFile);
			// 从文件中 读取srcFile
			FileInputStream fis = new FileInputStream(src);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			GZIPOutputStream gos = new GZIPOutputStream(baos);
			byte[] buf = new byte[1024];
			int len = -1;
			while ((len = fis.read(buf)) != -1) {
				gos.write(buf, 0, len);
			}
			gos.finish();
			FileOutputStream fos = new FileOutputStream(f);
			fos.write(baos.toByteArray());
			fos.close();
			baos.close();
			gos.close();
			fis.close();
		} catch (Exception e) {
			logger.error("upload file failure.", e);
		}
		return f.getAbsolutePath();
	}

	/**
	 * 将输入流InputStream转换为String
	 * 
	 * @param is
	 *            InputStream
	 * @return 字符串
	 */
	public static String convertStreamToString(InputStream is) {
		/*
		 * To convert the InputStream to String we use the
		 * BufferedReader.readLine() method. We iterate until the BufferedReader
		 * return null which means there's no more data to read. Each line will
		 * appended to a StringBuilder and returned as String.
		 */
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return sb.toString();
	}

	/**
	 * 获取响应头
	 * 
	 * @param http
	 *            HttpURLConnection
	 * @return string
	 * @throws UnsupportedEncodingException
	 */
	public static String printResponseHeader(HttpURLConnection http) {
		Map<String, String> header;
		try {
			header = getHttpResponseHeader(http);
			StringBuffer jbsb = new StringBuffer();
			for (Map.Entry<String, String> entry : header.entrySet()) {
				String key = entry.getKey() != null ? entry.getKey() + ":" : "";
				jbsb.append(key + entry.getValue());
				System.out.println(key + entry.getValue());
			}
			return jbsb.toString();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			logger.error("printResponseHeader failure", e);
			return "";
		}
	}

	/**
	 * 将HttpURLConnection中的header解析为map格式
	 * 
	 * @param http
	 *            HttpURLConnection
	 * @return map LinkedHashMap 格式的header
	 * @throws UnsupportedEncodingException
	 */
	public static Map<String, String> getHttpResponseHeader(HttpURLConnection http)
			throws UnsupportedEncodingException {
		Map<String, String> header = new LinkedHashMap<String, String>();
		for (int i = 0;; i++) {
			String mine = http.getHeaderField(i);
			if (mine == null)
				break;
			header.put(http.getHeaderFieldKey(i), mine);
		}
		return header;
	}

	/**
	 * 将inputstream转换成byte数组
	 * 
	 * @param input
	 * @return
	 * @throws IOException
	 */
	public static byte[] toByteArray(InputStream input) {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		byte[] buffer = new byte[4096];
		int n = 0;
		try {
			while (-1 != (n = input.read(buffer))) {
				output.write(buffer, 0, n);
			}
			return output.toByteArray();
		} catch (IOException e) {
			logger.error("toByteArray(InputStream input) failure", e);
			return new byte[10];
		}

	}
}
