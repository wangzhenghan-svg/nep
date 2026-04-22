package com.neusoft.yth.nepBackend;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

/**
 * @ClassName CodeGenerator
 * @Description
 * @Author YTH
 * @Date 2024-05-29 14:24
 */
public class CodeGenerator {
    private static  String  drivename= "com.mysql.cj.jdbc.Driver";
    private static  String  username= "root";
    private static  String  password= "123456";
    private static  String  url ="jdbc:mysql://127.0.0.1:3306/nep?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=UTC";

    public static void main(String[] args) {
        // 使用 FastAutoGenerator 快速配置代码生成器
        FastAutoGenerator.create(url, username, password)
                .globalConfig(builder -> {
                    builder.author("YTH") // 设置作者
                            .dateType(DateType.ONLY_DATE) // 设置时间类型策略
                            .commentDate("yyyy-MM-dd") // 设置注释日期格式
                            .outputDir("C:\\Users\\YTH\\Work\\neu-neusoft24\\nepBackend\\nep-grid\\src\\main\\java")
                            .enableSwagger(); // 输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.neusoft.yth.nepBackend.grid") // 设置父包名
                            .controller("controller")
                            .entity("entity") // 设置实体类包名
                            .mapper("mapper") // 设置 Mapper 接口包名
                            .service("service") // 设置 Service 接口包名
                            .serviceImpl("service.impl") // 设置 Service 实现类包名
                            .xml("mapper")
                            .pathInfo(Collections.singletonMap(OutputFile.xml, "C:\\Users\\YTH\\Work\\neu-neusoft24\\nepBackend\\nep-grid\\src\\main\\resources\\mapper"));
                    // 设置路径配置信息; // 设置 Mapper XML 文件包名
                })
                .strategyConfig(builder -> {
                    builder.addInclude("grid") // 设置需要生成的表名

                            .entityBuilder()
                            .enableLombok() // 启用 Lombok
                            .enableTableFieldAnnotation() // 启用字段注解
                            .controllerBuilder()
                            .enableRestStyle(); // 启用 REST 风格
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用 Freemarker 模板引擎
                .execute(); // 执行生成
    }
}
