package com.whtt.smsgroup.exception;


import org.springframework.security.core.AuthenticationException;

/**
 * @Auther: wbh
 * @Date: 2019/10/30 10:42
 * @Description: 用户登录失败时抛出的异常信息
 */
public class UserLoginException extends AuthenticationException {

    public UserLoginException(String msg) {
        super(msg);
    }
}
