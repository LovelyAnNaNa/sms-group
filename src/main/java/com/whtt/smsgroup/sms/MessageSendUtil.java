package com.whtt.smsgroup.sms;

import java.util.Map;

/**
 * @Auther: wbh
 * @Date: 2019/11/1 15:12
 * @Description:
 */
public class MessageSendUtil {

    public static Map<String,Object> sendSms(String[] phoneNumbers,Integer noteId,String templateId,Map<String,Object> paramMap
                ,String signName){
        Map<String,Object> resultMap = null;
        //根据通道id选择对应的通道发送
        if(noteId == 1){
            //获取发送参数
            String[] params = new String[paramMap.size()];
            int index = 0;
            for (String key : paramMap.keySet()) {
                params[index] = paramMap.get(key) + "";
                index++;
            }
            resultMap = TencentSMS.massSend(phoneNumbers,Integer.valueOf(templateId),params,signName,"","");
        }
//        Map<String, Object> resultMap = TencentSMS.massSend(phoneArray, Integer.valueOf(templateInfo.getTemplateId()), params, "new", "1", "");
        return resultMap;
    }

    /**
     * 计算短信内容所需费用
     * @return
     */
    public static Integer checkSmsFee(String smsContent,Integer noteId){
        //根据平台id进行不同的计算方式 1腾讯,2阿里
        if(noteId == 1){
            if(smsContent.length() <= 1){
                return 1;
            }else{
                return (int)Math.ceil(smsContent.length() / 67.0);
            }
        }
        return null;
    }
}
