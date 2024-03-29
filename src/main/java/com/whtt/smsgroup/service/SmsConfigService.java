package com.whtt.smsgroup.service;

import com.whtt.smsgroup.entity.pojo.SmsConfig;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wbh
 * @since 2019-10-31
 */
public interface SmsConfigService extends IService<SmsConfig> {

    Integer getNormalRoleId();
}
