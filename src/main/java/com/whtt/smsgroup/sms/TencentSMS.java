package com.whtt.smsgroup.sms;

import com.github.qcloudsms.*;
import com.github.qcloudsms.httpclient.HTTPException;
import com.whtt.smsgroup.config.Constants;
import com.whtt.smsgroup.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;

import java.io.IOException;

/**
 * @Auther: wbh
 * @Date: 2019/10/28 14:53
 * @Description:
 */
@Slf4j
public class TencentSMS {

    String[] phoneNumbers = {"18637736725"};

    //发送验证码
    public static boolean singleSendCode(String phone,Integer templateId,String code,String smsSign,String extend,String ext) throws Exception{
        try {
//            Integer templateId = 412555;
            //拼接参数
            String[] params = {code};
//            String smsSign = "appkey=" + Constants.TENCENT_SMS_SDK_KEY +  "&random=" + RandomUtil.getRandmonNumber(10) + "&time=" + RandomUtil.getCurrenMillis() + "&mobile=" + phone;
//            smsSign = Sha256.getSHA256(smsSign);
//            smsSign = "网慧天拓";
            SmsSingleSender  msender = new SmsSingleSender(Constants.TENCENT_SMS_SDK_ID, Constants.TENCENT_SMS_SDK_KEY);
            //ext再返回结果中会返回回来
            SmsSingleSenderResult result =  msender.sendWithParam("86", phone, templateId, params, smsSign, extend, ext);
//            System.out.println(result);
            if(result.result == 0){
                log.info("给{}发送模板为{}的消息成功",phone,templateId);
                return true;
            }
        } catch (HTTPException e) {
            // HTTP 响应码错误
            e.printStackTrace();
        } catch (JSONException e) {
            // JSON 解析错误
            e.printStackTrace();
        } catch (IOException e) {
            // 网络 IO 错误
            e.printStackTrace();
        }

        return false;
    }
    
    public void testSMSPull() throws Exception{
        try {
            // Note: 短信拉取功能需要联系腾讯云短信技术支持（QQ：3012203387）开通权限
            int maxNum = 10;  // 单次拉取最大量
            SmsStatusPuller spuller = new SmsStatusPuller(Constants.TENCENT_SMS_SDK_ID, Constants.TENCENT_SMS_SDK_KEY);

            // 拉取短信回执
            SmsStatusPullCallbackResult callbackResult = spuller.pullCallback(maxNum);
            System.out.println(callbackResult);

            // 拉取回复，国际/港澳台短信不支持回复功能
            SmsStatusPullReplyResult replyResult = spuller.pullReply(maxNum);
            System.out.println(replyResult);
        } catch (HTTPException e) {
            // HTTP 响应码错误
            e.printStackTrace();
        } catch (JSONException e) {
            // JSON 解析错误
            e.printStackTrace();
        } catch (IOException e) {
            // 网络 IO 错误
            e.printStackTrace();
        }
    }
    
    public void testGetPhoneStatus() throws Exception{
        try {
            int beginTime = Integer.valueOf(RandomUtil.getCurrenMillis()) - 10000;  // 开始时间（UNIX timestamp）
            int endTime = Integer.valueOf(RandomUtil.getCurrenMillis());    // 结束时间（UNIX timestamp）
            int maxNum = 10;             // 单次拉取最大量
            SmsMobileStatusPuller mspuller = new SmsMobileStatusPuller(Constants.TENCENT_SMS_SDK_ID,Constants.TENCENT_SMS_SDK_KEY);

            // 拉取短信回执
            SmsStatusPullCallbackResult callbackResult = mspuller.pullCallback("86",
                    phoneNumbers[0], beginTime, endTime, maxNum);
            System.out.println(callbackResult);

            // 拉取回复，国际/港澳台短信不支持回复功能
            SmsStatusPullReplyResult replyResult = mspuller.pullReply("86",
                    phoneNumbers[0], beginTime, endTime, maxNum);
            System.out.println(replyResult);
        } catch (HTTPException e) {
            // HTTP 响应码错误
            e.printStackTrace();
        } catch (JSONException e) {
            // JSON 解析错误
            e.printStackTrace();
        } catch (IOException e) {
            // 网络 IO 错误
            e.printStackTrace();
        }
    }
}
