package com.whtt.smsgroup;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.qcloudsms.*;
import com.github.qcloudsms.httpclient.HTTPException;
import com.whtt.smsgroup.config.Constants;
import com.whtt.smsgroup.util.HttpUtils;
import com.whtt.smsgroup.util.RandomUtil;
import com.whtt.smsgroup.util.Sha256;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.jsoup.Connection;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @Auther: wbh
 * @Date: 2019/10/28 14:53
 * @Description:
 */
@Slf4j
public class TencentSMS {

    String[] phoneNumbers = {"11111111111"};
    String content = "q王qqqqqqqq";

    @Test
    public void testg() throws Exception{
        String currentTime = System.currentTimeMillis() / 1000 + "";
        System.out.println(currentTime);
    }

    @Test
    public void httpSendTemplateSms(){
        JSONObject params = new JSONObject();
        //组装公共参数
        params.put("Version","2019-07-11");
        params.put("Action","SendSms");
        List<String> phoneNumbers = Arrays.asList("18637736725");

        //组装手机号参数
        for (int i = 0; i < phoneNumbers.size(); i++) {
            params.put("PhoneNumberSet." + i, phoneNumbers.get(i));
        }

        //模板ID
        params.put("TemplateID",412555);
        //appID
        params.put("SmsSdkAppid",Constants.TENCENT_SMS_SDK_ID);
        HashMap<String, String> templateParam = new HashMap<>();
        templateParam.put("0","1234");
        //组装模板参数
        if(templateParam != null){
            int templateParamIndex = 0;
            for (String key : templateParam.keySet()) {
                params.put("TemplateParamSet." + templateParam ,templateParam.get(key));
                templateParamIndex++;
            }
        }
        try {
            Connection.Response response = HttpUtils.post("https://sms.tencentcloudapi.com/", params.toJSONString());
            System.out.println(response.body().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    
    @Test
    public void testSendMany() throws Exception{
        try {
            String[] params = {"5678"};
            SmsMultiSender msender = new SmsMultiSender(Constants.TENCENT_SMS_SDK_ID, Constants.TENCENT_SMS_SDK_KEY);
            SmsMultiSenderResult result =  msender.sendWithParam("86", phoneNumbers,
                    412555, params, "网慧天拓", "", "");
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

   @Test
   public void testManySend() throws Exception{
       HashMap<String, Object> params = new HashMap<>();
       params.put("ext","ext");
       params.put("extend","");
       params.put("params",new String[]{"123456"});
       String smsSig = "appkey=" + Constants.TENCENT_SMS_SDK_KEY +  "&random=" + RandomUtil.getRandmonNumber(10) + "&time=" + RandomUtil.getCurrenMillis() + "&mobile=" + phoneNumbers[0];
       smsSig = Sha256.getSHA256(smsSig);
       params.put("sig",smsSig);
       params.put("sign","网慧天拓");

       JSONArray telArray = new JSONArray();
       JSONObject tel1 = new JSONObject();
       tel1.put("mobile","15839272879");
       tel1.put("nationcode","86");
       JSONObject tel2 = new JSONObject();
       tel2.put("mobile","18637736725");
       tel2.put("nationcode","86");
       telArray.add(tel1);
       telArray.add(tel2);

       params.put("time",RandomUtil.getCurrenMillis());
       params.put("tpl_id",412555);

       String url = "https://yun.tim.qq.com/v5/tlssmssvr/sendmultisms2?sdkappid=" + Constants.TENCENT_SMS_SDK_ID + "&random=" + RandomUtil.getRandmonNumber(20);
       Connection.Response response = HttpUtils.post(url, telArray.toJSONString());
       System.out.println(response.body().toString());
   }

    //指定模板id单发消息
    @Test
    public void test() throws Exception{
        try {
            Integer templateId = 412555;
            String[] params = {"5678"};
            params[0] = RandomUtil.getRandmonNumber(4);
            String smsSign = "appkey=" + Constants.TENCENT_SMS_SDK_KEY +  "&random=" + RandomUtil.getRandmonNumber(10) + "&time=" + RandomUtil.getCurrenMillis() + "&mobile=" + phoneNumbers[0];
            smsSign = Sha256.getSHA256(smsSign);
            smsSign = "网慧天拓";
            SmsSingleSender  msender = new SmsSingleSender(Constants.TENCENT_SMS_SDK_ID, Constants.TENCENT_SMS_SDK_KEY);
            SmsSingleSenderResult result =  msender.sendWithParam("86", phoneNumbers[0], templateId, params, smsSign, "", "123456");
            System.out.println(result);
            if(result.result == 0){
                log.info("给{}发送模板为{}的消息成功",phoneNumbers[0],templateId);
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
    }
    
    @Test
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
    
    @Test
    public void testPackageInformation() throws Exception{
        Integer randomNum = Integer.valueOf(RandomUtil.getRandmonNumber(5));
        String url = "https://yun.tim.qq.com/v5/tlssmssvr/getsmspackages?sdkappid=" + Constants.TENCENT_SMS_SDK_ID + "&random=" + randomNum;
        String smsSign = "appkey=" + Constants.TENCENT_SMS_SDK_KEY +  "&random=" + randomNum + "&time=" + RandomUtil.getCurrenMillis();
        smsSign = Sha256.getSHA256(smsSign);
        JSONObject params = new JSONObject();
        params.put("offset",0);
        params.put("length",100);
        params.put("sig",smsSign);
        params.put("time",Integer.valueOf(RandomUtil.getCurrenMillis()));
        Connection.Response response = HttpUtils.post(url, params.toJSONString());
        //获取返回的购买的套餐包信息
        String resultStr = response.body().toString();
        System.out.println(resultStr);
        //转换为JSON字符串
        JSONObject resultJson = JSON.parseObject(resultStr);
        //获取购买的套餐包总数
        Integer total = resultJson.getInteger("total");
        if(total == null){

        }
        //解析套餐包信息
        JSONArray dataJson = resultJson.getJSONArray("data");
        //获取当前时间
        Date nowTime = new Date();
        String timeFormat = "yyyy-MM-dd HH:mm:ss";
        dataJson.forEach(e -> {
            //获取每个套餐包信息
            JSONObject data = (JSONObject) e;
            //获取套餐包到期时间
            String toTimeStr = data.getString("to_time");
            DateTime toTime = DateUtil.parse(toTimeStr, timeFormat);
            //如果套餐包信息未过期
            if(nowTime.before(toTime)){
                System.out.println("jsonData.getString(\"amount\") = " + data.getString("amount"));
                System.out.println("jsonData.getString(\"used\") = " + data.getString("used"));
            }
        });

    }
    
    @Test
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
