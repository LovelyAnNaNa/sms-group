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
@TableName("sms_config")
public class SmsConfig extends Model<SmsConfig> {

    private static final long serialVersionUID=1L;

    /**
     * 配置key值
     */
    private String configKey;

    /**
     * 配置信息
     */
    private String configValue;

    private String configExplain;

    /**
     * 备用字段
     */
    private String standbyApplication;


    public static final String CONFIG_KEY = "config_key";

    public static final String CONFIG_VALUE = "config_value";

    public static final String CONFIG_EXPLAIN = "config_explain";

    public static final String STANDBY_APPLICATION = "standby_application";

    @Override
    protected Serializable pkVal() {
        return null;
    }

}
