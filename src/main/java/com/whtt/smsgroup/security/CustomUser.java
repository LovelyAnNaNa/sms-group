package com.whtt.smsgroup.security;

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
    //当前用户在数据库中的id
    private Integer id;

    public CustomUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

}
