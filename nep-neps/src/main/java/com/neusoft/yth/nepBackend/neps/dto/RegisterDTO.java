package com.neusoft.yth.nepBackend.neps.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @ClassName RegisterDTO
 * @Description 注册数据传输对象
 * @Author YTH
 * @Date 2024-05-29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RegisterDTO {
    private String phoneNumber;
    private String password;
    private String realName;
    private int age;
    private String gender;
}
