package com.whtt.smsgroup.entity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 第三方短信平台模板
 * </p>
 *
 * @author wbh
 * @since 2019-11-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sms_platform_template")
public class SmsPlatformTemplate extends Model<SmsPlatformTemplate> {

    private static final long serialVersionUID=1L;

    /**
     * 模板id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 平台id
     */
    private Integer noteId;

    /**
     * 模板id
     */
    private String templateId;

    /**
     * 是否可用,1可用,0不可用
     */
    private Integer enable;

    /**
     * 短信模板内容
     */
    private String content;

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
     * 最后一次更改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    /**
     * 最后一次更改用户id
     */
    private Integer updateId;


    public static final String ID = "id";

    public static final String NOTE_ID = "note_id";

    public static final String TEMPLATE_ID = "template_id";

    public static final String ENABLE = "enable";

    public static final String CONTENT = "content";

    public static final String CREATE_TIME = "create_time";

    public static final String CREATE_ID = "create_id";

    public static final String UPDATE_TIME = "update_time";

    public static final String UPDATE_ID = "update_id";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
