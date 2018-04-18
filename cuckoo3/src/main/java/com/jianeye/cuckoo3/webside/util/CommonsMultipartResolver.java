package com.jianeye.cuckoo3.webside.util;

import javax.servlet.http.HttpServletRequest;

/**
 * 自定义commonsMultiparResolver放行百度编辑器的请求action
 * @author xudan
 *
 */
public class CommonsMultipartResolver extends org.springframework.web.multipart.commons.CommonsMultipartResolver {  
	  /**
	   * 用于过滤百度编辑器上传文件时阻止 commonsMultipartResolver 对文件进行包装
	   */  
	  @Override  
	  public boolean isMultipart(HttpServletRequest request) {  
		  String uri = request.getRequestURI(); 
		  //过滤使用百度UEditor的URI  
		  if (uri.indexOf("ueditor.html") > 0) {  
			  //System.out.println(uri+"------commonsMultipartResolver 放行");
			  return false;  
		  }  
		  //System.out.println(uri+"------commonsMultipartResolver 拦截");
		  return super.isMultipart(request);  
	}
}
