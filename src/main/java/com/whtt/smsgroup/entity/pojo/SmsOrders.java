package com.whtt.smsgroup.entity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.whtt.smsgroup.base.SmsOrderBase;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
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
 * @since 2019-11-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sms_orders")
public class SmsOrders extends SmsOrderBase<SmsOrders> {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 下单用户id
     */
    private Integer userId;

    /**
     * 下单手机号,如果有多个用','隔开
     */
    private String phone;

    /**
     * 发送的短信模板id
     */
    @NotNull(message = "短信模板id不能为空")
    private Integer templateId;

    /**
     * 发送的信息参数
     */
    private String params;

    /**
     * 发送结果(一串JSON字符串)
     */
    private String resultMsg;

    /**
     * 要发送的短信条数
     */
    private Integer preTotal;

    /**
     * 发送成功的短信条数
     */
    private Integer successTotal;

    /**
     * 失败的短信条数
     */
    private Integer failedTotal;

    /**
     * 下单时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date orderTime;

    /**
     * 所有短信发送完成结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date finishTime;

    /**
     * 总共消费的短信条数
     */
    private Integer feeTotal;

    /**
     * 预留字段1
     */
    private String standbyApplication1;

    /**
     * 预留字段2
     */
    private String standbyApplication2;

    public static final String ID = "id";

    public static final String USER_ID = "user_id";

    public static final String PHONE = "phone";

    public static final String TEMPLATE_ID = "template_id";

    public static final String PARAMS = "params";

    public static final String RESULT_MSG = "result_msg";

    public static final String PRE_TOTAL = "pre_total";

    public static final String SUCCESS_TOTAL = "success_total";

    public static final String FAILED_TOTAL = "failed_total";

    public static final String ORDER_TIME = "order_time";

    public static final String FINISH_TIME = "finish_time";

    public static final String FEE_TOTAL = "fee_total";

    public static final String STANDBY_APPLICATION1 = "standby_application1";

    public static final String STANDBY_APPLICATION2 = "standby_application2";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    public void validateParams() {
        //调用JSR303验证工具，校验参数
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<SmsOrders>> violations = validator.validate(this);
        Iterator<ConstraintViolation<SmsOrders>> iter = violations.iterator();
        if (iter.hasNext()) {
            //需要springmvc捕获全局异常
            throw new ConstraintViolationException(violations);
        }
    }
}
