package com.jianeye.cuckoo3.webside.role.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.jianeye.cuckoo3.webside.base.basemapper.BaseMapper;
import com.jianeye.cuckoo3.webside.role.model.RoleEntity;

/**
 * 
 * RoleMapper.java
 * @author 张孟志
 * @date 2016年7月1日
 * @caption 角色
 */
@Repository
public interface RoleMapper extends BaseMapper<RoleEntity, Long>{
	
	/**
	 * 查询该角色是否有权限信息
	 * @param roleId	角色id 
	 * 使用@Param注解主要是设置mapping中可以使用参数名进行取值
	 * @return
	 */
	public int findRoleResourceById(@Param(value="roleId") int roleId);
	
	/**
	 * 删除角色的权限信息
	 * @param roleId	角色id 
	 * 使用@Param注解主要是设置mapping中可以使用参数名进行取值
	 * @return
	 */
	public int deleteRoleResource(@Param(value="roleId") int roleId);
	
	/**
	 * 添加角色和权限映射信息
	 * @param roleId	角色id
	 * @param list<Long>
	 * @return
	 */
	public int addRoleResource(Map<String, Object> parameter);
	
	public RoleEntity findByNameAndOwner(@Param("name")String name, @Param("createUid")int createUid);
}
