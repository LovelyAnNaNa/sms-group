package com.whtt.smsgroup.entity.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.NotBlank;
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
@TableName("sms_role")
public class SmsRole extends Model<SmsRole> {

    private static final long serialVersionUID=1L;

    private Integer id;

    /**
     * 角色名字
     */
    @NotBlank(message = "角色名称不能为空")
    private String roleName;


    public static final String ID = "id";

    public static final String ROLE_NAME = "role_name";

    @Override
    protected Serializable pkVal() {
        return null;
    }

    public void validateParams() {
        //调用JSR303验证工具，校验参数
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<SmsRole>> violations = validator.validate(this);
        Iterator<ConstraintViolation<SmsRole>> iter = violations.iterator();
        if (iter.hasNext()) {
            //需要springmvc捕获全局异常
            throw new ConstraintViolationException(violations);
        }
    }
}
