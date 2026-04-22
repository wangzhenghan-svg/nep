package com.neusoft.yth.nepBackend.aqi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @ClassName NepAqiApplication
 * @Description
 * @Author YTH
 * @Date 2024-05-29 12:48
 */
@SpringBootApplication
@MapperScan("com.neusoft.yth.nepBackend.aqi.mapper")
public class NepAqiApplication {
    public static void main(String[] args) {
        org.springframework.boot.SpringApplication.run(NepAqiApplication.class, args);
    }
}


