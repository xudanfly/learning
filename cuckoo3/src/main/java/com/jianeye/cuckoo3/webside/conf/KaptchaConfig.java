/**
 * 
 */
package com.jianeye.cuckoo3.webside.conf;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;

/**
 * Kaptcha验证码生成器 
 * @author xudan
 *
 */
@Configuration
public class KaptchaConfig {
	
	@Bean 
	public Config config(){
		Properties props = new Properties();
		props.put("kaptcha.session.key", "kaptcha.code");
		props.put("kaptcha.border", "no");//无边框
		props.put("kaptcha.textproducer.font.color", "black");
		props.put("kaptcha.obscurificator.impl", "com.google.code.kaptcha.impl.WaterRipple");//<!-- 渲染效果：水纹：WaterRipple；鱼眼：FishEyeGimpy；阴影：ShadowGimpy -->
		props.put("kaptcha.noise.impl", "com.google.code.kaptcha.impl.NoNoise");//不要噪点
		props.put("kaptcha.image.width", "90");
		props.put("kaptcha.image.height", "33");
		props.put("kaptcha.textproducer.font.size", "25");
		props.put("kaptcha.textproducer.char.length", "4");
		props.put("kaptcha.textproducer.char.space", "5");
		props.put("kaptcha.background.clear.from", "247,247,247");//!-- 和登录框背景颜色一致 -->
		props.put("kaptcha.background.clear.to", "247,247,247");
		Config config = new Config(props);//注：属性必须是字符串，否则不起效果
		return config;
	}
	
	@Bean(name="captchaProducer")
	@Scope("singleton")
	public DefaultKaptcha DefaultKaptcha(){
		DefaultKaptcha captchaProducer = new DefaultKaptcha();
		captchaProducer.setConfig(config());
		return captchaProducer;
		
	}

}
