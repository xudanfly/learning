package com.jianeye.cuckoo3.webside.resource.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.jianeye.cuckoo3.webside.base.basemapper.BaseMapper;
import com.jianeye.cuckoo3.webside.resource.model.ResourceEntity;

/**
 * 
 * ResourceMapper.java
 * @author 张孟志
 * @date 2016年7月1日
 * @caption 资源
 */
@Repository
public interface ResourceMapper extends BaseMapper<ResourceEntity, Long>{
	
	/**
	 * 自定义方法
	 * 获取用户ID对应的资源信息
	 * @param userId
	 * @return
	 */
	public List<ResourceEntity> findResourcesByUserId(@Param(value="userId") int userId);
	
	/**
	 * 查询权限树集合
	 * @param parameter 参数中必须包含roleId,其他参数可参考mapping文件
	 * @return
	 */
    public List<ResourceEntity> queryResourceList(Map<String, Object> parameter);
}
