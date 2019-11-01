package com.whtt.smsgroup.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whtt.smsgroup.entity.pojo.SmsConfig;
import com.whtt.smsgroup.mapper.SmsConfigMapper;
import com.whtt.smsgroup.service.SmsConfigService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wbh
 * @since 2019-10-31
 */
@Service
public class SmsConfigServiceImpl extends ServiceImpl<SmsConfigMapper, SmsConfig> implements SmsConfigService {

    @Resource
    private SmsConfigMapper configMapper;

    @Override
    public Integer getNormalRoleId() {
        QueryWrapper<SmsConfig> configQueryWrapper = new QueryWrapper<>();
        configQueryWrapper.eq("config_key","normal_role_id");
        SmsConfig smsConfig = configMapper.selectOne(configQueryWrapper);
        Integer normalRoleId = Integer.valueOf(smsConfig.getConfigValue());
        return normalRoleId;
    }
}
