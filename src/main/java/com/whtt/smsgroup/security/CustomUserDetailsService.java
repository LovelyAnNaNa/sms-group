package com.whtt.smsgroup.security;

import com.whtt.smsgroup.entity.pojo.SmsRole;
import com.whtt.smsgroup.entity.pojo.SmsUser;
import com.whtt.smsgroup.entity.pojo.SmsUserRole;
import com.whtt.smsgroup.exception.UserLoginException;
import com.whtt.smsgroup.service.SmsRoleService;
import com.whtt.smsgroup.service.SmsUserRoleService;
import com.whtt.smsgroup.service.SmsUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @Auther: wbh
 * @Date: 2019/10/29 19:41
 * @Description:  负责用户登录
 */
@Service("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private SmsUserService userService;
    @Autowired
    private SmsRoleService roleService;
    @Autowired
    private SmsUserRoleService userRoleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UserLoginException {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        //从数据库中取出用户信息
        SmsUser userInfo = userService.getByName(username);

        //判断用户是否存在
        if(userInfo == null){
            throw new UserLoginException("用户名不存在");
        }

        if(userInfo.getStatus() == 1){
            throw new UserLoginException("账号已被禁用");
        }

        //获取用户角色的映射
        List<SmsUserRole> userRoleList = userRoleService.listByUserId(userInfo.getId());
        if (userRoleList != null && userRoleList.size() > 0) {
            //获取所有的角色信息
            ArrayList<Integer> roleIds = new ArrayList<>(userRoleList.size());
            userRoleList.forEach(userRole -> roleIds.add(userRole.getRoleId()));
            Collection<SmsRole> roleList = roleService.listByIds(roleIds);
            //添加角色名称
            roleList.forEach(role -> {
                authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
            });
        }

        CustomUser loginUser = new CustomUser(userInfo.getUserName(), userInfo.getUserPassword(), authorities);
        loginUser.setUserInfo(userInfo);
        return loginUser;
    }
}
