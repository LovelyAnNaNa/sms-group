package com.whtt.smsgroup.util;

import java.util.Random;

/**
 * @Auther: wbh
 * @Date: 2019/10/28 14:51
 * @Description: 用于生成随机数
 */
public class RandomUtil {

    private static Random random = new Random();

    public static String getRandmonNumber(Integer length){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    /**
     * 获取时间戳
     * @return
     */
    public static String getCurrenMillis(){
        String currentTime = System.currentTimeMillis() / 1000 + "";
        return currentTime;
    }
}
