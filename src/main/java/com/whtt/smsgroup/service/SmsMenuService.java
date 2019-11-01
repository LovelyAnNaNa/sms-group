package com.whtt.smsgroup.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whtt.smsgroup.entity.pojo.SmsMenu;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wbh
 * @since 2019-10-31
 */
public interface SmsMenuService extends IService<SmsMenu> {

    //根据用户id获取用户的菜单列表
    Collection<SmsMenu> getUserMenu(@Param("userId") Integer userId);
}
