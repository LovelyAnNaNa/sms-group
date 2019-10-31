package com.whtt.smsgroup.security;

import com.whtt.smsgroup.entity.pojo.SmsMenu;
import com.whtt.smsgroup.entity.pojo.SmsRoleMenu;
import com.whtt.smsgroup.service.SmsMenuService;
import com.whtt.smsgroup.service.SmsRoleMenuService;
import com.whtt.smsgroup.service.SmsRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Auther: wbh
 * @Date: 2019/10/30 16:43
 * @Description: 赋予用户权限
 */
@Component
public class CustomPermissionEvaluator implements PermissionEvaluator {

    @Autowired
    private SmsRoleService roleService;
    @Autowired
    private SmsMenuService menuService;
    @Autowired
    private SmsRoleMenuService roleMenuService;

    @Override
    public boolean hasPermission(Authentication authentication, Object targetUrl, Object targetPermission) {
        //读取loadUserByUsername()方法的结果
        User user = (User) authentication.getPrincipal();
        //获取loadUserByUsername()注入的角色
        Collection<GrantedAuthority> authorities = user.getAuthorities();

        //便利用户所有角色
        for (GrantedAuthority auth : authorities) {
            String roleName = auth.getAuthority();
            Integer roleId = roleService.getByName(roleName).getId();

            //得到角色所有的权限
            List<SmsRoleMenu> roleMenuList = roleMenuService.listByRoleId(roleId);
            for (SmsRoleMenu roleMenu : roleMenuList) {
                SmsMenu menu = menuService.getById(roleMenu.getMenuId());
                if(menu.getHref().equals(targetUrl) ){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable serializable, String s, Object o) {
        return false;
    }

}
