package com.whtt.smsgroup.controller.admin;


import com.whtt.smsgroup.common.CommonResult;
import com.whtt.smsgroup.service.SmsPlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wbh
 * @since 2019-11-02
 */
@RestController
@RequestMapping("/smsPlatform")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class SmsPlatformController {

    @Autowired
    private SmsPlatformService platformService;

    //查看各个平台剩余短信的信息
    @ResponseBody
    @PostMapping(value = "/info")
    public Object info(){
        return CommonResult.success(platformService.list());
    }
}






