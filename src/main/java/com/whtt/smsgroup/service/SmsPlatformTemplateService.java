package com.whtt.smsgroup.service;

import com.whtt.smsgroup.entity.pojo.SmsPlatformTemplate;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 第三方短信平台模板 服务类
 * </p>
 *
 * @author wbh
 * @since 2019-11-01
 */
public interface SmsPlatformTemplateService extends IService<SmsPlatformTemplate> {

    //拼接短信模板参数,获取正文
    SmsPlatformTemplate replaceParam(Map<String,Object> params,Integer templateId);
}
