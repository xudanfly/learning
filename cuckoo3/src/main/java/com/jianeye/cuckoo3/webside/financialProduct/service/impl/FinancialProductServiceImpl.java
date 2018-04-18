package com.jianeye.cuckoo3.webside.financialProduct.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jianeye.cuckoo3.webside.base.baseservice.impl.AbstractService;
import com.jianeye.cuckoo3.webside.financialProduct.mapper.FinancialProductMapper;
import com.jianeye.cuckoo3.webside.financialProduct.model.FinancialProduct;
import com.jianeye.cuckoo3.webside.financialProduct.service.FinancialProductService;

/**
* 理财产品服务
* @author xudan
*
*/
@Service("financialProductService")
public class FinancialProductServiceImpl extends AbstractService<FinancialProduct, Long> implements FinancialProductService{

	@Autowired
	private FinancialProductMapper financialProductMapper;
	
	//这句必须要加上。不然会报空指针异常，因为在实际调用的时候不是BaseMapper调用，而是具体的mapper，这里为financialProductMapper
	@Autowired
	public void setBaseMapper() {
		super.setBaseMapper(financialProductMapper);
	}
	
}
