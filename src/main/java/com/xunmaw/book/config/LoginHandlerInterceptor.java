package com.xunmaw.book.config;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginHandlerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {

        Object loginUser = req.getSession().getAttribute("loginUser");

        if (loginUser == null) {
            req.setAttribute("msg", "没有权限，请先登录");
            req.getRequestDispatcher("/index.html").forward(req, resp);
            return false;
        } else {
            return true;
        }

    }

}
