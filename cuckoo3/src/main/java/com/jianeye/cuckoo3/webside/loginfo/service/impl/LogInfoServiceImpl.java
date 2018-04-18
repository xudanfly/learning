package com.jianeye.cuckoo3.webside.loginfo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jianeye.cuckoo3.webside.base.baseservice.impl.AbstractService;
import com.jianeye.cuckoo3.webside.loginfo.mapper.LogInfoMapper;
import com.jianeye.cuckoo3.webside.loginfo.model.LogInfoEntity;
import com.jianeye.cuckoo3.webside.loginfo.service.LogInfoService;

/**
 * 
 * LogInfoServiceImpl.java
 * @author 张孟志
 * @date 2016年7月1日
 * @caption 审计用的用户操作信息
 * 
 * 修改人：张孟志
 * 修改日期：2016年7月1日
 * 修改说明：增加clearLoginfo方法，用于清理日志
 */
@Service("logInfoService")
public class LogInfoServiceImpl extends AbstractService<LogInfoEntity, Long> implements LogInfoService{

	@Autowired
	private LogInfoMapper logInfoMapper;
	
	//这句必须要加上。不然会报空指针异常，因为在实际调用的时候不是BaseMapper调用，而是具体的mapper，这里为userMapper
	@Autowired
	public void setBaseMapper() {
		super.setBaseMapper(logInfoMapper);
	}

	@Override
	public int log(LogInfoEntity logInfo) {
		return logInfoMapper.insert(logInfo);
	}

	@Override
	public void clearLoginfo() {
		logInfoMapper.clear7daysBefore();
	}
	
}
