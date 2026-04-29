package com.neusoft.yth.nepBackend.neps.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @ClassName LoginDTO
 * @Description 登录数据传输对象
 * @Author YTH
 * @Date 2024-05-29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoginDTO {
    private String phoneNumber;
    private String password;
}
