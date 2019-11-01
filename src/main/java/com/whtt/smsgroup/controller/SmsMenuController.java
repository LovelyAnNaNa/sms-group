package com.whtt.smsgroup.controller;


import com.whtt.smsgroup.common.CommonResult;
import com.whtt.smsgroup.entity.pojo.SmsMenu;
import com.whtt.smsgroup.entity.pojo.SmsUser;
import com.whtt.smsgroup.service.SmsMenuService;
import com.whtt.smsgroup.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wbh
 * @since 2019-10-31
 */
@Controller
@RequestMapping("/smsMenu")
public class SmsMenuController {

    @Autowired
    private SmsMenuService menuService;

    /**
     * 获取当前登录的用户列表信息
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/userMenu")
    public Object userMenu(){
        //获取当前登录用户信息
        SmsUser loginUser = SecurityUtil.getLoginUser();
        //根据当前登录用户获取用户的菜单
        Collection<SmsMenu> userMenu = menuService.getUserMenu(loginUser.getId());
        return CommonResult.success(userMenu);
    }

    @ResponseBody
    @RequestMapping(value = "/tree")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Object tree(){
        Collection<SmsMenu> allMenu = menuService.getUserMenu(null);
        return CommonResult.success(allMenu);
    }

}

