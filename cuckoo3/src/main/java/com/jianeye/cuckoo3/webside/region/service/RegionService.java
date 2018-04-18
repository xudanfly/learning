package com.jianeye.cuckoo3.webside.region.service;

import java.util.List;
import java.util.Map;

import com.jianeye.cuckoo3.webside.exception.ServiceException;
import com.jianeye.cuckoo3.webside.region.model.Region;

/**
 * 
 * RegionService.java
 * @author 张孟志
 * @date 2016年7月1日
 * @caption 纳税人区域
 */
public interface RegionService {

    public List<Region> queryListAll(Map<String, Object> parameter);
    
	public List<Region> queryListByPage(Map<String, Object> parameter);

	public Region findByName(String regionName);
	
	public int insert(Region region);
	
	public Region findById(Long id);

	public int update(Region region);
	
	public int updateOnly(Region region) throws ServiceException;
    
    public int deleteBatchById(List<Long> regionIds);

}
