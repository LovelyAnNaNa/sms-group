package com.whtt.smsgroup.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whtt.smsgroup.entity.pojo.SmsUser;
import com.whtt.smsgroup.entity.pojo.SmsUserRole;
import com.whtt.smsgroup.mapper.SmsUserMapper;
import com.whtt.smsgroup.service.SmsConfigService;
import com.whtt.smsgroup.service.SmsRoleService;
import com.whtt.smsgroup.service.SmsUserRoleService;
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
    @Autowired
    private SmsUserRoleService userRoleService;
    @Autowired
    private SmsConfigService configService;


    @Override
    public Integer updateUserStatus(Integer userId, Integer status) {
        SmsUser updateUser = new SmsUser();
        updateUser.setId(userId);
        updateUser.setStatus(status);
        int count = userMapper.updateById(updateUser);
        return count;
    }

    @Override
    public Integer saveNormalUser(SmsUser newUser) {
        //密码加密
        String oldPassword = newUser.getUserPassword();
        newUser.setUserPassword(SecurityUtil.encryptPassword(oldPassword));

        //如果用户保存成功,为用户设置默认角色
        int result = userMapper.insert(newUser);
        if(result > 0){
            SmsUserRole newUserRole = new SmsUserRole();
            //获取最基础的角色信息
            newUserRole.setRoleId(configService.getNormalRoleId());
            newUserRole.setUserId(newUser.getId());
            //保存新增用户的角色信息
            boolean save = userRoleService.save(newUserRole);
            if(save){
                return 1;
            }
        }
        return 0;
    }

    @Override
    public SmsUser getByName(String username) {
        QueryWrapper<SmsUser> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("user_name",username);
        return userMapper.selectOne(userQueryWrapper);
    }
}
