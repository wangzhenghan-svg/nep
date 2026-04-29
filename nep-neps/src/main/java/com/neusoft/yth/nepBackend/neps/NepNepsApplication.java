package com.neusoft.yth.nepBackend.neps;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @ClassName NepNepsApplication
 * @Description NEPS公众监督员端启动类
 * @Author YTH
 * @Date 2024-05-29
 */
@SpringBootApplication
@MapperScan("com.neusoft.yth.nepBackend.neps.mapper")
public class NepNepsApplication {
    public static void main(String[] args) {
        SpringApplication.run(NepNepsApplication.class, args);
    }
}
