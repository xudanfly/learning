package com.jianeye.cuckoo3.webside.loginfo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jianeye.cuckoo3.webside.base.basecontroller.BaseController;
import com.jianeye.cuckoo3.webside.dtgrid.model.Pager;
import com.jianeye.cuckoo3.webside.exception.SystemException;
import com.jianeye.cuckoo3.webside.loginfo.model.LogInfoEntity;
import com.jianeye.cuckoo3.webside.loginfo.service.LogInfoService;

/**
 * 
 * LogInfoController.java
 * @author 张孟志
 * @date 2016年7月1日
 * @caption 审计用的用户操作信息控制器
 */
@Controller
@Scope("prototype")
@RequestMapping(value = "/logInfo/")
public class LogInfoController extends BaseController {

	@Autowired
	private LogInfoService logInfoService;

	@RequestMapping("listUI.html")
	public String listUI() {
		try
		{
			return "loginfo/list";
		}catch(Exception e)
		{
			throw new SystemException(e);
		}
	}

	/**
	 * ajax分页动态加载模式
	 * 
	 * @param gridPager
	 *            Pager对象
	 * @throws Exception
	 */
	@RequestMapping(value = "/list.html", method = RequestMethod.POST)
	@ResponseBody
	public Object list(String gridPager) throws Exception{
		Map<String, Object> parameters = null;
		// 映射Pager对象
		Pager pager = JSON.parseObject(gridPager, Pager.class);
		// 判断是否包含自定义参数
		parameters = pager.getParameters();
		if (parameters.size() < 0) {
			parameters.put("accountName", null);
		}
		// 设置分页，page里面包含了分页信息
		Page<Object> page = PageHelper.startPage(pager.getNowPage(),pager.getPageSize(), "l_create_time DESC");
		List<LogInfoEntity> list = logInfoService.queryListByPage(parameters);
		parameters.clear();
		parameters.put("isSuccess", Boolean.TRUE);
		parameters.put("nowPage", pager.getNowPage());
		parameters.put("pageSize", pager.getPageSize());
		parameters.put("pageCount", page.getPages());
		parameters.put("recordCount", page.getTotal());
		parameters.put("startRecord", page.getStartRow());
		parameters.put("exhibitDatas", list);
		return parameters;
		
	}

}
