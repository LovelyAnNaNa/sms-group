package com.whtt.smsgroup.service.impl;

import com.whtt.smsgroup.entity.pojo.SmsPlatformTemplate;
import com.whtt.smsgroup.mapper.SmsPlatformTemplateMapper;
import com.whtt.smsgroup.service.SmsPlatformTemplateService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <p>
 * 第三方短信平台模板 服务实现类
 * </p>
 *
 * @author wbh
 * @since 2019-11-01
 */
@Service
public class SmsPlatformTemplateServiceImpl extends ServiceImpl<SmsPlatformTemplateMapper, SmsPlatformTemplate> implements SmsPlatformTemplateService {

    @Resource
    private SmsPlatformTemplateMapper templateMapper;

    @Override
    public SmsPlatformTemplate replaceParam(Map<String, Object> params, Integer templateId) {
        //根据id获取模板信息
        SmsPlatformTemplate templateInfo = templateMapper.selectById(templateId);

        String content = templateInfo.getContent();
        //1为腾讯平台,按照腾讯平台的参数组装方式拼接
        if(templateInfo.getPlatformId() == 1){
            for (String key : params.keySet()) {
                content = content.replace("{" + key + "}",params.get(key) + "");
            }
        }
        //重新设置
        templateInfo.setContent(content);
        return templateInfo;
    }
}
