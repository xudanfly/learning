package com.jianeye.cuckoo3.webside.loginfo.mapper;

import org.springframework.stereotype.Repository;

import com.jianeye.cuckoo3.webside.base.basemapper.BaseMapper;
import com.jianeye.cuckoo3.webside.loginfo.model.LogInfoEntity;

/**
 * 
 * LogInfoMapper.java
 * @author 张孟志
 * @date 2016年7月1日
 * @caption 审计用的用户操作信息
 */
@Repository
public interface LogInfoMapper extends BaseMapper<LogInfoEntity, Long> {

	// 删除七天前的记录
	public void clear7daysBefore() ;
}
