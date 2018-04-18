package com.jianeye.cuckoo3.webside.webservice;

import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * 
 * @ClassName:TraditionalSOAPWebService 
 * @Description:传统的SOAP WEB SERVICE
 * @author:张孟志
 * @date:2015-1-8 上午11:12:15 
 * @version V1.0
 * 说明：JAX-WS2.0的WebService接口定义类 
 * 使用JAX-WS2.0 annotation设置WSDL中的定义.
 * 只返回字符串，根据具体业务定义字符串内容
 */
// name 指明wsdl中<wsdl:portType>元素的名称
@WebService(name = "TraditionalSOAPWebService", targetNamespace = WsConstants.NS)
public interface TraditionalSOAPWebService {
	
	/**
	 * 约定报文
	 * JSON/XML
	 * 提供统一的服务入口
	 */
	String doService(@WebParam(name = "message") String message);
	
}

