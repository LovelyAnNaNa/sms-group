package com.whtt.smsgroup.sms;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.qcloudsms.SmsMultiSender;
import com.github.qcloudsms.SmsMultiSenderResult;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import com.whtt.smsgroup.config.Constants;
import com.whtt.smsgroup.util.HttpUtils;
import com.whtt.smsgroup.util.RandomUtil;
import com.whtt.smsgroup.util.Sha256;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.jsoup.Connection;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @Auther: wbh
 * @Date: 2019/10/28 14:53
 * @Description:
 */
@Slf4j
public class TencentSMS {

    String[] phoneNumbers = {"18637736725"};

    //群发短信
    public void massCode(String[] phoneNumbers,Integer templateId,String code,String sign,String extend,String ext) {
        try {
            String[] params = {code};
            SmsMultiSender msender = new SmsMultiSender(Constants.TENCENT_SMS_SDK_ID, Constants.TENCENT_SMS_SDK_KEY);
            //群发短信
            SmsMultiSenderResult result =  msender.sendWithParam("86", phoneNumbers,
                    templateId, params, sign, extend, ext);
            //获取群发结果
            ArrayList<SmsMultiSenderResult.Detail> details = result.details;
            //遍历群发结果
            details.forEach(d -> {

            });

            System.out.println(result);
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

    //单发验证码
    public static boolean singleSendCode(String phone,Integer templateId,String code,String smsSign,String extend,String ext) throws Exception{
        try {
            //拼接参数
            String[] params = {code};
            SmsSingleSender  msender = new SmsSingleSender(Constants.TENCENT_SMS_SDK_ID, Constants.TENCENT_SMS_SDK_KEY);
            //ext再返回结果中会返回回来
            SmsSingleSenderResult result =  msender.sendWithParam("86", phone, templateId, params, smsSign, extend, ext);
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

    /**
     * 获取腾讯云购买的短信套餐包信息
     * @return
     */
    public JSONObject getPackageInformation(){
        Integer randomNum = Integer.valueOf(RandomUtil.getRandmonNumber(5));
        String url = "https://yun.tim.qq.com/v5/tlssmssvr/getsmspackages?sdkappid=" + Constants.TENCENT_SMS_SDK_ID + "&random=" + randomNum;
        //拼接请求所需的签名
        String smsSign = "appkey=" + Constants.TENCENT_SMS_SDK_KEY +  "&random=" + randomNum + "&time=" + RandomUtil.getCurrenMillis();
        //签名加密
        smsSign = Sha256.getSHA256(smsSign);
        //请求所需参数
        JSONObject params = new JSONObject();
        params.put("offset",0);
        params.put("length",1);
        params.put("sig",smsSign);
        params.put("time",Integer.valueOf(RandomUtil.getCurrenMillis()));
        Connection.Response response = null;
        try {
            //发送请求获取套餐包信息
            response = HttpUtils.post(url, params.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        //获取返回的购买的套餐包信息
        String resultStr = response.body().toString();
        System.out.println(resultStr);
        //转换为JSON字符串
        JSONObject resultJson = JSON.parseObject(resultStr);
        //获取购买的套餐包总数
        Integer total = resultJson.getInteger("total");
        if(total == null){
            return null;
        }
        return resultJson;
    }

}
