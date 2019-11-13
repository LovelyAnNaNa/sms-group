package com.whtt.smsgroup.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.whtt.smsgroup.entity.pojo.SmsPlatform;
import com.whtt.smsgroup.mapper.SmsPlatformMapper;
import com.whtt.smsgroup.service.SmsPlatformService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wbh
 * @since 2019-11-02
 */
@Service
public class SmsPlatformServiceImpl extends ServiceImpl<SmsPlatformMapper, SmsPlatform> implements SmsPlatformService {

    @Resource
    private SmsPlatformMapper platformMapper;

    @Override
    public SmsPlatform getByPlatformId(Integer platformId) {
        QueryWrapper<SmsPlatform> platformQueryWrapper = new QueryWrapper<>();
        platformQueryWrapper.eq("platform_id",platformId);
        return platformMapper.selectOne(platformQueryWrapper);
    }

    @Override
    public SmsPlatform getDefaultPlatform() {
        QueryWrapper<SmsPlatform> platformQueryWrapper = new QueryWrapper<>();
        platformQueryWrapper.eq("default_use",1);
        return platformMapper.selectOne(platformQueryWrapper);
    }
}
