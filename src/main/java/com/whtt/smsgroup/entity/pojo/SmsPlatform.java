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
@TableName("sms_platform")
public class SmsPlatform extends Model<SmsPlatform> {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

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


    public static final String ID = "id";

    public static final String PLATFORM_ID = "platform_id";

    public static final String PLATFORM_NAME = "platform_name";

    public static final String TOTAL_AMOUNT = "total_amount";

    public static final String USABLE_AMOUNT = "usable_amount";

    public static final String USED_AMOUNT = "used_amount";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
