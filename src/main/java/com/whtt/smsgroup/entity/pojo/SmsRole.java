package com.whtt.smsgroup.entity.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
    private String roleName;


    public static final String ID = "id";

    public static final String ROLE_NAME = "role_name";

    @Override
    protected Serializable pkVal() {
        return null;
    }

}
