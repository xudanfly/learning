package com.jianeye.cuckoo3.webside.resource.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jianeye.cuckoo3.webside.base.baseservice.impl.AbstractService;
import com.jianeye.cuckoo3.webside.resource.mapper.ResourceMapper;
import com.jianeye.cuckoo3.webside.resource.model.ResourceEntity;
import com.jianeye.cuckoo3.webside.resource.service.ResourceService;

/**
 * 
 * ResourceServiceImpl.java
 * @author 张孟志
 * @date 2016年7月1日
 * @caption 资源
 */
@Service("resourceService")
public class ResourceServiceImpl extends AbstractService<ResourceEntity, Long> implements ResourceService{

	@Autowired
	private ResourceMapper resourceMapper;
	
	//这句必须要加上。不然会报空指针异常，因为在实际调用的时候不是BaseMapper调用，而是具体的mapper，这里为userMapper
	@Autowired
	public void setBaseMapper() {
		super.setBaseMapper(resourceMapper);
	}
	
	@Override
	public List<ResourceEntity> findResourcesByUserId(int userId) {
		return resourceMapper.findResourcesByUserId(userId);
	}

	@Override
	public List<ResourceEntity> queryResourceList(Map<String, Object> parameter) {
		return resourceMapper.queryResourceList(parameter);
	}

}
