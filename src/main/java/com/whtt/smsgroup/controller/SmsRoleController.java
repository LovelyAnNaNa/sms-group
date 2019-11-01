package com.whtt.smsgroup.controller;


import com.whtt.smsgroup.common.CommonResult;
import com.whtt.smsgroup.entity.pojo.SmsRole;
import com.whtt.smsgroup.service.SmsRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wbh
 * @since 2019-10-31
 */
@Controller
@RequestMapping("/smsRole")
public class SmsRoleController {

    @Autowired
    private SmsRoleService roleService;

    @ResponseBody
    @RequestMapping(value = "/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Object add(@RequestBody SmsRole newRole){
        newRole.validateParams();
        if(newRole.getRoleName().indexOf("ROLE_") != 0){
            return CommonResult.failed("角色名称格式不正确!");
        }
        boolean result = roleService.save(newRole);
        if (result) {
            return CommonResult.success();
        }
        return CommonResult.failed("添加角色信息失败");
    }

    @ResponseBody
    @RequestMapping(value = "/deleteById")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Object deleteById(@NotNull(message = "角色id不能为空") @RequestParam Integer roleId){
        Integer count = roleService.deleteById(roleId);
        if(count > 0){
            return CommonResult.success("角色删除成功");
        }
        return CommonResult.failed("角色删除失败!");
    }

    @ResponseBody
    @PostMapping(value = "/updateRole")
    public Object updateRole(@RequestBody SmsRole updateRole){
        boolean result = roleService.updateById(updateRole);
        if(result){
            return CommonResult.success("角色信息修改成功");
        }
        return CommonResult.failed("角色信息修改失败");
    }
}

