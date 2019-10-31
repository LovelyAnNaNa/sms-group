package com.whtt.smsgroup.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whtt.smsgroup.entity.pojo.SmsRole;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wbh
 * @since 2019-10-31
 */
public interface SmsRoleService extends IService<SmsRole> {

    SmsRole getByName(String roleName);
}
