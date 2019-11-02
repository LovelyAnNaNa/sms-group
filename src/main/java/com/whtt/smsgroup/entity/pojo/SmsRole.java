package com.whtt.smsgroup.entity.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
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

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 创建人
     */
    private Integer createId;

    /**
     * 最后一次修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    /**
     * 最后一次修改的用户id
     */
    private Integer updateId;

    /**
     * 备注,展示给前台用户查看
     */
    private String remarks;

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
