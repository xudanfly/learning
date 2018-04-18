package com.jianeye.cuckoo3.webside.region.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jianeye.cuckoo3.webside.base.basecontroller.BaseController;
import com.jianeye.cuckoo3.webside.dtgrid.model.Pager;
import com.jianeye.cuckoo3.webside.exception.AjaxException;
import com.jianeye.cuckoo3.webside.exception.ServiceException;
import com.jianeye.cuckoo3.webside.region.model.Region;
import com.jianeye.cuckoo3.webside.region.service.RegionService;
import com.jianeye.cuckoo3.webside.role.model.RoleEntity;
import com.jianeye.cuckoo3.webside.role.service.RoleService;
import com.jianeye.cuckoo3.webside.user.model.UserEntity;
import com.jianeye.cuckoo3.webside.util.PageUtil;

/**
 * 
 * RegionController.java
 * @author 张孟志
 * @date 2016年7月1日
 * @caption 纳税人区域
 */
@Controller
@Scope("prototype")
@RequestMapping("/region/")
public class RegionController extends BaseController {

	@Autowired
	private RegionService regionService;
	
	@Autowired
	private RoleService roleService;
	
	@RequestMapping("listUI.html")
	public String listUI(Model model, HttpServletRequest request) {
		try
		{
			PageUtil page = new PageUtil();
			if(request.getParameterMap().containsKey("page")){
				page.setPageNum(Integer.valueOf(request.getParameter("page")));
				page.setPageSize(Integer.valueOf(request.getParameter("rows")));
				page.setOrderByColumn(request.getParameter("sidx"));
				page.setOrderByType(request.getParameter("sord"));
			}
			model.addAttribute("page", page);
			return "region/list";
		}catch(Exception e)
		{
			throw new AjaxException(e);
		}
	}
	
	
	/**
	 * ajax分页动态加载模式
	 * @param dtGridPager Pager对象
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
		if (parameters.size() < 1) {
			parameters.put("regionName", null);
		}
		// 设置分页，page里面包含了分页信息
		Page<Object> page = PageHelper.startPage(pager.getNowPage(),pager.getPageSize(), "id DESC");
		List<Region> list = regionService.queryListByPage(parameters);
		parameters.clear();
		parameters.put("isSuccess", Boolean.TRUE);
		parameters.put("nowPage", pager.getNowPage());
		parameters.put("pageSize", pager.getPageSize());
		parameters.put("pageCount", page.getPages());
		parameters.put("recordCount", page.getTotal());
		parameters.put("startRecord", page.getStartRow());
		//列表展示数据
		parameters.put("exhibitDatas", list);
		return parameters;
	}
	
	
	
	@RequestMapping("addUI.html")
	public String addUI(Model model) {
		// 当验证都通过后，把用户信息放在session里
		Session session = SecurityUtils.getSubject().getSession();
		Long createUid = (Long) session.getAttribute("userSessionId");
		
		try
		{
			Map<String,Object> parameter = new HashMap<String,Object>();
			parameter.put("createUid", createUid);
			
			List<RoleEntity> list = roleService.queryListByPage(parameter);
			model.addAttribute("roleList", list);
			return "region/form";
		}catch(Exception e)
		{
			throw new AjaxException(e);
		}
		
	}
	
	@RequestMapping("add.html")
	@ResponseBody
	public Object add(Region region) throws AjaxException
	{
		Map<String, Object> map = new HashMap<String, Object>();
		try
		{			
			//获取登录用户
			HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
			UserEntity sessionUser = (UserEntity)request.getSession().getAttribute("userSession");			
			//设置创建者姓名(姓名可能重复，使用ID区别) 
			region.setCreatorId(sessionUser.getId().intValue());
			region.setCreateTime(new Date());
			// 设置锁定状态：未锁定；删除状态：未删除
			// region.setLocked(0);
			// region.setDeleteStatus(0);
			int result = regionService.insert(region);
			if(result == 1)
			{
				map.put("success", Boolean.TRUE);
				map.put("data", null);
				map.put("message", "添加成功");
			}else
			{
				map.put("success", Boolean.FALSE);
				map.put("data", null);
				map.put("message", "添加失败");
			}
		}catch(ServiceException e)
		{
			throw new AjaxException(e);
		}
		return map;
	}
	
	
	@RequestMapping("editUI.html")
	public String editUI(Model model, HttpServletRequest request, Long id) {
		try
		{
			Region region = regionService.findById(id);
			PageUtil page = new PageUtil();
			page.setPageNum(Integer.valueOf(request.getParameter("page")));
			page.setPageSize(Integer.valueOf(request.getParameter("rows")));
			page.setOrderByColumn(request.getParameter("sidx"));
			page.setOrderByType(request.getParameter("sord"));
			
			List<RoleEntity> list = roleService.queryListByPage(new HashMap<String, Object>());
			
			model.addAttribute("page", page);
			model.addAttribute("regionEntity", region);
			model.addAttribute("roleList", list);
			return "region/form";
		}catch(Exception e)
		{
			throw new AjaxException(e);
		}
	}
	
	@RequestMapping("edit.html")
	@ResponseBody
	public Object update(Region region) throws AjaxException
	{
		Map<String, Object> map = new HashMap<String, Object>();
		try
		{			
			int result = regionService.update(region);
			if(result == 1)
			{
				map.put("success", Boolean.TRUE);
				map.put("data", null);
				map.put("message", "编辑成功");
			}else
			{
				map.put("success", Boolean.FALSE);
				map.put("data", null);
				map.put("message", "编辑失败");
			}
		}catch(Exception e)
		{
			throw new AjaxException(e);
		}
		return map;
	}
	
	
	@RequestMapping("deleteBatch.html")
	@ResponseBody
	public Object deleteBatch(String ids){
		Map<String, Object> result = new HashMap<String, Object>();
		try
		{
			String[] regionIds = ids.split(",");
			List<Long> list = new ArrayList<Long>();
			for (String string : regionIds) {
				list.add(Long.valueOf(string));
			}
			int cnt = regionService.deleteBatchById(list);
			if(cnt == list.size())
			{
				result.put("success", true);
				result.put("data", null);
				result.put("message", "删除成功");
			}else
			{
				result.put("success", false);
				result.put("data", null);
				result.put("message", "删除失败");
			}
		}catch(Exception e)
		{
			throw new AjaxException(e);
		}
		return result;
	}	
		
	@RequestMapping("validateRegionName.html")
	@ResponseBody
	public Object validateRegionName(String regionName){
		try
		{
			Region region = regionService.findByName(regionName);
			if(region == null)
			{
				return true;
			}else
			{
				return false;
			}
		}catch(Exception e)
		{
			throw new AjaxException(e);
		}
	}
	
	
}
