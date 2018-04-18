package com.jianeye.cuckoo3.webside.shiro;

import java.io.Serializable;

import org.apache.shiro.util.SimpleByteSource;

/**
 * 
 * MySimpleByteSource.java
 * @author 张孟志
 * @date 2016年7月1日
 * @caption
 */
public class MySimpleByteSource extends SimpleByteSource implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MySimpleByteSource(String string) {
		super(string);
	}
}
