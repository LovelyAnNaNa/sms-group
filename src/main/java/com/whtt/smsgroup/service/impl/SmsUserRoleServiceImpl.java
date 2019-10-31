package com.whtt.smsgroup.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.whtt.smsgroup.entity.pojo.SmsUserRole;
import com.whtt.smsgroup.mapper.SmsUserRoleMapper;
import com.whtt.smsgroup.service.SmsUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wbh
 * @since 2019-10-31
 */
@Service
public class SmsUserRoleServiceImpl extends ServiceImpl<SmsUserRoleMapper, SmsUserRole> implements SmsUserRoleService {

    @Resource
    private SmsUserRoleMapper userRoleMapper;

    @Override
    public List<SmsUserRole> listByUserId(Integer userId) {
        QueryWrapper<SmsUserRole> userRoleQueryWrapper = new QueryWrapper<>();
        userRoleQueryWrapper.eq("user_id",userId);
        return userRoleMapper.selectList(userRoleQueryWrapper);
    }
}
