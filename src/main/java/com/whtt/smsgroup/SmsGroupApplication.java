package com.whtt.smsgroup;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.whtt.smsgroup.mapper")
public class SmsGroupApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmsGroupApplication.class, args);
    }

}
