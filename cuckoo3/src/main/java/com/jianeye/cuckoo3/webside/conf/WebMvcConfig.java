package com.jianeye.cuckoo3.webside.conf;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.jianeye.cuckoo3.webside.util.DateFormatter;

/**
 * MVC配置
 * @author xd
 *
 */

@Configuration
@EnableWebMvc
public class WebMvcConfig extends WebMvcConfigurerAdapter{
	/*附件上传到文件系统的路径*/
	@Value("${local_path}")
	private String local_path;
	
	/**
	 * 增加资源访问路径
	 */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
        								 
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        //上传文件
        registry.addResourceHandler("/image/**").addResourceLocations("file:"+local_path+"/image/");
        registry.addResourceHandler("/file/**").addResourceLocations("file:"+local_path+"/file/");
        registry.addResourceHandler("/video/**").addResourceLocations("file:"+local_path+"/video/");
    }
    
    /*@Bean 
    public IntrospectorCleanupListener introspectorCleanupListener(){
    	return new IntrospectorCleanupListener();
    }*/
    
    /**
     * springmvc日期转换器
     * @return
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(new DateFormatter());
        super.addFormatters(registry);
    }
    
    /**解析String请求数据*/
    @Bean
    public StringHttpMessageConverter stringConverter(){
    	StringHttpMessageConverter stringConverter = new StringHttpMessageConverter();
    	List<MediaType> list = new ArrayList<MediaType>();
    	list.add(MediaType.TEXT_PLAIN);
    	stringConverter.setSupportedMediaTypes(list);//text/plain;charset=UTF-8
    	return stringConverter;
    }
   
    /**解析json请求数据，将json转换为java对象*/
    @Bean
    public FastJsonHttpMessageConverter fastjsonConverter(){
    	FastJsonHttpMessageConverter fastjsonConverter = new FastJsonHttpMessageConverter();
    	List<MediaType> list = new ArrayList<MediaType>();
    	list.add(MediaType.TEXT_HTML);
    	list.add(MediaType.APPLICATION_JSON);
    	fastjsonConverter.setSupportedMediaTypes(list);
    	SerializerFeature[] features = {SerializerFeature.WriteMapNullValue, //<!-- 空值继续输出 -->
    			SerializerFeature.DisableCircularReferenceDetect , //<!-- 防止循环引用 -->
    			SerializerFeature.WriteDateUseDateFormat}; //<!-- 格式化日期,默认格式为：yyyy-MM-dd HH:mm:ss -->
    	fastjsonConverter.setFeatures(features);
    	return fastjsonConverter;
    }
    
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    	converters.add(stringConverter());
    	converters.add(fastjsonConverter());
    }
    
    /**防止循环引用*/
    /*@Bean 
    public FieldRetrievingFactoryBean DisableCircularReferenceDetect(){
    	FieldRetrievingFactoryBean DisableCircularReferenceDetect =  new FieldRetrievingFactoryBean();
    	DisableCircularReferenceDetect.setStaticField("com.alibaba.fastjson.serializer.SerializerFeature.DisableCircularReferenceDetect");
    	return DisableCircularReferenceDetect;
    }*/
    
    /*@Bean
    public FastJsonJsonView fastJsonJsonView(){
    	FastJsonJsonView fastJsonJsonView = new FastJsonJsonView();
    	fastJsonJsonView.setExtractValueFromSingleKeyModel(true);
    	SerializerFeature[] features = {SerializerFeature.WriteMapNullValue, //<!-- 空值继续输出 -->
    			SerializerFeature.DisableCircularReferenceDetect , //<!-- 防止循环引用 -->
    			SerializerFeature.WriteDateUseDateFormat}; //<!-- 格式化日期,默认格式为：yyyy-MM-dd HH:mm:ss -->
    	fastJsonJsonView.setFeatures(features);
    	return fastJsonJsonView;
    }*/
    
    //视图解析器，根据视图的名称new ModelAndView(name)，在配置文件查找对应的bean配置 
    /*@Bean
	public BeanNameViewResolver beanNameViewResolver(){
		BeanNameViewResolver beanNameViewResolver = new BeanNameViewResolver();
    	return beanNameViewResolver;
    }*/
    
    /*@Bean
    public InternalResourceViewResolver jstlView(){
    	InternalResourceViewResolver jstlView = new InternalResourceViewResolver();
    	jstlView.setViewClass(JstlView.class);
    	jstlView.setContentType("text/html");
    	jstlView.setPrefix("/");
    	jstlView.setSuffix(".jsp");
    	return jstlView;
    }*/
    
    /**ContentNegotiatingViewResolver视图解析器，返回多视图*/
    /*@Bean 
    public ContentNegotiatingViewResolver ContentNegotiatingViewResolver(){
    	ContentNegotiatingViewResolver contentNegotiatingViewResolver = new ContentNegotiatingViewResolver();
    	contentNegotiatingViewResolver.setOrder(1);
    	List<View> defaultViews = new ArrayList<View>();
    	defaultViews.add(fastJsonJsonView());
    	contentNegotiatingViewResolver.setDefaultViews(defaultViews);
    	List<ViewResolver> viewResolvers = new ArrayList<ViewResolver>();
    	viewResolvers.add(beanNameViewResolver());
    	//viewResolvers.add(jstlView());
    	contentNegotiatingViewResolver.setViewResolvers(viewResolvers);
    	return contentNegotiatingViewResolver;
    }*/
    
    /**
	   （1）继承：WebMvcConfigurerAdapter
	   （2）覆盖方法：configureContentNegotiation
	  favorPathExtension表示支持后缀匹配，
	     属性ignoreAcceptHeader默认为fasle，表示accept-header匹配，defaultContentType开启默认匹配。
	     例如：请求aaa.xx，若设置<entry key="xx" value="application/xml"/> 也能匹配以xml返回。
	     根据以上条件进行一一匹配最终，得到相关并符合的策略初始化ContentNegotiationManager  
	 */
	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
       configurer.favorPathExtension(false);
	}
    
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
    	configurer.setUseSuffixPatternMatch(false);
    }
    
    /**
     * 文件上传
     * @return
     */
    /*@Bean
    public CommonsMultipartResolver multipartResolver(){
    	CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
    	multipartResolver.setDefaultEncoding("utf-8");
    	multipartResolver.setMaxUploadSize(Long.parseLong("10485760000"));
    	multipartResolver.setMaxInMemorySize(40960);
    	return multipartResolver;
    }*/
}
