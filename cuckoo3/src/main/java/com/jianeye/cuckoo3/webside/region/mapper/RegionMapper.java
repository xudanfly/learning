package com.jianeye.cuckoo3.webside.region.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jianeye.cuckoo3.webside.base.basemapper.BaseMapper;
import com.jianeye.cuckoo3.webside.region.model.Region;

/**
 * 
 * RegionMapper.java
 * @author 张孟志
 * @date 2016年7月1日
 * @caption  纳税人区域
 */
@Repository 
public interface RegionMapper extends BaseMapper <Region , Long > {
	/**
	 * 添加区域和角色对应关系
	 * @param region
	 * @return
	 */
	public int insertRegionRole(Region region);
	
	/**
	 * 更新区域和角色对应关系
	 * @param region
	 * @return
	 */
	public int updateRegionRole(Region region);
	
	/**
	 * 删除区域和角色对应关系
	 * @param regionIds
	 * @return
	 */
	public int deleteBatchRegionRole(List<Long> regionIds);
}
