package com.study.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.pojo.entity.AjaxResult;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

public class LoginFormFilter extends FormAuthenticationFilter {

    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.setIsSuccess(true);
        ajaxResult.setMessage("登录成功");
        response.setContentType("text/html; charset=utf-8");
        response.getWriter().write(new ObjectMapper().writeValueAsString(ajaxResult));
        return false;
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.setIsSuccess(false);
        String exceptionName = e.getClass().getName();
        if (exceptionName.equals(UnknownAccountException.class.getName())) {
            ajaxResult.setMessage("用户名错误");
        } else if (exceptionName.equals(IncorrectCredentialsException.class.getName())) {
            ajaxResult.setMessage("密码错误");
        } else {
            ajaxResult.setMessage("未知错误");
        }
        response.setContentType("text/html; charset=utf-8");
        try {
            response.getWriter().write(new ObjectMapper().writeValueAsString(ajaxResult));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return false;
    }
}
