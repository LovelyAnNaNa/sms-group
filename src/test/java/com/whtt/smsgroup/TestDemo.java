package com.whtt.smsgroup;

import org.junit.Test;

import java.util.regex.Pattern;

/**
 * @Auther: wbh
 * @Date: 2019/10/31 15:30
 * @Description:
 */
public class TestDemo {

    @Test
    public void test() throws Exception{
//        System.out.println("ROLE_ADMIN".indexOf("ROLE_"));
        Pattern pattern = Pattern.compile("^[1]\\d{10}$");
        System.out.println(pattern.matcher("18633333233").matches());
    }
}
