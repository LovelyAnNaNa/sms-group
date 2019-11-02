package com.whtt.smsgroup.entity.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2019-11-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("note_into")
public class NoteInto extends Model<NoteInto> {

    private static final long serialVersionUID=1L;

    /**
     * 套餐包短信总数
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 平台名字
     */
    private String platformName;

    /**
     * 开发者账号
     */
    private String appid;

    /**
     * 开发者秘钥
     */
    private String appkey;

    /**
     * 模板id
     */
    private String templateId;

    /**
     * 备用字段
     */
    private String standbyApplication;


    public static final String ID = "id";

    public static final String PLATFORM_NAME = "platform_name";

    public static final String APPID = "appid";

    public static final String APPKEY = "appkey";

    public static final String TEMPLATE_ID = "template_id";

    public static final String STANDBY_APPLICATION = "standby_application";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
