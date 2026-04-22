package com.neusoft.yth.nepBackend.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @author YTH
 * member实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Member {
    /**
     * 用户id
     */
    private int memId;
    /**
     * 用户电话号码11位，不能重复
     */
    private String memNumber;

    /**
     * 用户名称
     */
    private String memName;
    /**
     * 登录密码
     */
    private String memPwd;
    /**
     * 用户性别（男、女）
     */
    private String memSex;
    /**
     * 用户出生日期（年月日）
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date memBirthday;
    /**
     * 用户省区域id
     */
    private String provinceId;
    /**
     * 用户市区域id
     */
    private String cityId;
    /**
     * 用户家庭详细住址
     */
    private String address;
    /**
     * 用户角色
     * 1：管理员
     * 2：网格员
     * 3：公众监督员
     */
    private String roleId;
    /**
     * 成员状态：
     * 0——已删除
     * 1——正常使用
     * 2——可工作
     * 3——临时抽调
     * 4——休假
     * 5——其他
     */
    private String memState;
    /**
     * 验证码
     */
    private String verifyCode;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp codeTime;

    /**
     * 备注
     */
    private String remarks;

}
