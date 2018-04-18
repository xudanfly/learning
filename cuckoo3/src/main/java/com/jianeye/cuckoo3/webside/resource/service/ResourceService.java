package com.jianeye.cuckoo3.webside.resource.service;

import java.util.List;
import java.util.Map;

import com.jianeye.cuckoo3.webside.resource.model.ResourceEntity;

/**
 * 
 * ResourceService.java
 * @author 张孟志
 * @date 2016年7月1日
 * @caption 资源
 */
public interface ResourceService{

	/**
	 * 自定义方法
	 * 获取用户ID对应的资源信息
	 * @param userId
	 * @return
	 */
	public List<ResourceEntity> findResourcesByUserId(int userId);

	public List<ResourceEntity> queryListByPage(Map<String, Object> parameter);
	
	public ResourceEntity findByName(String name);
	
	public int insert(ResourceEntity resourceEntity);
	
	public ResourceEntity findById(Long id);

	public int update(ResourceEntity resourceEntity);
    
    public int deleteBatchById(List<Long> roleIds);
    
    public List<ResourceEntity> queryResourceList(Map<String, Object> parameter);
}
