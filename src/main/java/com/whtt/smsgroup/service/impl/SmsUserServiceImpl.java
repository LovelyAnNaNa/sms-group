package com.whtt.smsgroup.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.whtt.smsgroup.entity.pojo.SmsUser;
import com.whtt.smsgroup.mapper.SmsUserMapper;
import com.whtt.smsgroup.service.SmsUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class SmsUserServiceImpl extends ServiceImpl<SmsUserMapper, SmsUser> implements SmsUserService {

    @Resource
    private SmsUserMapper userMapper;

    @Override
    public SmsUser getByName(String username) {
        QueryWrapper<SmsUser> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("user_name",username);
        return userMapper.selectOne(userQueryWrapper);
    }
}
