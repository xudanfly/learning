package com.jianeye.cuckoo3.webside.financialProduct.mapper;

import org.springframework.stereotype.Repository;

import com.jianeye.cuckoo3.webside.base.basemapper.BaseMapper;
import com.jianeye.cuckoo3.webside.financialProduct.model.FinancialProduct;

@Repository 
public interface FinancialProductMapper extends BaseMapper<FinancialProduct, Long>{
    
    int insertSelective(FinancialProduct record);

}