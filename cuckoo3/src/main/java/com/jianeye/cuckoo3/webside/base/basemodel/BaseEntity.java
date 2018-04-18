package com.jianeye.cuckoo3.webside.base.basemodel;

import java.io.Serializable;

/**
 * 
 * BaseEntity.java
 * @author 张孟志
 * @date 2016年7月1日
 * @caption 统一定义id的BaseEntity基类,基类统一定义id的属性名称、数据类型.
 *          子类可重载getId()函数.
 */
public abstract class BaseEntity implements Serializable{

	private static final long serialVersionUID = 1L;

	/*
	 * id
	 */
	protected Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
