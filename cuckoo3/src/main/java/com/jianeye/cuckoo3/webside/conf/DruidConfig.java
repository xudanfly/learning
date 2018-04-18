package com.jianeye.cuckoo3.webside.conf;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.RegexpMethodPointcutAdvisor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.logging.LogFilter;
import com.alibaba.druid.filter.logging.Slf4jLogFilter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.alibaba.druid.support.spring.stat.DruidStatInterceptor;
import com.alibaba.druid.wall.WallFilter;

/**
 * @author xudan
 *	配置druid监控 
 */
@Configuration
public class DruidConfig {
	
	private static final Logger logger = LoggerFactory.getLogger(ShiroConfig.class);
	
    @Value("${spring.datasource.url}")  
    private String dbUrl;  
      
    @Value("${spring.datasource.username}")  
    private String username;  
      
    @Value("${spring.datasource.password}")  
    private String password;  
      
    @Value("${spring.datasource.driverClassName}")  
    private String driverClassName;  
      
    @Value("${spring.datasource.initialSize}")  
    private int initialSize;  
      
    @Value("${spring.datasource.minIdle}")  
    private int minIdle;  
      
    @Value("${spring.datasource.maxActive}")  
    private int maxActive;  
      
    @Value("${spring.datasource.maxWait}")  
    private int maxWait;  
      
    @Value("${spring.datasource.timeBetweenEvictionRunsMillis}")  
    private int timeBetweenEvictionRunsMillis;  
      
    @Value("${spring.datasource.minEvictableIdleTimeMillis}")  
    private int minEvictableIdleTimeMillis;  
      
    @Value("${spring.datasource.validationQuery}")  
    private String validationQuery;  
      
    @Value("${spring.datasource.testWhileIdle}")  
    private boolean testWhileIdle;  
      
    @Value("${spring.datasource.testOnBorrow}")  
    private boolean testOnBorrow;  
      
    @Value("${spring.datasource.testOnReturn}")  
    private boolean testOnReturn;  
      
    @Value("${spring.datasource.poolPreparedStatements}")  
    private boolean poolPreparedStatements;  
      
    @Value("${spring.datasource.maxPoolPreparedStatementPerConnectionSize}")  
    private int maxPoolPreparedStatementPerConnectionSize;  
    
    @Value("${spring.datasource.maxOpenPreparedStatements}")  
    private int maxOpenPreparedStatements;  
    
    //<!-- 打开removeAbandoned功能 -->
    @Value("${spring.datasource.removeAbandoned}")  
    private boolean removeAbandoned;  
    
    //<!-- 1800秒，也就是30分钟,超过30分钟,连接会自动关闭 -->
    @Value("${spring.datasource.removeAbandonedTimeout}")  
    private int removeAbandonedTimeout;  
    
    //<!-- 关闭abanded连接时输出错误日志 -->
    @Value("${spring.datasource.logAbandoned}")  
    private boolean logAbandoned;  

    @Value("${spring.datasource.filters}")  
    private String filters;  
      
    @Value("{spring.datasource.connectionProperties}")  
    private String connectionProperties;  
    
    @Value("{spring.datasource.useGlobalDataSourceStat}")  
    private String useGlobalDataSourceStat; 
      
