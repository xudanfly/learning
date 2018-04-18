package com.jianeye.cuckoo3.webside.base.baseservice;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 
 * BaseService.java
 * @author 张孟志
 * @date 2016年7月1日
 * @caption 定义一般使用到的服务
 * @param <T> 实体类型
 * @param <ID> 主键类型
 */
public interface BaseService<T,ID extends Serializable> {

    public int insert(T t);
    
    public int insertBatch(List<T> t);
    
    public int deleteBatchById(List<ID> ids);
 
    public int deleteById(ID id);
 
    public int deleteByUUID(String uuid);
 
    public int update(T t);
    
    public T find(Map<String, Object> parameter);
 
    public T findById(ID id);
 
    public T findByUUID(String uuid);
 
    public T findByName(String name);
 
    public List<T> queryListAll(Map<String, Object> parameter);
    
    public List<T> queryListByPage(Map<String, Object> parameter);
    
    int count(Map<String, Object> parameter);

}
