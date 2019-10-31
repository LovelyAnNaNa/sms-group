package com.whtt.smsgroup.base;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @Auther: wbh
 * @Date: 2019/10/30 11:59
 * @Description:
 */
@Getter
@Setter
public class UserBase<T> extends Model {

    /**
     * 用户注册时存储手机验证码
     */
    @NotBlank(message = "手机验证码不能为空")
    @TableField(exist = false)
    protected String phoneCode;
}