    @Bean(destroyMethod = "close", initMethod = "init")     //声明其为Bean实例  
    @Primary  //在同样的DataSource中，首先使用被标注的DataSource  
    public DataSource dataSource(){  
        DruidDataSource datasource = new DruidDataSource();  
          
        datasource.setUrl(this.dbUrl);  
        datasource.setUsername(username);  
        datasource.setPassword(password);  
        datasource.setDriverClassName(driverClassName);  
          
        //configuration  
        datasource.setInitialSize(initialSize);  
        datasource.setMinIdle(minIdle);  
        datasource.setMaxActive(maxActive);  
        datasource.setMaxWait(maxWait);  
        datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);  
        datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);  
        datasource.setValidationQuery(validationQuery);  
        datasource.setTestWhileIdle(testWhileIdle);  
        datasource.setTestOnBorrow(testOnBorrow);  
        datasource.setTestOnReturn(testOnReturn);  
        datasource.setPoolPreparedStatements(poolPreparedStatements);  
        datasource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);  
        datasource.setMaxOpenPreparedStatements(maxOpenPreparedStatements);
        datasource.setRemoveAbandoned(removeAbandoned);
        datasource.setRemoveAbandonedTimeout(removeAbandonedTimeout);
        datasource.setLogAbandoned(logAbandoned);
        try {  
            datasource.setFilters(filters);  
        } catch (SQLException e) {  
            logger.error("druid configuration initialization filter", e);  
        }  
        List<Filter> proxyFilters = new ArrayList<Filter>();
    	proxyFilters.add(statfilter());//统计
    	proxyFilters.add(logFilter());//日志
    	proxyFilters.add(wallFilter());//防火墙
    	datasource.setProxyFilters(proxyFilters);
        datasource.setConnectionProperties(connectionProperties);  
        datasource.setUseGlobalDataSourceStat(Boolean.parseBoolean(useGlobalDataSourceStat));  
        return datasource;  
    }  
    
   @Bean(name="logFilter")
   public LogFilter logFilter(){
	   LogFilter logFilter = new Slf4jLogFilter();
	   logFilter.setStatementExecutableSqlLogEnable(false);
	   return logFilter;
   }
   
   @Bean(name="wallFilter")
   public WallFilter wallFilter(){
	   WallFilter wallFilter = new WallFilter();
	   wallFilter.setDbType("mysql");
	   wallFilter.setLogViolation(true);
	   wallFilter.setThrowException(false);
	   return wallFilter;
   }
    
    @Bean(name="statfilter") 
    public StatFilter statfilter(){
    	StatFilter statfilter = new StatFilter();
    	statfilter.setSlowSqlMillis(30000);
    	statfilter.setLogSlowSql(true);
    	statfilter.setMergeSql(true);
    	statfilter.setDbType("mysql");
    	return statfilter;
    }
	
    @Bean(name="DruidWebStatFilter")
    public FilterRegistrationBean druidWebStatFilter(){
    	FilterRegistrationBean filter = new FilterRegistrationBean();
    	filter.setFilter(new WebStatFilter());
    	filter.setName("DruidWebStatFilter");
    	filter.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
    	filter.addInitParameter("principalCookieName", "webside.cookieid");
    	filter.addInitParameter("principalSessionName","userSession");
    	filter.addInitParameter("sessionStatMaxCount", "1000");
    	filter.addInitParameter("sessionStatEnable", "true");
    	filter.addInitParameter("profileEnable", "true");
    	filter.addUrlPatterns("/*");
    	return filter;
    }
    
    @Bean(name="DruidStatView")
    public ServletRegistrationBean statViewServletBean(){
    	ServletRegistrationBean servlet = new ServletRegistrationBean(new StatViewServlet(),"/druid/*");
    	servlet.addInitParameter("resetEnable", "true");
    	servlet.addInitParameter("loginUsername", "druid");
    	servlet.addInitParameter("loginPassword", "druid");
    	servlet.addInitParameter("allow", "127.0.0.1/24,127.0.0.1");
    	servlet.addInitParameter("deny", "192.168.0.1");
    	return servlet;
    }
    
    /**
     * druid的spring监控
     */
    @Bean
    public DruidStatInterceptor druidStatInterceptor(){
    	return new DruidStatInterceptor();
    }
    
    /*@Bean
    public JdkRegexpMethodPointcut druidStatPointcut(){
    	JdkRegexpMethodPointcut druidStatPointcut = new JdkRegexpMethodPointcut();
    	druidStatPointcut.setPatterns("com.jianeye.cuckoo3.webside.*.service.*","com.jianeye.cuckoo3.webside.*.mapper.*");
    	return druidStatPointcut;
    }
    
    @Bean
    public DefaultPointcutAdvisor pointcutAdvisor(){
    	DefaultPointcutAdvisor pointcutAdvisor = new DefaultPointcutAdvisor();
    	pointcutAdvisor.setAdvice(druidStatInterceptor());
    	pointcutAdvisor.setPointcut(druidStatPointcut());
    	return pointcutAdvisor;
    }*/
    
   @Bean
   public RegexpMethodPointcutAdvisor pointcutAdvisor(){
    	RegexpMethodPointcutAdvisor pointcutAdvisor = new RegexpMethodPointcutAdvisor();
    	pointcutAdvisor.setAdvice(druidStatInterceptor());
    	pointcutAdvisor.setPatterns("com.jianeye.cuckoo3.webside.*.service.*","com.jianeye.cuckoo3.webside.*.mapper.*");
    	return pointcutAdvisor;
    }
}
