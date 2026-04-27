
package com.neusoft.yth.nepBackend.nepv;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.neusoft.yth.nepBackend.nepv.mapper")
public class NepvApplication {
    public static void main(String[] args) {
        SpringApplication.run(NepvApplication.class, args);
    }
}
