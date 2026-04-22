package com.neusoft.yth.nepBackend.grid;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @ClassName NepGridApplication
 * @Description
 * @Author YTH
 * @Date 2024-05-29 14:30
 */
@SpringBootApplication
@MapperScan("com.neusoft.yth.nepBackend.grid.mapper")
public class NepGridApplication {
    public static void main(String[] args) {
        SpringApplication.run(NepGridApplication.class, args);
    }
}
