package com.jianeye.cuckoo3.webside.loginfo.service;

import java.util.List;
import java.util.Map;

import com.jianeye.cuckoo3.webside.loginfo.model.LogInfoEntity;

/**
 * 
 * LogInfoService.java
 * @author 张孟志
 * @date 2016年7月1日
 * @caption 审计用的用户操作信息
 * 
 * 修改人：张孟志
 * 修改日期：2016年7月1日
 * 修改说明：增加clearLoginfo方法，用于清理日志
 */
public interface LogInfoService {

	public int log(LogInfoEntity logInfo);
	
	public List<LogInfoEntity> queryListByPage(Map<String, Object> parameter);
	
	// 删除七天前的记录
	public void clearLoginfo();
}
