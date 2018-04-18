package com.jianeye.cuckoo3.webside.index.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.jianeye.cuckoo3.webside.base.basecontroller.BaseController;
import com.jianeye.cuckoo3.webside.exception.SystemException;
import com.jianeye.cuckoo3.webside.logininfo.model.LoginInfoEntity;
import com.jianeye.cuckoo3.webside.logininfo.service.LoginInfoService;
import com.jianeye.cuckoo3.webside.region.model.Region;
import com.jianeye.cuckoo3.webside.region.service.RegionService;
import com.jianeye.cuckoo3.webside.resource.model.ResourceEntity;
import com.jianeye.cuckoo3.webside.resource.service.ResourceService;
import com.jianeye.cuckoo3.webside.role.model.RoleEntity;
import com.jianeye.cuckoo3.webside.user.model.UserEntity;
import com.jianeye.cuckoo3.webside.user.service.UserService;
import com.jianeye.cuckoo3.webside.util.EndecryptUtils;
import com.jianeye.cuckoo3.webside.util.HttpServletRequestUtil;
import com.jianeye.cuckoo3.webside.util.TreeUtil;

/**
 * 
 * IndexController.java
 * @author 张孟志
 * @date 2016年7月1日
 * @caption 首页控制器，包括home/index/login/logout
 */
