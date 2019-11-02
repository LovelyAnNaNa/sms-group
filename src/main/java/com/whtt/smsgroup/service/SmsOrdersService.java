package com.whtt.smsgroup.service;

import com.whtt.smsgroup.common.CommonResult;
import com.whtt.smsgroup.entity.pojo.SmsOrders;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wbh
 * @since 2019-11-01
 */
public interface SmsOrdersService extends IService<SmsOrders> {

    CommonResult onOrder(SmsOrders newOrder);
}
