package com.whtt.smsgroup.security;

import com.alibaba.fastjson.JSON;
import com.whtt.smsgroup.common.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Auther: wbh
 * @Date: 2019/10/30 09:52
 * @Description: 负责处理用户登录成功和登录失败的操作
 */
@Component
@Slf4j
public class CustomAuthenticationLoginHandler implements AuthenticationSuccessHandler, AuthenticationFailureHandler {

    //处理用户登录失败的操作
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        //返回异常信息
        Object result = null;
        String message = exception.getMessage();
        if("Bad credentials".equals(message)){
            result = CommonResult.failed("密码错误");
        }else{
            //用户名不存在会直接在CustomUser中声明异常信息
            result = CommonResult.failed(exception.getMessage());
        }
        writer.write(JSON.toJSONString(result));
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("用户{}登录系统",authentication);
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        CommonResult<Object> result = CommonResult.success("登录成功");
        writer.write(JSON.toJSONString(result));
        //自动跳转页面0
//        response.sendRedirect("/smsUser/home");
    }
}
