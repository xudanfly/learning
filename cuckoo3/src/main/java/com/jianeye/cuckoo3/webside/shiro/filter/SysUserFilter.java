package com.jianeye.cuckoo3.webside.shiro.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.springframework.beans.factory.annotation.Autowired;

import com.jianeye.cuckoo3.webside.user.mapper.UserMapper;

/**
 * 
 * SysUserFilter.java
 * @author 张孟志
 * @date 2016年7月1日
 * @caption
 */
public class SysUserFilter extends PathMatchingFilter {

	//@Inject
	@Autowired(required=false)
	private UserMapper userMapper;

    @Override
    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {

        String userName = (String)SecurityUtils.getSubject().getPrincipal();
        request.setAttribute("user", userMapper.findByName(userName));
        return true;
    }
}