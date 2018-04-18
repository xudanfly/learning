package com.jianeye.cuckoo3.webside.logininfo.model;

import java.util.Date;

import com.jianeye.cuckoo3.webside.base.basemodel.BaseEntity;

/**
 * 
 * LoginInfoEntity.java
 * @author 张孟志
 * @date 2016年7月1日
 * @caption 登陆信息
 */
public class LoginInfoEntity extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer userId;
	
	private String accountName;
	
	private String loginIp;
	
	private Date loginTime;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}
	
}
