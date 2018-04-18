package com.jianeye.cuckoo3.webside.base.basecontroller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * BaseController.java
 * @author 张孟志
 * @date 2016年7月1日
 * @caption 基础Controller，只有日志功能
 */
public abstract class BaseController {
	
	public Logger logger = LoggerFactory.getLogger(this.getClass());
	
}