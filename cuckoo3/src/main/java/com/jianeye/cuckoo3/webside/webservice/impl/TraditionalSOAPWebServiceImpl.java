package com.jianeye.cuckoo3.webside.webservice.impl;

import javax.jws.WebParam;
import javax.jws.WebService;

import com.jianeye.cuckoo3.webside.webservice.TraditionalSOAPWebService;
import com.jianeye.cuckoo3.webside.webservice.WsConstants;

/**
 * 
 * @ClassName:TraditionalSOAPWebServiceImpl 
 * @Description:传统的SOAP WEB SERVICE服务实现
 * @author:张孟志
 * @date:2015-1-8 上午11:53:27 
 * @version V1.0
 * 说明：传统的SOAP WEB SERVICE服务实现
 */
//serviceName指明WSDL中<wsdl:service>与<wsdl:binding>元素的名称, endpointInterface属性指向Interface类全称.
@WebService(serviceName = "TraditionalSOAPWebService", 
	endpointInterface = "com.jianeye.cuckoo3.webside.webservice.TraditionalSOAPWebService", 
	targetNamespace = WsConstants.NS)
public class TraditionalSOAPWebServiceImpl implements TraditionalSOAPWebService {

	@Override
	public String doService(/*@WebParam(name = "message")*/ String message) {
		// 具体业务操作
		// 可以设计一个资源定位器，根据message中的某个标签（SERVICE_ID）查找服务处理类
		// 资源定位器可以为系统解耦，同时可以带来两个业务功能：一是兼容报文形式、二是定位服务
		
		return "你发起的报文：\n" + message;
	}

}

