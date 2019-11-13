package com.whtt.smsgroup.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @Auther: wbh
 * @Date: 2019/11/5 13:48
 * @Description:
 */
@Slf4j
public class VerifyFilter extends OncePerRequestFilter {

    private static final PathMatcher pathMatcher = new AntPathMatcher();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("验证码过滤器!!!");
        HttpSession session = request.getSession();
        Object aaa = session.getAttribute("aaa");
        if (aaa == null) {
            log.info("null");
            session.setAttribute("aaa", "123456");
        } else {
            log.info("not null{}", aaa);
        }
        if (isProtectedUrl(request)) {
            log.info("登录请求!!!!");
        }
        //转发请求
        filterChain.doFilter(request, response);
    }

    // 拦截 登录请求
    private boolean isProtectedUrl(HttpServletRequest request) {
        return "POST".equals(request.getMethod()) && pathMatcher.match("/smsUser/login", request.getServletPath());
    }
}
