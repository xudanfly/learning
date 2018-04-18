package com.jianeye.cuckoo3.webside.user.service;

import java.util.List;
import java.util.Map;

import com.jianeye.cuckoo3.webside.exception.ServiceException;
import com.jianeye.cuckoo3.webside.user.model.UserEntity;

/**
 * 
 * UserService.java
 * @author 张孟志
 * @date 2016年7月1日
 * @caption 用户
 */
public interface UserService {

	public List<UserEntity> queryListByPage(Map<String, Object> parameter);

	public UserEntity findByName(String accountName);
	
	public int insert(UserEntity userEntity, String password);
	
	public UserEntity findById(Long id);

	public int update(UserEntity userEntity);
	
	public int updateOnly(UserEntity userEntity, String password) throws ServiceException;
    
    public int deleteBatchById(List<Long> userIds);
    
}