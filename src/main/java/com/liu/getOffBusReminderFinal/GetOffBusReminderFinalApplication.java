package com.liu.getOffBusReminderFinal;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.liu.getOffBusReminder.dao")
public class GetOffBusReminderFinalApplication {

    public static void main(String[] args) {
        SpringApplication.run(GetOffBusReminderFinalApplication.class, args);
    }

}
