/**
 * 
 */
package com.jianeye.cuckoo3.webside.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.jianeye.cuckoo3.webside.batch.clearlog.ClearLog;

/**
 * 定时任务配置
 * spring-batch配置
 * @author xudan
 *
 */
@Configuration
@EnableScheduling // 启用定时任务
public class SchedulingConfig {
	//<!-- 每天凌晨2点执行 <property name="cronExpression" value="0 0 2 * * ?"/> -->
	//<!-- 测试每10秒执行一次 <property name="cronExpression" value="0/10 * * * * ?"/> -->
	//@Scheduled(cron = "0/10 * * * * ?")
	@Bean(name="clearLog")
	@Lazy(false)
	public ClearLog clearLog(){
		ClearLog clearLog = new ClearLog();
		clearLog.setCronExpression("0 0 2 * * ?");
		clearLog.setShutdownTimeout(20);
		return clearLog;
	}
}
