package com.whtt.smsgroup.base;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * @Auther: wbh
 * @Date: 2019/11/1 13:59
 * @Description:
 */
@Getter
@Setter
public class SmsOrderBase<T> extends Model {

    /**
     * 短信签名内容
     */
    @TableField(exist = false)
    private String signName;

    /**
     * 模板参数
     * 腾讯key为数字
     * 阿里key为变量名
     */
    @TableField(exist = false)
    private Map<String,Object> paramMap;

    /**
     * 下单手机号集合,也可用作已下订单信息展示给前台
     */
    @TableField(exist = false)
    @NotNull(message = "不能为空")
    private List<String> phoneList;
}
