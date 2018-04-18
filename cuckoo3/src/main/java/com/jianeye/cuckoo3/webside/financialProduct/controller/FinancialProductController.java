/**
 * 
 */
package com.jianeye.cuckoo3.webside.financialProduct.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.baidu.ueditor.ActionEnter;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jianeye.cuckoo3.webside.attachment.model.Attachment;
import com.jianeye.cuckoo3.webside.attachment.service.AttachmentService;
import com.jianeye.cuckoo3.webside.base.basecontroller.BaseController;
import com.jianeye.cuckoo3.webside.dtgrid.model.Pager;
import com.jianeye.cuckoo3.webside.exception.AjaxException;
import com.jianeye.cuckoo3.webside.exception.SystemException;
import com.jianeye.cuckoo3.webside.financialProduct.model.FinancialProduct;
import com.jianeye.cuckoo3.webside.financialProduct.service.FinancialProductService;
import com.jianeye.cuckoo3.webside.user.model.UserEntity;
import com.jianeye.cuckoo3.webside.util.Common;
import com.jianeye.cuckoo3.webside.util.PageUtil;

/**
 * @author xudan
 * 理财产品模块
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/financialProduct/")
public class FinancialProductController extends BaseController {
	@Autowired
	private FinancialProductService financialProductService;
	@Autowired
	private AttachmentService attachmentService;
	@Autowired
	private ActionEnter actionEnter;
	
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
			return "financialProduct/list";
		}catch(Exception e)
		{
			throw new SystemException(e);
		}
	}
	
	@ResponseBody
	@RequestMapping("list.html")
	public Object list(String gridPager) throws Exception{
		Map<String, Object> parameters = null;
		// 映射Pager对象
		Pager pager = JSON.parseObject(gridPager, Pager.class);
		// 自定义参数
		parameters = pager.getParameters();
		if(StringUtils.isNotBlank(Common.getloginUserGroupName())){
			parameters.put("groupName", Common.getloginUserGroupName());
		}
		// 选取有效状态的记录
		// parameters.put("deleteStatus", 0);//状态-有效
		
		//设置分页，page里面包含了分页信息
		Page<Object> page = PageHelper.startPage(pager.getNowPage(),pager.getPageSize(), "f_id DESC");
	
		List<FinancialProduct> list = financialProductService.queryListByPage(parameters);
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
	public String addUI() {
		return "financialProduct/form";
	}
	
	@RequestMapping("add.html")
	@ResponseBody
	@Transactional
	public Object add(FinancialProduct financialProduct)//
	{
		Map<String, Object> map = new HashMap<String, Object>();
		try
		{
			HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
			HttpSession session = request.getSession();
			UserEntity sessionUser = (UserEntity)session.getAttribute("userSession");
			String attachmenturls = request.getParameter("attachmenturls");
			//保存数据
			financialProduct.setCreatorId(sessionUser.getId().intValue());
			financialProduct.setCreatorName(sessionUser.getUserName());
			financialProduct.setCreateTime(new Date());
			if(!StringUtils.isBlank(sessionUser.getGroupName())){financialProduct.setGroupName(sessionUser.getGroupName());}
			int result = financialProductService.insert(financialProduct);//保存理财产品信息
			boolean f = this.saveAttachments(session,financialProduct.getId(),attachmenturls);//保存附件
			if(result > 0 && f)
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
		}catch(Exception e)
		{
			throw new AjaxException(e);
		}
		return map;
	}
	
	
	@RequestMapping("editUI.html")
	public String editUI(Model model, HttpServletRequest request, Long id) {
		try
		{
			FinancialProduct financialProduct = financialProductService.findById(id);
			PageUtil page = new PageUtil();
			page.setPageNum(Integer.valueOf(request.getParameter("page")));
			page.setPageSize(Integer.valueOf(request.getParameter("rows")));
			page.setOrderByColumn(request.getParameter("sidx"));
			page.setOrderByType(request.getParameter("sord"));
			model.addAttribute("page", page);
			model.addAttribute("financialProduct", financialProduct);
			return "financialProduct/form";
		}catch(Exception e)
		{
			throw new SystemException(e);
		}
	}
	
	@RequestMapping("edit.html")
	@ResponseBody
	@Transactional
	public Object update(FinancialProduct financialProduct)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		try
		{
			HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
			HttpSession session = request.getSession();
			String attachmenturls = request.getParameter("attachmenturls");
			//更新数据
			int result = financialProductService.update(financialProduct);
			boolean f = this.saveAttachments(session,financialProduct.getId(),attachmenturls);
			if(result > 0 && f)
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
	
	private boolean saveAttachments(HttpSession session,Long fkId,String attachmenturls){
		boolean f = true;
		try{
			UserEntity sessionUser = (UserEntity)session.getAttribute("userSession");
			List<Attachment> allattachments = (List<Attachment>) session.getAttribute("allattachments");
			//用户添加的附件
			List<String> attachmenturlArr = Arrays.asList(attachmenturls.split(","));//url数组
			//文章原始附件
			FinancialProduct fProduct = financialProductService.findById(fkId);
			List<Attachment> attachmentsOrig = fProduct.getAttachments();//文章原始附件
			//用户最后保存的附件
			List<Attachment> savedAttachments = new ArrayList<Attachment>();
			if(allattachments != null && allattachments.size()>0){
				for(Attachment attachment :allattachments){
					if(attachmenturlArr.contains(attachment.getUrl())){
						attachment.setFkId(fkId);//设置关联表ID
						attachment.setCreatorId(sessionUser.getId().intValue());
						attachment.setCreatorName(sessionUser.getUserName());
						attachment.setStatus("1");//设置有效状态
						savedAttachments.add(attachment);
					}
				}
				//添加附件信息
				if(savedAttachments != null && savedAttachments.size()>0){
					attachmentService.insertBatch(savedAttachments);
				}
				//删除无效附件[编辑过程中产生的无效附件]
				allattachments.removeAll(savedAttachments);
				if(allattachments != null && allattachments.size()>0){
					attachmentService.deleteBatchByUrl(allattachments);
				}
			}
			//删除无效附件[编辑过程中删除的原始附件]
			if(attachmentsOrig != null && attachmentsOrig.size()>0){
				for(Attachment attachment :attachmentsOrig){
					if(attachmenturlArr.contains(attachment.getUrl())){
						attachment.setFkId(fkId);//设置关联表ID
						attachment.setCreatorId(sessionUser.getId().intValue());
						attachment.setCreatorName(sessionUser.getUserName());
						attachment.setStatus("1");//设置有效状态
						savedAttachments.add(attachment);
					}
				}
				attachmentsOrig.removeAll(savedAttachments);
				if(attachmentsOrig !=null && attachmentsOrig.size()>0){
					Map<String,Object> mapParam = new HashMap<String,Object>();
					mapParam.put("fkId", fkId);
					mapParam.put("fkTable", FinancialProduct.getTablename());
					mapParam.put("fkIdName", FinancialProduct.getIdname());
					mapParam.put("attachments",attachmentsOrig);
					attachmentService.deleteBatchByUrl(mapParam);
				}
			}
			session.removeAttribute("allattachments");//清除缓存
		}catch(Exception e){
			e.printStackTrace();
			f = false;
		}
		return f;
	}
	
	
	@RequestMapping("deleteBatch.html")
	@ResponseBody
	@Transactional
	public Object deleteBatch(String ids) {
		Map<String, Object> result = new HashMap<String, Object>();
		try
		{
			String[] financialProductIds = ids.split(",");
			List<Long> list = new ArrayList<Long>();
			for (String string : financialProductIds) {
				list.add(Long.valueOf(string));
			}
			int cnt = financialProductService.deleteBatchById(list);
			boolean f = deleteBatchAttachments(list);//删除相关附件信息
			if(cnt == list.size() && f)
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
		}catch(Exception e){
			throw new AjaxException(e);
		}
		return result;
	}
	
	private boolean deleteBatchAttachments(List<Long> fkIds){
		boolean f = true;
		try{
			Map<String,Object> mapParam = new HashMap<String,Object>();
			mapParam.put("fkIds", fkIds);
			mapParam.put("fkTable", FinancialProduct.getTablename());
			mapParam.put("fkIdName", FinancialProduct.getIdname());
			attachmentService.deleteBatchByFkId(mapParam);
		}catch(Exception e){
			e.printStackTrace();
			f = false;
		}
		return f;
	}
	
	@RequestMapping(value = "detail.html", method = RequestMethod.GET)
	public String detail(Model model,Long id) {
		try
		{
			FinancialProduct financialProduct = financialProductService.findById(id);
			model.addAttribute("financialProduct", financialProduct);
			return "financialProduct/detail";
		}catch(Exception e)
		{
			throw new SystemException(e);
		}
	}
	
	@RequestMapping("ueditor.html")
    public void ueditor(HttpServletRequest request,HttpServletResponse response) throws Exception {
        request.setCharacterEncoding( "utf-8" );
        response.setHeader("Content-Type" , "text/html");
		String rootPath = request.getServletContext().getRealPath("/");
        actionEnter.initActionEnter(request, rootPath);
        String exec = actionEnter.exec();
        response.getWriter().write(exec);
    }
}