@Controller
@Scope("prototype")
@RequestMapping(value = "/")
public class IndexController extends BaseController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private ResourceService resourceService;
	
	@Autowired
	private LoginInfoService loginInfoService;	
	
	@Autowired
	private Producer captchaProducer;

	// add by zhangmz 2016-06-21 为纳税人注册定义区域
	@Autowired
	private RegionService regionService;

	@RequestMapping(method = RequestMethod.GET)
	public String home(Model model) {
		return "home";
	}
	
	@RequestMapping(value = "index", method = RequestMethod.GET)
	public String syptIndex(Model model) {
		return "home";
	}
	
	@RequestMapping(value = "welcome.html")
	public String welcome(Model model) {
		return "welcome";
	}
	
	@RequestMapping(value = "login.html", method = RequestMethod.GET, produces = "text/html; charset=utf-8")
	public String login(HttpServletRequest request) {
		try{
			request.removeAttribute("error");
			// add by zhangmz 2016-06-21 为注册功能提供区域信息
			// 这一句有二级缓存，不影响效率
			List<Region> regions = regionService.queryListAll(new HashMap<>());
			List<Map<String, String>> regionsList  = new ArrayList<Map<String, String>>();
			for (Region region : regions) {
				Map<String, String> regionsMap = new HashMap<String, String>();
				regionsMap.put("regId", region.getId().toString());
				regionsMap.put("regName", region.getRegionName());
				regionsList.add(regionsMap);
			}
			request.setAttribute("regionsList", regionsList);
			return "login";
		}catch(Exception e)
		{
			throw new SystemException(e);
		}
	}

	/**
	 * 用户登录
	 * 认证过程：
	 * 1、想要得到Subject对象,访问地址必须在shiro的拦截地址内,不然会报空指针
	 * 2、用户输入的账号和密码,存到UsernamePasswordToken对象中,然后由shiro内部认证对比
	 * 3、认证执行者交由ShiroDbRealm中doGetAuthenticationInfo处理
	 * 4、当以上认证成功后会向下执行,认证失败会抛出异常
	 * 
	 * @param accountName	账户名称
	 * @param password	密码
	 * @return
	 */
	@RequestMapping(value = "login.html", method = RequestMethod.POST, produces = "text/html; charset=utf-8")
	public String userLogin(String accountName, String password, String captcha, Boolean rememberMe, HttpServletRequest request) {
		UsernamePasswordToken token = null;
		try {
			 //从session中取出servlet生成的验证码text值
	        String expected = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
	        //获取用户页面输入的验证码
	        if(!captcha.equalsIgnoreCase(expected))
	        {
	        	request.setAttribute("error", "验证码错误！");
				return "/login";
	        }else
	        {
				// 想要得到Subject对象,访问地址必须在shiro的拦截地址内,不然会报空指针
				Subject subject = SecurityUtils.getSubject();
				token = new UsernamePasswordToken(accountName, password);
				//token.setRememberMe(rememberMe);
				subject.login(token);
				if (subject.isAuthenticated()) {
					LoginInfoEntity loginInfo = new LoginInfoEntity();
					Session session = SecurityUtils.getSubject().getSession();
					loginInfo.setUserId(Integer.valueOf(session.getAttribute("userSessionId").toString()));
					loginInfo.setAccountName(accountName);
					
					// modify by zhangmz 2016-07-11 
					// 获取客户端（用户/浏览器）的IP，不是服务器本身IP
					// loginInfo.setLoginIp(session.getHost());
					//
					// request.getRemoteHost() 会引发底层代码有去作dns查询的动作
					// java.net.InetAddress.getHostFromNameService(InetAddress.java:532)，从而导致系统奇慢。
					// loginInfo.setLoginIp(request.getRemoteHost());
					//
					// 在通过了Apache,Squid等反向代理软件就不能获取到客户端的真实IP地址
					// loginInfo.setLoginIp(request.getRemoteAddr());	
					//
					loginInfo.setLoginIp(HttpServletRequestUtil.getIpAddr(request));
					
					loginInfoService.log(loginInfo);
					request.removeAttribute("error");
				} else {
					token.clear();
					request.setAttribute("error", "用户名或密码不正确！");
					return "login";
				}
	        }
		}catch (UnknownAccountException uae)
		{
			token.clear();
			request.setAttribute("error", "账户不存在！");
			return "login";
		}
		catch (IncorrectCredentialsException ice)
		{
			token.clear();
			request.setAttribute("error", "密码错误！");
			return "login";
		}catch (LockedAccountException e) {
			token.clear();
			request.setAttribute("error", "您的账户已被锁定,请与管理员联系或10分钟后重试！");
			return "login";
		} catch (ExcessiveAttemptsException e) {
			token.clear();
			request.setAttribute("error", "您连续输错5次,帐号将被锁定10分钟!");
			return "login";
		}catch(ExpiredCredentialsException eca)
		{
			token.clear();
			request.setAttribute("error", "账户凭证过期！");
			return "login";
		}catch (AuthenticationException e) {
			token.clear();
			request.setAttribute("error", "账户验证失败！");
			return "login";
		}catch (Exception e)
		{
			token.clear();
			request.setAttribute("error", "登录异常，请联系管理员！");
			return "login";
		}
		return "redirect:index.html";
	}

	/**
	 * 
	 * @return
	 */
	@RequestMapping(value = "index.html", method = RequestMethod.GET)
	public String index(Model model) {
		try
		{
			// 获取登录的bean
			HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
			UserEntity userEntity = (UserEntity)request.getSession().getAttribute("userSession");
			List<ResourceEntity> list = resourceService.findResourcesByUserId(userEntity.getId().intValue());
			List<ResourceEntity> treeList = new TreeUtil().getChildResourceEntitys(list, null);
			model.addAttribute("list", treeList);
			// 登陆的信息回传页面
			model.addAttribute("userEntity", userEntity);
			return "index";
		}catch(Exception e)
		{
			throw new SystemException(e);
		}
	}
	
	
	/**
	 * 用户注册
	 * 
	 * @param userEntity
	 * @return
	 */
	@RequestMapping(value = "register.html", method = RequestMethod.POST, produces = "text/html; charset=utf-8")
	public String register(UserEntity userEntity) {
		try {
			String password = userEntity.getPassword();
			// 加密用户输入的密码，得到密码和加密盐，保存到数据库
			UserEntity user = EndecryptUtils.md5Password(userEntity.getAccountName(), userEntity.getPassword(), 2);
			//设置添加用户的密码和加密盐
			userEntity.setPassword(user.getPassword());
			userEntity.setCredentialsSalt(user.getCredentialsSalt());
			//设置创建者ID(默认为管理员) 
			userEntity.setCreatorId(1L);
			//设置创建者姓名
			userEntity.setCreatorName(userEntity.getUserName());
			userEntity.setCreateTime(new Date());
			//设置锁定状态：未锁定；删除状态：未删除
			userEntity.setLocked(0);
			userEntity.setDeleteStatus(0);
			
			// modify by zhangmz 根据配置赋予角色
			// 通过注册页面注册的用户统一设置为普通用户
			// RoleEntity roleEntity = roleService.findByName("普通用户");
			// 默认为“深圳”用户，无论tb_region表中深圳是否删除/锁定
			Long regionId = userEntity.getRegionId()==null?1L:userEntity.getRegionId();
			userEntity.setRegionId(regionId);
			
			Region region = regionService.findById(regionId);
			RoleEntity roleEntity = region.getRole();
						
			userEntity.setRole(roleEntity);
			// 保存用户注册信息
			userService.insert(userEntity, password);
			return "login";
		}catch(Exception e)
		{
			throw new SystemException(e);
		}
		
	}
	

	/**
	 * 用户退出
	 * 
	 * @return
	 */
	@RequestMapping(value = "logout.html", method = RequestMethod.GET)
	public String logout() {
		// 使用权限管理工具进行用户的退出，注销登录
		SecurityUtils.getSubject().logout(); // session
		return "redirect:login.html";
	}
	
	
	@RequestMapping(value = "captcha.html", method = RequestMethod.GET)
    public void kaptcha(HttpServletRequest req, HttpServletResponse rsp) {
		ServletOutputStream out = null;
		try {
	        HttpSession session = req.getSession();
	        rsp.setDateHeader("Expires", 0);
	        rsp.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
	        rsp.addHeader("Cache-Control", "post-check=0, pre-check=0");
	        rsp.setHeader("Pragma", "no-cache");
	        rsp.setContentType("image/jpeg");
	
	        String capText = captchaProducer.createText();
	        session.setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);
	
	        BufferedImage image = captchaProducer.createImage(capText);
	        out = rsp.getOutputStream();
	        ImageIO.write(image, "jpg", out);
	        out.flush();
        }catch(IOException e)
		{
			throw new SystemException(e);
		} finally {
            try {
				if(out != null){
					out.close();
				}
			} catch (IOException e) {
				throw new SystemException(e);
			}
        }
    }
	
	/**
	 * 一些公共入口
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "denied.html", method = RequestMethod.GET)
	public String disclaimer(Model model) {
		return "denied";
	}
	
	@RequestMapping(value = "error/error.html", method = RequestMethod.GET)
	public String error(Model model) {
		return "error/error";
	}	
	
	@RequestMapping(value = "error/error-404.html", method = RequestMethod.GET)
	public String error404(Model model) {
		return "error/error-404";
	}	
	@RequestMapping(value = "error/error-500.html", method = RequestMethod.GET)
	public String error500(Model model) {
		return "error/error-500";
	}	
}
