package com.jianeye.cuckoo3.webside.batch.clearlog;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;

import com.jianeye.cuckoo3.webside.loginfo.service.LogInfoService;
import com.jianeye.cuckoo3.webside.logininfo.service.LoginInfoService;
import com.jianeye.cuckoo3.webside.thread.Threads;

/**
 * 
 * ClearLog.java
 * @author 张孟志
 * @date 2016年7月1日
 * @caption 清理日志批处理，清理tb_log_info/tb_login_info两张表
 */
public class ClearLog implements Runnable {

	private String cronExpression;

	private int shutdownTimeout = Integer.MAX_VALUE;

	private ThreadPoolTaskScheduler threadPoolTaskScheduler;

	@Autowired
	private LogInfoService logInfoService;
	@Autowired
	private LoginInfoService loginInfoService;

	@PostConstruct
	public void start() {
		Validate.notBlank(cronExpression);

		threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
		// 这个参数要与配置文件中的Bean id 一致
		threadPoolTaskScheduler.setThreadNamePrefix("clearLog");
		threadPoolTaskScheduler.initialize();

		threadPoolTaskScheduler.schedule(this, new CronTrigger(cronExpression));
	}

	@PreDestroy
	public void stop() {
		ScheduledExecutorService scheduledExecutorService = threadPoolTaskScheduler.getScheduledExecutor();
		Threads.gracefulShutdown(scheduledExecutorService, shutdownTimeout, TimeUnit.SECONDS);
	}

	/**
	 * 清理loginfo表七天前的记录.
	 * <!-- 每天凌晨2点执行 <property name="cronExpression" value="0 0 2 * * ?"/> -->
	 * <!-- 测试每10秒执行一次 <property name="cronExpression" value="0/10 * * * * ?"/> -->
	 */
	@Override
	public void run() {
		System.out.println("===============================ClearLoginfo begin");
		logInfoService.clearLoginfo();
		System.out.println("===============================ClearLoginfo end");
		System.out.println("===============================ClearLogininfo begin");
		loginInfoService.clearLogininfo();
		System.out.println("===============================ClearLogininfo end");
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	/**
	 * 设置normalShutdown的等待时间,单位秒.
	 */
	public void setShutdownTimeout(int shutdownTimeout) {
		this.shutdownTimeout = shutdownTimeout;
	}


}

