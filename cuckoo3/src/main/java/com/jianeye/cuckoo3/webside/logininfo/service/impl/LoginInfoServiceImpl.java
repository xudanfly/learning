package com.jianeye.cuckoo3.webside.logininfo.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jianeye.cuckoo3.webside.base.baseservice.impl.AbstractService;
import com.jianeye.cuckoo3.webside.logininfo.mapper.LoginInfoMapper;
import com.jianeye.cuckoo3.webside.logininfo.model.LoginInfoEntity;
import com.jianeye.cuckoo3.webside.logininfo.service.LoginInfoService;

/**
 * 
 * LoginInfoServiceImpl.java
 * @author 张孟志
 * @date 2016年7月1日
 * @caption 登陆信息
 */
@Service("loginInfoService")
public class LoginInfoServiceImpl extends AbstractService<LoginInfoEntity, Long> implements LoginInfoService{

	@Autowired
	private LoginInfoMapper loginInfoMapper;
	
	//这句必须要加上。不然会报空指针异常，因为在实际调用的时候不是BaseMapper调用，而是具体的mapper，这里为userMapper
	@Autowired
	public void setBaseMapper() {
		super.setBaseMapper(loginInfoMapper);
	}

	@Override
	public int log(LoginInfoEntity loginInfo) {
		return loginInfoMapper.insert(loginInfo);
	}

	@Override
	public void clearLogininfo() {
		loginInfoMapper.clear7daysBefore();
	}
}
