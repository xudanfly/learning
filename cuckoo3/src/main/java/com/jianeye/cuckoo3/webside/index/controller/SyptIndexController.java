package com.jianeye.cuckoo3.webside.index.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jianeye.cuckoo3.webside.base.basecontroller.BaseController;

/**
 * 
 * SyptIndexController.java
 * @author 张孟志
 * @date 2016年7月1日
 * @caption 为税银平台信息浏览准备的统一请求入口
 */
@Controller
@Scope("prototype")
@RequestMapping(value = "/sypt/")
public class SyptIndexController extends BaseController {


	@RequestMapping(value = "disclaimer.html", method = RequestMethod.GET)
	public String disclaimer(Model model) {
		return "sypt/disclaimer";
	}	

	@RequestMapping(value = "userAgreement.html", method = RequestMethod.GET)
	public String userAgreement() {
		return "sypt/userAgreement";
	}

//	/**
//	 * 下面两个方法测试HttpClientUtil用
//	 * @param request
//	 * @param response
//	 * @throws IOException 
//	 */
//	@RequestMapping(value = "simpleGet", method = RequestMethod.GET)
//	public void simpleGet(HttpServletRequest request,HttpServletResponse response) throws IOException {
//		response.getWriter().write("{\"firstName\":\"Brett\",\"lastName\":\"McLaughlin\"}");
//	}
//	
//	@RequestMapping(value = "simplePost", method = RequestMethod.POST)
//	public void simplePost(HttpServletRequest request,HttpServletResponse response) throws IOException {
//		String rtn = request.getParameter("1") + (String)request.getParameter("2");
//		response.getWriter().write("{\"rtn\":\"" + rtn + "\"}");
//	}
}
