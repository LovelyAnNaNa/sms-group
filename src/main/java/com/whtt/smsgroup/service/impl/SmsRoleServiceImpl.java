package com.whtt.smsgroup.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whtt.smsgroup.entity.pojo.SmsRole;
import com.whtt.smsgroup.entity.pojo.SmsRoleMenu;
import com.whtt.smsgroup.entity.pojo.SmsUserRole;
import com.whtt.smsgroup.mapper.SmsRoleMapper;
import com.whtt.smsgroup.service.SmsRoleMenuService;
import com.whtt.smsgroup.service.SmsRoleService;
import com.whtt.smsgroup.service.SmsUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class SmsRoleServiceImpl extends ServiceImpl<SmsRoleMapper, SmsRole> implements SmsRoleService {

    @Resource
    private SmsRoleMapper roleMapper;
    @Autowired
    private SmsRoleMenuService roleMenuService;
    @Autowired
    private SmsUserRoleService userRoleService;

    @Override
    public Integer deleteById(Integer roleId) {
        int count = roleMapper.deleteById(roleId);
        //获取有这个角色的所有用户信息
        QueryWrapper<SmsUserRole> roleMenuQueryWrapper = new QueryWrapper<>();
        roleMenuQueryWrapper.eq("role_id",roleId);
        roleMenuQueryWrapper.orderByAsc("user_id");
        List<SmsUserRole> roleMenuList = userRoleService.list(roleMenuQueryWrapper);

        UpdateWrapper<SmsRoleMenu> roleMenuUpdateWrapper = new UpdateWrapper<>();
        roleMenuUpdateWrapper.eq("role_id",roleId);
        roleMenuService.remove(roleMenuUpdateWrapper);

        return count;
    }

    @Override
    public SmsRole getByName(String roleName) {
        QueryWrapper<SmsRole> roleQueryWrapper = new QueryWrapper<>();
        roleQueryWrapper.eq("role_name",roleName);
        return roleMapper.selectOne(roleQueryWrapper);
    }
}
