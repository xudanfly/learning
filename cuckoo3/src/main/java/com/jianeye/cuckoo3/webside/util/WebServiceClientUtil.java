package com.jianeye.cuckoo3.webside.util;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;

/**
 * 
 * WebServiceClientUtil.java
 * @author 张孟志
 * @date 2016年7月26日
 * @caption Web Service请求工具类
 */
public class WebServiceClientUtil {	
	
//	/** 
//	 * @Title: main 
//	 * @Description:调用WEB SERVICE的客户端代码
//	 * @param args
//	 * @throws 
//	 * 增加人:张孟志
//	 * 增加日期:2015-1-8 下午12:01:06
//	 * 说明：JaxWsDynamicClientFactory动态调用
//	 */
//	public static void main(String[] args) {
//		String address = 
//				"http://localhost:8080/taxbankplatform/sypt/ws/soap/traditionalSOAPWebService?wsdl";
//		String message = 
//				"<ROOT><SERVICE_ID>USER.QUERY</SERVICE_ID></ROOT>";
//		
//		System.out.println("dynamicClient requ:");	
//    	System.out.println(message);
//		
//		// JaxWsDynamicClientFactory动态调用
//		String rtn = dynamicClient(address, message);
//
//		System.out.println("dynamicClient resp:");		
//    	System.out.println(rtn);
//	}	
	
	/**
	 * 
	 * @Title: dynamicClient 
	 * @Description: JaxWsDynamicClientFactory动态调用
	 * @param address  WS地址
	 * @param message  访问参数，一段约定好的报文
	 * @throws 
	 * 增加人:张孟志
	 * 增加日期:2016-7-27 下午1:51:00
	 * 说明：JaxWsDynamicClientFactory动态调用
	 * 		这种调用方式需要了解WS的参数设置，一般可以事先协商
	 * 		同时要求，WS服务方法名为doService，例如：
	 * 		String doService(@WebParam(name = "message") String message);
	 */
	public static String dynamicClient(String address, String message) {
		String rtn = null;
		JaxWsDynamicClientFactory  factory =JaxWsDynamicClientFactory.newInstance();
	    Client client =factory.createClient(address);
	    try {
	    	Object[] obj =client.invoke("doService",message);
	    	rtn = (String) obj[0];
		 } catch (Exception e) {
		  e.printStackTrace();
		 }
	    return rtn;
	}

}
