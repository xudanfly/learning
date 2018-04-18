package com;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
//启注解事务管理  
@EnableTransactionManagement
@MapperScan("com.jianeye.cuckoo3.webside.*.mapper")
public class Cuckoo3Application {
	
	
	public static void main(String[] args) {
		SpringApplication.run(Cuckoo3Application.class, args);
	}
	
    @Bean
	public EmbeddedServletContainerCustomizer containerCustomizer() {

	   return (container -> {
		    ErrorPage errorPage = new ErrorPage(Throwable.class, "/error/error.html");
    		ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/error/error-404.html");
	        ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error/error-500.html");

	        container.addErrorPages(errorPage,error404Page, error500Page);
	   });
	}
}
