package com.jianeye.cuckoo3.webside.role.service;

import java.util.List;
import java.util.Map;

import com.jianeye.cuckoo3.webside.role.model.RoleEntity;

/**
 * 
 * RoleService.java
 * @author 张孟志
 * @date 2016年7月1日
 * @caption 角色
 */
public interface RoleService {

	public List<RoleEntity> queryListByPage(Map<String, Object> parameter);

	public RoleEntity findByName(String name);
	
	public RoleEntity findByNameAndOwner(String name, int createUid);
	
	public int insert(RoleEntity roleEntity);
	
	public RoleEntity findById(Long id);

	public int update(RoleEntity roleEntity);
    
    public int deleteBatchById(List<Long> roleIds);
    
    public boolean addRolePerm(int id, List<Integer> ids) throws Exception ;
    
    // add by zhangmz 单独增加一条赋权记录
    public boolean addRolePermOne(int id, Long resourcesId) throws Exception ;

}