package com.whtt.smsgroup.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whtt.smsgroup.entity.pojo.SmsRole;
import com.whtt.smsgroup.mapper.SmsRoleMapper;
import com.whtt.smsgroup.service.SmsRoleService;
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
public class SmsRoleServiceImpl extends ServiceImpl<SmsRoleMapper, SmsRole> implements SmsRoleService {

    @Resource
    private SmsRoleMapper roleMapper;

    @Override
    public SmsRole getByName(String roleName) {
        QueryWrapper<SmsRole> roleQueryWrapper = new QueryWrapper<>();
        roleQueryWrapper.eq("role_name",roleName);
        return roleMapper.selectOne(roleQueryWrapper);
    }
}
