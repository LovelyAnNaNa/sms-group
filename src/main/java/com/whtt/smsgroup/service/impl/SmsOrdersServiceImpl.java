package com.whtt.smsgroup.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whtt.smsgroup.common.CommonResult;
import com.whtt.smsgroup.entity.pojo.SmsOrders;
import com.whtt.smsgroup.entity.pojo.SmsPlatform;
import com.whtt.smsgroup.entity.pojo.SmsPlatformTemplate;
import com.whtt.smsgroup.entity.pojo.SmsUser;
import com.whtt.smsgroup.mapper.SmsOrdersMapper;
import com.whtt.smsgroup.service.SmsOrdersService;
import com.whtt.smsgroup.service.SmsPlatformService;
import com.whtt.smsgroup.service.SmsPlatformTemplateService;
import com.whtt.smsgroup.service.SmsUserService;
import com.whtt.smsgroup.sms.MessageSendUtil;
import com.whtt.smsgroup.util.SecurityUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wbh
 * @since 2019-11-01
 */
@Service
public class SmsOrdersServiceImpl extends ServiceImpl<SmsOrdersMapper, SmsOrders> implements SmsOrdersService {

    @Resource
    private SmsOrdersMapper ordersMapper;
    @Autowired
    private SmsPlatformTemplateService templateService;
    @Autowired
    private SmsUserService userService;
    @Autowired
    private SmsPlatformService platformService;


    @Override
    public CommonResult addOrder(SmsOrders newOrder) {

        //下单时间
        newOrder.setOrderTime(new Date());
        //获取拼接后的模板内容
        SmsPlatformTemplate templateInfo = templateService.replaceParam(newOrder.getParamMap(), newOrder.getTemplateId());
        //获取拼接后的短信内容所需要的短信费
        Integer fee = MessageSendUtil.checkSmsFee(templateInfo.getContent(), templateInfo.getPlatformId());
        //获取要发送的总条数
        int phoneCount = newOrder.getPhoneList().size();
        Integer totalFee = phoneCount * fee;
        //获取当前登录用户短信余额
        SmsUser loginUser = SecurityUtil.getLoginUser();
        Integer userTotal = loginUser.getTotal();
        if(userTotal < totalFee){
            return CommonResult.failed(String.format("您的短信余额不足,每条短信需要扣除您%d条短信,共需要%d条短信,您还剩余%d条短信",fee,totalFee,userTotal));
        }

        //手机集合转数组
        List<String> phoneList = newOrder.getPhoneList();
        String[] phoneArray = new String[phoneList.size()];
        phoneList.toArray(phoneArray);

        //获取发送参数
        Map<String, Object> paramMap = newOrder.getParamMap();
        String[] params = new String[paramMap.size()];
        int index = 0;
        for (String key : paramMap.keySet()) {
            params[index] = paramMap.get(key) + "";
            index++;
        }

        //获取要使用的短信平台的信息
        SmsPlatform usePlatform = platformService.getByPlatformId(templateInfo.getPlatformId());
        //发送短信
        Map<String, Object> resultMap = MessageSendUtil.sendSms(phoneArray,usePlatform.getPlatformId(),templateInfo.getTemplateId(),newOrder.getParamMap(),newOrder.getSignName());

        if(resultMap == null){
            return CommonResult.failed("发送失败,请稍后再试");
        }

        String sendCode = resultMap.get("sendCode").toString();
        if(StringUtils.isBlank(sendCode) || !"0".equals(sendCode)){
        }
        //获取发送结果
        Integer failedCount = Integer.valueOf(resultMap.get("failedCount") + "");
        Integer successCount = Integer.valueOf(resultMap.get("successCount") + "");
        Integer totalCount = Integer.valueOf(resultMap.get("totalCount") + "");

        String resultMsg = resultMap.get("result_msg").toString();
        //设置订单信息
        newOrder.setResultMsg(resultMsg);
        newOrder.setPreTotal(totalCount);
        newOrder.setFailedTotal(failedCount);
        newOrder.setSuccessTotal(successCount);
        newOrder.setFeeTotal(successCount);
        newOrder.setPhone(newOrder.getPhoneList().toString());
        newOrder.setParams(JSON.toJSONString(newOrder.getParamMap()));
        newOrder.setFinishTime(new Date());
        newOrder.setUserId(loginUser.getId());

        //减去第三方平台剩余短信数量
        usePlatform.setUsableAmount(usePlatform.getUsedAmount() - successCount);
        usePlatform.setUsedAmount(usePlatform.getUsedAmount() + successCount);
        platformService.updateById(usePlatform);

        //用户可用余额更新
        loginUser.setTotal(loginUser.getTotal() - successCount);
        userService.updateById(loginUser);

        int result = ordersMapper.insert(newOrder);

        return CommonResult.success(String.format("短信发送成功,共发送%d条短信,%d条失败",totalCount,failedCount));
    }
}
