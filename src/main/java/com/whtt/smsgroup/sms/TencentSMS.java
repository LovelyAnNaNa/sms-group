package com.whtt.smsgroup.sms;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
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
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: wbh
 * @Date: 2019/10/28 14:53
 * @Description:
 */
@Slf4j
public class TencentSMS {

    String[] phoneNumbers = {"18637736725"};

    //腾讯云群发短信
    public static Map<String,Object> massSend(String[] phoneNumbers, Integer templateId, String[] params, String sign, String extend, String ext) {
        try {
            SmsMultiSender msender = new SmsMultiSender(Constants.TENCENT_SMS_SDK_ID, Constants.TENCENT_SMS_SDK_KEY);
            //群发短信
            SmsMultiSenderResult result =  msender.sendWithParam("86", phoneNumbers,
                    templateId, params, sign, extend, ext);
            //发送结果map集合
            HashMap<String, Object> resultMap = new HashMap<>();

            //获取短信发送结果
            Integer resultCode = result.result;
            if(resultCode != 0){
                resultMap.put("sendCode",-1);
            }
            resultMap.put("sendCode",0);

            //发送成功条数,失败条数和总条数
            int successCount = 0;
            int failedCount = 0;
            int totalCount = 0;
            //获取群发结果
            ArrayList<SmsMultiSenderResult.Detail> details = result.details;
            //所有手机号的发送结果
            JSONArray sendInfo = new JSONArray();
            //遍历群发结果
            for (SmsMultiSenderResult.Detail detail : details) {
                JSONObject info = new JSONObject();

                int detailCode = detail.result;
                info.put("result",detailCode);
                //result为0时短信为发送成功
                if(detailCode == 0){
                    successCount++;
                }else{
                    failedCount++;
                }
                info.put("errmsg",detail.errmsg);
                info.put("fee",detail.fee);
                sendInfo.add(info);
                totalCount++;
            }

            resultMap.put("totalCount",totalCount);
            resultMap.put("successCount",successCount);
            resultMap.put("failedCount",failedCount);
            resultMap.put("result_msg",sendInfo.toJSONString());

            return resultMap;
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
        return null;
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
    public static JSONObject getPackageInformation(){
        //获取一个五位的随机数
        Integer randomNum = Integer.valueOf(RandomUtil.getRandmonNumber(5));
        String url = "https://yun.tim.qq.com/v5/tlssmssvr/getsmspackages?sdkappid=" + Constants.TENCENT_SMS_SDK_ID + "&random=" + randomNum;
        //拼接请求所需的签名
        String smsSign = "appkey=" + Constants.TENCENT_SMS_SDK_KEY +  "&random=" + randomNum + "&time=" + RandomUtil.getCurrenMillis();
        //签名加密
        smsSign = Sha256.getSHA256(smsSign);
        //请求所需参数
        JSONObject params = new JSONObject();
        params.put("offset",0);
        params.put("length",1000);
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
        //转换为JSON字符串
        JSONObject resultJson = JSON.parseObject(resultStr);
        //获取购买的套餐包总数
        Integer total = resultJson.getInteger("total");
        //从套餐包总数判断获取的信息是否正确
        if(total == null){
            return null;
        }
        //解析套餐包信息
        JSONArray dataJson = resultJson.getJSONArray("data");
        dataJson.forEach(data -> {
            //获取每个套餐包信息
            JSONObject jsonData = (JSONObject) data;
            String formTime = jsonData.getString("form_time");
            String toTime = jsonData.getString("to_time");
            DateUtil.parse(formTime,"yyyy-MM-dd HH:mm:ss");
        });
        return resultJson;
    }

}
