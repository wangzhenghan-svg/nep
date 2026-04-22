package com.neusoft.yth.nepBackend.member;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author YTH
 * 定义MyBatis的接口的扫描范围
 */
@SpringBootApplication
@MapperScan("com.neusoft.yth.nepBackend.member.mapper")
public class NepMemberApplication {
    public static void main(String[] args) {
        SpringApplication.run(NepMemberApplication.class, args);
    }
}


