package com.whtt.smsgroup.controller.platform;

import com.alibaba.fastjson.JSONObject;
import com.whtt.smsgroup.common.CommonResult;
import com.whtt.smsgroup.sms.TencentSMS;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

/**
 * @Auther: wbh
 * @Date: 2019/11/1 08:52
 * @Description: 腾讯短信平台
 */
@Controller
@RequestMapping("/admin/platform/")
public class TencentController {


    /**
     * 获取其他平台的短信套餐包信息
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/getPackageInfo")
    @PreAuthorize("hasPermission('','sms:platform:packgeInfo')")
    public Object getPackageInformation(){

        HashMap<String, Object> infoMap = new HashMap<>();
        //腾讯短信平台套餐包信息
        JSONObject tencentPackageInfo = TencentSMS.getPackageInformation();
        infoMap.put("tencent",tencentPackageInfo);

        return CommonResult.success(infoMap);
    }
}
