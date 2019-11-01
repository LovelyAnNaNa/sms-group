package com.whtt.smsgroup.service;

import com.whtt.smsgroup.entity.pojo.SmsUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wbh
 * @since 2019-10-31
 */
public interface SmsUserService extends IService<SmsUser> {

    Integer updateUserStatus(Integer userId,Integer status);

    Integer saveNormalUser(SmsUser newUser);

    SmsUser getByName(String username);
}
