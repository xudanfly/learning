package com.jianeye.cuckoo3.webside.conf;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.session.mgt.quartz.QuartzSessionValidationScheduler;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.filter.DelegatingFilterProxy;

import com.jianeye.cuckoo3.webside.shiro.ChainDefinitionSectionMetaSource;
import com.jianeye.cuckoo3.webside.shiro.LimitRetryHashedMatcher;
import com.jianeye.cuckoo3.webside.shiro.MyRealm;
import com.jianeye.cuckoo3.webside.shiro.ShiroCacheManagerWrapper;
import com.jianeye.cuckoo3.webside.shiro.filter.KickoutSessionFilter;
import com.jianeye.cuckoo3.webside.shiro.filter.SysUserFilter;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;

/**
 * spring MVC与shiro配置
 * @author xudan
 *
 */
@Configuration
@EnableCaching
@Lazy
public class ShiroConfig {
	private static final Logger logger = LoggerFactory.getLogger(ShiroConfig.class);
	
	@Value("${spring.datasource.redis.database}")  
    private String database;  
	
	@Value("${spring.datasource.redis.host}")  
    private String host;  
	
	@Value("${spring.datasource.redis.port}")  
    private int port;  
	
	@Value("${spring.datasource.expire}")  
    private int expire;  
	
	@Value("${spring.datasource.timeout}")  
    private int timeout;  
	
	@Value("${spring.datasource.password}")  
    private String password;  
	
	/* 注册DelegatingFilterProxy（Shiro）/Shiro配置
	配置Shiro过滤器,先让Shiro过滤系统接收到的请求
	这里filter-name必须对应applicationContext.xml中定义的<bean id="shiroFilter"/>
	使用[/*]匹配所有请求,保证所有的可控请求都经过Shiro的过滤
	通常会将此filter-mapping放置到最前面(即其他filter-mapping前面),以保证它是过滤器链中第一个起作用的 */
    @Bean
    public FilterRegistrationBean filterRegistrationBean(){
    	FilterRegistrationBean filter = new FilterRegistrationBean();
    	filter.setFilter(new DelegatingFilterProxy("shiroFilter"));
    	filter.addInitParameter("targetFilterLifecycle", "true");//该值缺省为false,表示生命周期由SpringApplicationContext管理,设置为true则表示由ServletContainer管理
    	filter.setEnabled(true);
    	filter.addUrlPatterns("/*");
    	/*EnumSet<javax.servlet.DispatcherType> dispatcherTypes = EnumSet.of(DispatcherType.FORWARD,DispatcherType.REQUEST,
    			DispatcherType.INCLUDE,DispatcherType.ERROR);
    	filter.setDispatcherTypes(dispatcherTypes);*/
    	return filter;
    }
	
	/**
	 * hashAlgorithmName必须的，没有默认值。可以有MD5或者SHA-1，如果对密码安全有更高要求可以用SHA-256或者更高。
		这里使用MD5 storedCredentialsHexEncoded默认是true，此时用的是密码加密用的是Hex编码；false时用Base64编码 
		hashIterations迭代次数，默认值是1。
	 * @return
	 */
	@Bean(name="credentialsMatcher")
	public LimitRetryHashedMatcher credentialsMatcher(ShiroCacheManagerWrapper shiroCacheManager){
		LimitRetryHashedMatcher credentialsMatcher = new LimitRetryHashedMatcher(shiroCacheManager);
		//!-- RetryLimitHashedCredentialsMatcher继承父类HashedCredentialsMatcher，需要给父类注入以下属性 -->
		credentialsMatcher.setHashAlgorithmName("MD5");
		credentialsMatcher.setHashIterations(2);
		//<!-- 这里需要和注册时使用的加密方式一样，都使用MD5加密，然后转为base64,密码匹配也需和注册时的加密方式一致 -->
		credentialsMatcher.setStoredCredentialsHexEncoded(false);
		return credentialsMatcher;
	}
	
	/**
	 * <!--自定义Realm -->
	 * @return
	 */
	@Bean(name="myRealm")
	@DependsOn(value={"lifecycleBeanPostProcessor", "shiroCacheManager"})
	public MyRealm getShiroRealm(LimitRetryHashedMatcher credentialsMatcher){
		MyRealm myRealm = new MyRealm();
		myRealm.setCredentialsMatcher(credentialsMatcher);
		//<!--缓存 -->
		myRealm.setCachingEnabled(true);
		myRealm.setAuthenticationCachingEnabled(false);
		myRealm.setAuthenticationCacheName("authenticationCache");
		myRealm.setAuthorizationCachingEnabled(true);
		myRealm.setAuthorizationCacheName("authorizationCache");
		return myRealm;
	}
	
