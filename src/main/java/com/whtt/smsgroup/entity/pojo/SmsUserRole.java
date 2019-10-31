package com.whtt.smsgroup.entity.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

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
@TableName("sms_user_role")
public class SmsUserRole extends Model<SmsUserRole> {

    private static final long serialVersionUID=1L;

    /**
     * 用户id
     */
    @TableField("user_id")
    private Integer userId;

    /**
     * 角色id
     */
    private Integer roleId;


    public static final String USER_ID = "user_Id";

    public static final String ROLE_ID = "role_id";

    @Override
    protected Serializable pkVal() {
        return null;
    }

}
