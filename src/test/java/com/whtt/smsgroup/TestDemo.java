package com.whtt.smsgroup;

import org.junit.Test;

/**
 * @Auther: wbh
 * @Date: 2019/10/31 15:30
 * @Description:
 */
public class TestDemo {

    @Test
    public void test() throws Exception{
//        System.out.println("ROLE_ADMIN".indexOf("ROLE_"));
//        Pattern pattern = Pattern.compile("^[1]\\d{10}$");
//        System.out.println(pattern.matcher("18633333233").matches());
        System.out.println(String.format("您的短信余额不足,每条短信需要扣除您%d条短信,共需要%d条短信,您的余额为%d", 1,2, 31));
    }
}
