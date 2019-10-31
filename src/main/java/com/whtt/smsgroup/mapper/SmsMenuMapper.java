package com.whtt.smsgroup.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.whtt.smsgroup.entity.pojo.SmsMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wbh
 * @since 2019-10-31
 */
public interface SmsMenuMapper extends BaseMapper<SmsMenu> {

    List<SmsMenu> getUserMenu(@Param("userId") Integer userId);
}
