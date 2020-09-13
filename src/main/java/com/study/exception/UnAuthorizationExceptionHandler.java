package com.study.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.pojo.entity.AjaxResult;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 未授权异常处理器
@ControllerAdvice
public class UnAuthorizationExceptionHandler {

    @ExceptionHandler(AuthorizationException.class)
    public void AuthorizationException(
            HandlerMethod method,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        // 判断当前是否为ajax请求
        ResponseBody methodAnnotation = method.getMethodAnnotation(ResponseBody.class);
        if (methodAnnotation != null) {
            // Ajax请求   返回异常信息
            AjaxResult ajaxResult = new AjaxResult();
            ajaxResult.setIsSuccess(false);
            ajaxResult.setMessage("您的权限不够");
            response.setContentType("text/html; charset=utf-8");
            response.getWriter().write(new ObjectMapper().writeValueAsString(ajaxResult));
        } else {
            // 跳转页面
            String contextPath = request.getSession().getServletContext().getContextPath();
            request.getRequestDispatcher("/WEB-INF/views/noPermission.jsp").forward(request, response);
        }
    }
}
