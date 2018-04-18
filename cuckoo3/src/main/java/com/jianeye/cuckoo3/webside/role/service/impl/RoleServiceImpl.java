package com.jianeye.cuckoo3.webside.role.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jianeye.cuckoo3.webside.base.baseservice.impl.AbstractService;
import com.jianeye.cuckoo3.webside.exception.ServiceException;
import com.jianeye.cuckoo3.webside.role.mapper.RoleMapper;
import com.jianeye.cuckoo3.webside.role.model.RoleEntity;
import com.jianeye.cuckoo3.webside.role.service.RoleService;

/**
 * 
 * RoleServiceImpl.java
 * @author 张孟志
 * @date 2016年7月1日
 * @caption 角色
 */
@Service("roleService")
public class RoleServiceImpl extends AbstractService<RoleEntity, Long>
		implements RoleService {

	@Autowired
	private RoleMapper roleMapper;

	// 这句必须要加上。不然会报空指针异常，因为在实际调用的时候不是BaseMapper调用，而是具体的mapper，这里为userMapper
	@Autowired
	public void setBaseMapper() {
		super.setBaseMapper(roleMapper);
	}

	@Override
	public boolean addRolePerm(int id, List<Integer> ids){
		boolean flag = false;
		try
		{
			int permCount = roleMapper.findRoleResourceById(id);
			boolean delFlag = true;
			if(permCount > 0)
			{
				int delResult = roleMapper.deleteRoleResource(id);
				if(permCount != delResult)
				{
					delFlag = false;
				}
			}
			
			if (delFlag) {
				if(ids.size() > 0)
				{
					Map<String, Object> parameter = new HashMap<String, Object>();
					parameter.put("roleId", id);
					parameter.put("resourceIds", ids);
					int addResult = roleMapper.addRoleResource(parameter);
					if (addResult == ids.size()) {
						flag = true;
					}
				}else
				{
					flag = true;
				}
			}
			return flag;
		}catch(Exception e)
		{
			throw new ServiceException(e);
		}
	}

	@Override
	public boolean addRolePermOne(int id, Long resourcesId){
		boolean flag = false;
		try
		{	
			List<Long> ids = new ArrayList<>();
			ids.add(resourcesId);
			Map<String, Object> parameter = new HashMap<String, Object>();
			parameter.put("roleId", id);
			parameter.put("resourceIds", ids);
			int addResult = roleMapper.addRoleResource(parameter);
			if (addResult == ids.size()) {
				flag = true;
			}
			return flag;
		}catch(Exception e)
		{
			throw new ServiceException(e);
		}
	}
	
	@Override
	public RoleEntity findByNameAndOwner(String name, int createUid) {
		return roleMapper.findByNameAndOwner(name, createUid);
	}
}