	@Bean(name = "securityManager") 
	public DefaultWebSecurityManager securityManager(MyRealm myRealm,ShiroCacheManagerWrapper shiroCacheManager) {
        DefaultWebSecurityManager dwsm = new DefaultWebSecurityManager();
        dwsm.setRealm(myRealm); 
        dwsm.setSessionManager(shiroSessionManager());
        //<!-- 使用下面配置的缓存管理器 -->
        dwsm.setCacheManager(shiroCacheManager); 
        return dwsm;
    } 
	
	//<!-- 用户过滤器,将用户信息放入request中 -->
	@Bean
	public SysUserFilter sysUserFilter(){
		return new SysUserFilter();
	}
	
	//<!-- 用户登录数控制,超过最大回话数会强制退出 -->
	@Bean
	public KickoutSessionFilter kickoutSessionFilter(ShiroCacheManagerWrapper shiroCacheManager){
		KickoutSessionFilter kickoutSessionFilter = new KickoutSessionFilter();
		kickoutSessionFilter.setCacheManager(shiroCacheManager);
		kickoutSessionFilter.setSessionManager(shiroSessionManager());
		kickoutSessionFilter.setKickoutAfter(false);
		kickoutSessionFilter.setMaxSession(1);
		kickoutSessionFilter.setKickoutUrl("/login.html");
		return kickoutSessionFilter;
	}
	
	//<!-- 配置shiro的过滤器工厂类，这里bean的id shiroFilter要和我们在WebMvcConfig中配置的shior过滤器名称一致 -->
	@Bean(name="shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(DefaultWebSecurityManager securityManager,
    		KickoutSessionFilter kickoutSessionFilter,
    		ChainDefinitionSectionMetaSource cdsms) throws Exception {  
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean(); 
        //<!-- shiro的核心安全接口 -->
        shiroFilterFactoryBean.setSecurityManager(securityManager);  
        //<!-- 要求登录时的链接 --> 
        shiroFilterFactoryBean.setLoginUrl("/login.html"); 
        //<!-- 登陆成功后要跳转的连接 --> 
        shiroFilterFactoryBean.setSuccessUrl("/index.html"); 
        //<!-- 未授权时要跳转的连接 --> 
        shiroFilterFactoryBean.setUnauthorizedUrl("/denied.html");
        //<!-- shiro连接约束配置,在这里使用自定义的动态获取资源类 -->
        shiroFilterFactoryBean.setFilterChainDefinitionMap(cdsms.getObject());
        //<!-- 自定义过滤器 -->
        Map<String, Filter> filters = new HashMap<String,Filter>();
        filters.put("sysUser", sysUserFilter());
        filters.put("kickout", kickoutSessionFilter);
        shiroFilterFactoryBean.setFilters(filters);
        return shiroFilterFactoryBean;  
    }  
    
	@Bean
    public ChainDefinitionSectionMetaSource chainDefinitionSectionMetaSource() throws Exception{
    	ChainDefinitionSectionMetaSource cdsms = new ChainDefinitionSectionMetaSource();
    	StringBuffer filterChainDefinitions = new StringBuffer();
    	filterChainDefinitions.append("/css/** = anon\n");//换行
    	filterChainDefinitions.append("/fonts/** = anon\n");
    	filterChainDefinitions.append("/images/**  = anon\n");
    	filterChainDefinitions.append("/js/**  = anon\n");
    	filterChainDefinitions.append("/login.html=anon\n");
    	filterChainDefinitions.append("/logout.html=anon\n");
    	filterChainDefinitions.append("/register.html=anon\n");
    	filterChainDefinitions.append("/user/resetPassWithoutAuthc.html=anon\n");
    	filterChainDefinitions.append("/user/validateAccountName.html=anon\n");
    	filterChainDefinitions.append("/captcha.html=anon\n");
    	filterChainDefinitions.append("/denied.html=anon\n");
    	filterChainDefinitions.append("/error/**=anon\n");
    	filterChainDefinitions.append("/=anon\n");//<!--自定义税银平台的展示页面，不需要登录 。-->
    	filterChainDefinitions.append("/sypt/**=anon");
    	cdsms.setFilterChainDefinitions(filterChainDefinitions.toString());
    	return cdsms;
    }
    
    @Bean(name="sessionIdGenerator")
	public JavaUuidSessionIdGenerator sessionIdGenerator(){
		return  new JavaUuidSessionIdGenerator();
	}
    
    //<!-- 会话DAO -->
    @Bean(name="sessionDAO")
	public EnterpriseCacheSessionDAO sessionDAO(){
		EnterpriseCacheSessionDAO sessionDAO = new EnterpriseCacheSessionDAO();
		sessionDAO.setActiveSessionsCacheName("shiro-activeSessionCache");
		sessionDAO.setSessionIdGenerator(sessionIdGenerator());
		return sessionDAO;
	}
    
