package com.jianeye.cuckoo3.webside.util;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * HttpServletRequestUtil.java
 * @author 张孟志
 * @date 2016年7月1日
 * @caption 对HttpServletRequest提供帮助
 */
public class HttpServletRequestUtil {

	/**
	 * 获得客户端真实IP地址
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) { 
		 String ip = request.getHeader("x-forwarded-for"); 
		 if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
			 ip = request.getHeader("Proxy-Client-IP"); 
		 } 
		 if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
			 ip = request.getHeader("WL-Proxy-Client-IP"); 
		 } 
		 if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
			 ip = request.getRemoteAddr(); 
		 } 
		 return ip; 
	}
}
