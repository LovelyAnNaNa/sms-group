package com.whtt.smsgroup.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @Auther: wbh
 * @Date: 2019/10/30 08:43
 * @Description: security工具类
 */
public class SecurityUtil {

    //负责给密码加密的类
    private static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * 密码加密
     * @param password
     * @return
     */
    public static String encryptPassword(String password){
        return passwordEncoder.encode(password);
    }

}
