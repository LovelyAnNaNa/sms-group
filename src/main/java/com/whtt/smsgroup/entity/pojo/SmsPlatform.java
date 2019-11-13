package com.whtt.smsgroup.entity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2019-11-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sms_platform")
public class SmsPlatform extends Model<SmsPlatform> {

    private static final long serialVersionUID=1L;

    @TableId(value = "iid", type = IdType.AUTO)
    private Integer iid;

    /**
     * 平台id用于识别使用哪个平台(1腾讯，1阿里)
     */
    private Integer platformId;

    /**
     * 平台名称
     */
    private String platformName;

    /**
     * 总条数
     */
    private Integer totalAmount;

    /**
     * 可用短信数量
     */
    private Integer usableAmount;

    /**
     * 已用短信数量
     */
    private Integer usedAmount;

    /**
     * 是否为默认通道,1是,0不是
     */
    private Integer defaultUse;


    public static final String ID = "id";

    public static final String PLATFORM_ID = "platform_id";

    public static final String PLATFORM_NAME = "platform_name";

    public static final String TOTAL_AMOUNT = "total_amount";

    public static final String USABLE_AMOUNT = "usable_amount";

    public static final String USED_AMOUNT = "used_amount";

    public static final String DEFAULT_USE = "default_use";

    @Override
    protected Serializable pkVal() {
        return this.iid;
    }

}
