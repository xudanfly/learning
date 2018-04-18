package com.jianeye.cuckoo3.webside.base.basemapper;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * BaseMapper.java
 * @author 张孟志
 * @date 2016年7月1日
 * @caption 基础mapper定义,可以自己进行重新定义
 * @param <T> 实体类型
 * @param <ID> 主键类型，一般是Long
 */
public interface BaseMapper<T,ID extends Serializable> {

    public int insert(T t);
    
    public int insertBatch(List<T> t);
    
    public int deleteBatchById(List<ID> ids);
 
    public int deleteById(@Param("id")ID id);
 
    public int deleteByUUID(String uuid);
 
    public int update(T t);
 
    public T find(Map<String, Object> parameter);
 
    public T findById(@Param("id")ID id);
 
    public T findByUUID(@Param("uuid")String uuid);
 
    public T findByName(@Param("name")String name);
 
    public List<T> queryListAll(Map<String, Object> parameter);
    
    public List<T> queryListByPage(Map<String, Object> parameter);
    
    int count(Map<String, Object> parameter);
	
}
