package com.whtt.smsgroup.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whtt.smsgroup.entity.pojo.SmsRoleMenu;
import com.whtt.smsgroup.mapper.SmsRoleMenuMapper;
import com.whtt.smsgroup.service.SmsRoleMenuService;
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
public class SmsRoleMenuServiceImpl extends ServiceImpl<SmsRoleMenuMapper, SmsRoleMenu> implements SmsRoleMenuService {

    @Resource
    private SmsRoleMenuMapper roleMenuMapper;

    @Override
    public List<SmsRoleMenu> listByRoleId(Integer roleId) {
        QueryWrapper<SmsRoleMenu> roleMenuQueryWrapper = new QueryWrapper<>();
        roleMenuQueryWrapper.eq("role_id",roleId);
        return roleMenuMapper.selectList(roleMenuQueryWrapper);
    }
}