    //<!-- 会话验证调度器 -->
    //<!-- 全局的会话信息检测扫描信息间隔30分钟-->
	@Bean(name="sessionValidationScheduler")
	public QuartzSessionValidationScheduler sessionValidationScheduler(){
		QuartzSessionValidationScheduler sessionValidationScheduler = new QuartzSessionValidationScheduler();
		sessionValidationScheduler.setSessionValidationInterval(1800000);
		//sessionValidationScheduler.setSessionManager(shiroSessionManager());
		return sessionValidationScheduler;
	}
	
	@Bean(name="sessionIdCookie")
	public SimpleCookie sessionIdCookie(){
		SimpleCookie sessionIdCookie = new SimpleCookie("sid");
		sessionIdCookie.setHttpOnly(true);
		sessionIdCookie.setMaxAge(180000);
		return sessionIdCookie;
	}
	
	/**
	 * <!-- 会话管理器 -->
	   <!-- 全局的会话信息设置成30分钟,sessionValidationSchedulerEnabled参数就是是否开启扫描  -->
	 * @return
	 */
	@Bean(name="shiroSessionManager")
	public DefaultWebSessionManager shiroSessionManager(){
		DefaultWebSessionManager shiroSessionManager = new DefaultWebSessionManager();
		shiroSessionManager.setSessionIdCookieEnabled(true);
		shiroSessionManager.setSessionIdCookie(sessionIdCookie());
		shiroSessionManager.isDeleteInvalidSessions();
		shiroSessionManager.setSessionDAO(sessionDAO());//redisSessionDAO()
		shiroSessionManager.setSessionValidationSchedulerEnabled(true);
		shiroSessionManager.setGlobalSessionTimeout(1800000);
		shiroSessionManager.setSessionValidationInterval(1800000);
		shiroSessionManager.setSessionValidationScheduler(sessionValidationScheduler());
		return shiroSessionManager;
	}
	
	/**
	 * 缓存配置
	 * @return
	 */
	@Bean
	public EhCacheManagerFactoryBean ehCacheManagerFactory() {  
		EhCacheManagerFactoryBean ehCacheManagerFactory = new EhCacheManagerFactoryBean();
		ehCacheManagerFactory.setConfigLocation(new ClassPathResource("ehcache.xml")); //classpath:ehcache.xml
		ehCacheManagerFactory.setShared(true);
        return ehCacheManagerFactory; 
    } 
	
	@Bean(name="ehCacheManager")
	public EhCacheCacheManager ehCacheManager(EhCacheManagerFactoryBean bean){
		return new EhCacheCacheManager(bean.getObject());
	}
	
	/**
	 * <!--shiro缓存管理器 -->
	 * @return
	 */
	@Bean(name="shiroCacheManager")  
    public ShiroCacheManagerWrapper shiroCacheManager(EhCacheCacheManager cacheManager) {  
        /*EhCacheManager cacheManager = new EhCacheManager();  
        cacheManager.setCacheManagerConfigFile("classpath:ehcache.xml");  
        return cacheManager; */ 
        ShiroCacheManagerWrapper shiroCacheManager = new ShiroCacheManagerWrapper();
		shiroCacheManager.setCacheManager(cacheManager);
		return shiroCacheManager;
    }  
	
	//<!-- 保证实现了Shiro内部lifecycle函数的bean执行 -->
	@Bean(name = "lifecycleBeanPostProcessor") 
	public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() { 
		return new LifecycleBeanPostProcessor();
    }
	
	//<!-- AOP式方法级权限检查,注解  -->
	/*@Bean
	@DependsOn("lifecycleBeanPostProcessor")
	public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator daap = new DefaultAdvisorAutoProxyCreator();
        daap.setProxyTargetClass(true); 
        return daap;
    } 
	
	@Bean 
	public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor(MyRealm myShiroRealm) {
        AuthorizationAttributeSourceAdvisor aasa = new AuthorizationAttributeSourceAdvisor();
        aasa.setSecurityManager(securityManager()); 
        return aasa;
    } */
	
	/*@Bean 
	public RedisManager redisManager(){
		RedisManager redisManager = new RedisManager();
		redisManager.setHost(host);
		redisManager.setPort(port);
		redisManager.setExpire(expire);
		redisManager.setTimeout(timeout);
		redisManager.setPassword("");
		return redisManager;
	}
	
	@Bean
	public RedisSessionDAO redisSessionDAO(){
		RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
		redisSessionDAO.setRedisManager(redisManager());
		return redisSessionDAO;
	}
	
	@Bean
	public RedisCacheManager redisCacheManager(){
		RedisCacheManager redisCacheManager = new RedisCacheManager();
		redisCacheManager.setRedisManager(redisManager());
		return redisCacheManager;
	}*/
	
	/**
	 * 在thymeleaf里使用shiro的标签
	 * @return
	 */
	@Bean(name = "shiroDialect")
	public ShiroDialect shiroDialect(){
		return new ShiroDialect();
	}
}
