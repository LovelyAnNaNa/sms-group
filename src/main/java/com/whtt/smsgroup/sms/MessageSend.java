package com.whtt.smsgroup.sms;

/**
 * @Auther: wbh
 * @Date: 2019/11/1 15:12
 * @Description:
 */
public class MessageSend {



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
