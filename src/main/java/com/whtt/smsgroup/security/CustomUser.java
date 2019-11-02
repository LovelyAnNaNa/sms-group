package com.whtt.smsgroup.security;

import com.whtt.smsgroup.entity.pojo.SmsUser;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * @Auther: wbh
 * @Date: 2019/10/31 15:05
 * @Description:
 */
@Getter
@Setter
@ToString
public class CustomUser extends User {

    /**
     * 当前登录的用户信息
     */
    private SmsUser userInfo;

    public CustomUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

}
