package com.whtt.smsgroup.util;

import com.whtt.smsgroup.entity.pojo.SmsUser;
import com.whtt.smsgroup.service.SmsUserService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @Auther: wbh
 * @Date: 2019/10/30 08:43
 * @Description: security工具类
 */
@Component
public class SecurityUtil implements ApplicationRunner {

    private static SmsUserService userService;

    //负责给密码加密的类
    private static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * 获取当前登录的用户信息
     */
    public static SmsUser getLoginUser(){
        //获取SpringSecurity中的用户信息
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //根据用户名获取数据库中的user信息
        SmsUser loginUser = userService.getByName(userDetails.getUsername());
        return loginUser;
    }
    
    /**
     * 密码加密
     * @param password
     * @return
     */
    public static String encryptPassword(String password){
        return passwordEncoder.encode(password);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        userService = SpringContextUtils.getBean(SmsUserService.class);
    }
}
