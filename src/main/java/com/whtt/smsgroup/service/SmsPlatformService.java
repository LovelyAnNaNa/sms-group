package com.whtt.smsgroup.service;

import com.whtt.smsgroup.entity.pojo.SmsPlatform;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wbh
 * @since 2019-11-02
 */
public interface SmsPlatformService extends IService<SmsPlatform> {

    SmsPlatform getByPlatformId(Integer platformId);

    //获取默认平台信息
    SmsPlatform getDefaultPlatform();
}
