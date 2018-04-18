package com.jianeye.cuckoo3.webside.financialProduct.service;

import java.util.List;
import java.util.Map;

import com.jianeye.cuckoo3.webside.financialProduct.model.FinancialProduct;

/**
 * 理财产品服务
 * @author xudan
 *
 */
public interface FinancialProductService {
	/**
	 * 自定义方法
	 * 获取理财产品列表
	 * @param userId
	 * @return
	 */
	public List<FinancialProduct> queryListByPage(Map<String, Object> parameter);
	
	public int insert(FinancialProduct financialProduct);
	
	public FinancialProduct findById(Long id);

	public int update(FinancialProduct financialProduct);
    
    public int deleteBatchById(List<Long> ids);
    
    
}
