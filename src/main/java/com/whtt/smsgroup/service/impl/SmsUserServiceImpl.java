package com.whtt.smsgroup.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whtt.smsgroup.entity.pojo.SmsRole;
import com.whtt.smsgroup.entity.pojo.SmsUser;
import com.whtt.smsgroup.mapper.SmsUserMapper;
import com.whtt.smsgroup.service.SmsRoleService;
import com.whtt.smsgroup.service.SmsUserService;
import com.whtt.smsgroup.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private SmsRoleService roleService;

    @Override
    public Integer saveNormalUser(SmsUser newUser) {
        //密码加密
        String oldPassword = newUser.getUserPassword();
        newUser.setUserPassword(SecurityUtil.encryptPassword(oldPassword));
        //获取最基础的角色信息
        SmsRole normalRole = roleService.getNormalRole();
        newUser.setRoleId(normalRole.getId());

        int result = userMapper.insert(newUser);
        return result;
    }

    @Override
    public SmsUser getByName(String username) {
        QueryWrapper<SmsUser> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("user_name",username);
        return userMapper.selectOne(userQueryWrapper);
    }
}
