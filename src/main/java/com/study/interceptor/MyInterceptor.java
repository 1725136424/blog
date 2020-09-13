package com.study.interceptor;

import com.study.util.ServletUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        ServletUtils servletUtils = new ServletUtils();
        servletUtils.setHttpSession(request.getSession());
        return true;
    }

}
