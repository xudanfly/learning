package com.jianeye.cuckoo3.webside.region.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jianeye.cuckoo3.webside.base.baseservice.impl.AbstractService;
import com.jianeye.cuckoo3.webside.exception.ServiceException;
import com.jianeye.cuckoo3.webside.region.mapper.RegionMapper;
import com.jianeye.cuckoo3.webside.region.model.Region;
import com.jianeye.cuckoo3.webside.region.service.RegionService;

/**
 * 
 * RegionServiceImpl.java
 * @author 张孟志
 * @date 2016年7月1日
 * @caption  纳税人区域
 */
@Service("regionService ")
public class RegionServiceImpl extends AbstractService <Region, Long> implements RegionService{
	
	@Autowired
	private RegionMapper regionMapper ; 
	
	//这句必须要加上。不然会报空指针异常，因为在实际调用的时候不是BaseMapper调用，而是具体的mapper，这里为userMapper
	@Autowired
	public void setBaseMapper() {
		super.setBaseMapper(regionMapper);
	}
	/**
	 * 重写用户插入，逻辑：
	 * 1、插入用户
	 * 2、插入用户和角色的对应关系
	 * 3、插入用户的个人资料信息
	 */
	public int insert(Region region){
		try
		{
			if(regionMapper.insert(region) == 1)
			{
				return regionMapper.insertRegionRole(region);
			}else
			{
				return 0;
			}
		}catch(Exception e)
		{
			throw new ServiceException(e);
		}
	}
	

	/**
	 * 重写用户更新逻辑：
	 * 1、更新用户
	 * 2、更新用户和角色的对应关系
	 * 3、更新用户个人资料信息
	 */
	public int update(Region region){
		try
		{
			if(regionMapper.update(region) == 1)
			{
				return regionMapper.updateRegionRole(region);
			}else
			{
				return 0;
			}
		}catch(Exception e)
		{
			throw new ServiceException(e);
		}
	}
	
	/**
	 * 重写用户删除逻辑：
	 * 1、删除用户和角色的对应关系
	 * 2、删除用户
	 */
	public int deleteBatchById(List<Long> regionIds){
		try
		{
			int result = regionMapper.deleteBatchRegionRole(regionIds);
			if(result == regionIds.size())
			{
				return regionMapper.deleteBatchById(regionIds);
			}else
			{
				return 0;
			}
		}catch(Exception e)
		{
			throw new ServiceException(e);
		}
	}

	@Override
	public int updateOnly(Region region) throws ServiceException{
		try
		{ 
			int cnt = regionMapper.update(region);
			return cnt;
		}catch(Exception e)
		{
			throw new ServiceException(e);
		}
	}
}

