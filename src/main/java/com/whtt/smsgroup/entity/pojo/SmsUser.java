package com.whtt.smsgroup.entity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.whtt.smsgroup.base.UserBase;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Set;

/**
 * <p>
 * 
 * </p>
 *
 * @author wbh
 * @since 2019-10-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sms_user")
public class SmsUser extends UserBase<SmsUser> {

    private static final long serialVersionUID=1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户名
     */
    private String userName;

    private String userPassword;

    /**
     * 手机密码
     */
    private String phone;

    /**
     * 邮箱
     */
    private String emaile;

    /**
     * 角色id
     */
    private Integer roleId;

    /**
     * 状态（0启用  1禁用）
     */
    private Integer status;

    /**
     * 备用字段
     */
    private String standbyApplication;

    /**
     * 短信条数
     */
    private Integer total;


    public static final String ID = "id";

    public static final String USER_NAME = "user_name";

    public static final String USER_PASSWORD = "user_password";

    public static final String PHONE = "phone";

    public static final String EMAILE = "emaile";

    public static final String ROLE_ID = "role_id";

    public static final String STATUS = "status";

    public static final String STANDBY_APPLICATION = "standby_application";

    public static final String TOTAL = "total";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
    
    public void validateParams() {
        //调用JSR303验证工具，校验参数
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<SmsUser>> violations = validator.validate(this);
        Iterator<ConstraintViolation<SmsUser>> iter = violations.iterator();
        if (iter.hasNext()) {
            //需要springmvc捕获全局异常
            throw new ConstraintViolationException(violations);
        }
    }

}
