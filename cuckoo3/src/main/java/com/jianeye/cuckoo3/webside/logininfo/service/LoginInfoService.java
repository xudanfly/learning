package com.jianeye.cuckoo3.webside.logininfo.service;

import java.util.List;
import java.util.Map;

import com.jianeye.cuckoo3.webside.logininfo.model.LoginInfoEntity;

/**
 * 
 * LoginInfoService.java
 * @author 张孟志
 * @date 2016年7月1日
 * @caption 登陆信息
 */
public interface LoginInfoService {

	public int log(LoginInfoEntity loginInfo);
	
	public List<LoginInfoEntity> queryListByPage(Map<String, Object> parameter);
	
	// 删除七天前的记录
	public void clearLogininfo();
}
