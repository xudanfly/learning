package com.jianeye.cuckoo3.webside.thread;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 
 * WorkerItem.java
 * @author 张孟志
 * @date 2016年7月1日
 * @caption
 */
public class WorkerItem implements Runnable, Serializable {

	Logger logger = LoggerFactory.getLogger(WorkerItem.class.getSimpleName());
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 执行任务
	 */
	@Override
	public void run() {
		try {
			Thread.sleep(ThreadPool.SLEEP_TIME);
			System.out.print("任务执行了");
		} catch (InterruptedException e) {
			logger.error(e.toString());
		}
		
	}

}
