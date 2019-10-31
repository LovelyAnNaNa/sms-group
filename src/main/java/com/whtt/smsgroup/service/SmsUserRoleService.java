package com.whtt.smsgroup.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whtt.smsgroup.entity.pojo.SmsUserRole;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wbh
 * @since 2019-10-31
 */
public interface SmsUserRoleService extends IService<SmsUserRole> {

    List<SmsUserRole> listByUserId(Integer userId);
}
