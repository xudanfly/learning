/**
 * 
 */
package com.jianeye.cuckoo3.webside.conf;

import org.apache.sirona.reporting.web.SironaController;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xd
 * sirona监控配置
 */
@Configuration
public class SironaConfig {
	 /**
     * Sirona监控配置
     *  org.apache.sirona.reporting.activated: true   #activated per default
     *  org.apache.sirona.reporting.mapping: /sirona  #path in the application default is /sirona
     * @return
     */
    @Bean(name="Sirona")
	public FilterRegistrationBean sironaController(){
		FilterRegistrationBean filter = new FilterRegistrationBean();
		filter.setName("Sirona");
		filter.setFilter(new SironaController());
		filter.addInitParameter("monitoring-mapping", "/sirona/");
		filter.addUrlPatterns("/sirona/*");
		return filter;
	}
}
