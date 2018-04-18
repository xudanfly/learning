package com.jianeye.cuckoo3.webside.conf;


import javax.xml.ws.Endpoint;

import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import com.jianeye.cuckoo3.webside.webservice.TraditionalSOAPWebService;
import com.jianeye.cuckoo3.webside.webservice.impl.TraditionalSOAPWebServiceImpl;

/**
 * Spring boot 整合CXF开发web service
 * @author xudan
 *
 */
@Configuration
@Lazy
public class CxfConfig {
    @Bean 
    public ServletRegistrationBean CXFServlet(){
    	return new ServletRegistrationBean(new CXFServlet(),"/sypt/ws/*");
    }
    
    @Bean(name = Bus.DEFAULT_BUS_ID)
    public SpringBus springBus() {
        return new SpringBus();
    }
    
    //<!-- WebService的实现Bean定义 -->
    @Bean 
    public TraditionalSOAPWebService traditionalSOAPWebService(){
    	return new TraditionalSOAPWebServiceImpl();
    }
    
   
    //<!-- jax-ws endpoint定义  -->
	//<!-- 注意address的前缀是web.xml中CXFServlet配置url-pattern  /sypt/ws/* -->
	//<!-- http://localhost:8080/TBP/sypt/ws/soap/traditionalSOAPWebService?wsdl -->
    @Bean
    public Endpoint endpoint() {
    	EndpointImpl endpoint = new EndpointImpl(springBus(),traditionalSOAPWebService());
    	endpoint.setAddress("/soap/traditionalSOAPWebService");
    	endpoint.publish("/soap/traditionalSOAPWebService");
        return endpoint;
    }
}
