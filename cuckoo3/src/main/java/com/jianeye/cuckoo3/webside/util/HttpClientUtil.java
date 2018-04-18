package com.jianeye.cuckoo3.webside.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * 
 * HttpClientUtil.java
 * @author 张孟志
 * @date 2016年7月26日
 * @caption HTTP请求工具类
 */
public class HttpClientUtil {
	
//	public static void main(String[] args) {
//		System.out.println("--------------------------------------"); 
//		// 测试Get请求
//		String getRtn = HttpClientUtil.get("http://localhost:8080/TBP/sypt/simpleGet");
//		System.out.println(getRtn);
//
//		System.out.println("--------------------------------------"); 
//		// 测试POST请求
//		Map<String, String> parameter = new HashMap<String, String>();
//		parameter.put("1", "Hello");
//		parameter.put("2", "World");		
//		String postRtn = HttpClientUtil.post("http://localhost:8080/TBP/sypt/simplePost", parameter);
//		System.out.println(postRtn);
//		
//	}
	
	
	/** 
     * 发送 get请求 
     */  
    public static String get(String url) { 
    	String rtn = null;
    	
        CloseableHttpClient httpclient = HttpClients.createDefault();  
        try {  
            // 创建httpget.    
            HttpGet httpget = new HttpGet(url);  
            // System.out.println("executing request " + httpget.getURI());  
            // 执行get请求.    
            CloseableHttpResponse response = httpclient.execute(httpget);  
            try {  
                // 获取响应实体    
                HttpEntity entity = response.getEntity();  
                // System.out.println("--------------------------------------");  
                // 打印响应状态    
                // System.out.println(response.getStatusLine());  
                if (entity != null) {  
                    // 打印响应内容长度    
                	// System.out.println("Response content length: " + entity.getContentLength());  
                    // 打印响应内容    
                	// System.out.println("Response content: " + EntityUtils.toString(entity));
                	rtn = EntityUtils.toString(entity);
                }  
                // System.out.println("------------------------------------");  
            } finally {  
                response.close();  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
        	closeHttpClient(httpclient);
        }
        
        return rtn;
    }  
    
    /** 
     * 发送 post请求访问本地应用并根据传递参数不同返回不同结果 
     */  
    public static String post(String url, Map<String, String> parameter) { 
    	String rtn = null;
    	
        // 创建默认的httpClient实例.    
        CloseableHttpClient httpclient = HttpClients.createDefault();  
        // 创建httppost    
        HttpPost httppost = new HttpPost(url);  
        // 创建参数队列    
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();  
        for (Map.Entry<String, String> entry : parameter.entrySet()) {
        	formparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));  
        }        
        
        UrlEncodedFormEntity uefEntity;  
        try {  
            uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");  
            httppost.setEntity(uefEntity);  
            // System.out.println("executing request " + httppost.getURI());  
            CloseableHttpResponse response = httpclient.execute(httppost);  
            try {  
                HttpEntity entity = response.getEntity();  
                if (entity != null) {  
                	// System.out.println("--------------------------------------");  
                	// System.out.println("Response content: " + EntityUtils.toString(entity, "UTF-8"));
                	rtn = EntityUtils.toString(entity, "UTF-8");
                	// System.out.println("--------------------------------------");  
                }  
            } finally {  
                response.close();  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
        	closeHttpClient(httpclient);
        }  
        
        return rtn;
    }
    
    /**
     * 关闭资源
     * @param httpclient
     */
    private static void closeHttpClient(CloseableHttpClient httpclient) {
    	if(httpclient != null){
    		// 关闭连接,释放资源    
            try {  
                httpclient.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
    	}
    }
}
