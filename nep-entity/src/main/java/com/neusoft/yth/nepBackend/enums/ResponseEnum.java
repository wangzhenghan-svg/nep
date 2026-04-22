package com.neusoft.yth.nepBackend.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author YTH
 * @create 2024-05-26 16:01
 */
@Getter
public enum ResponseEnum {
    //2:响应成功

    /**响应成功**/
    SUCCESS(200, "操作成功"),

    //3:代表用户的行为问题

    REGISTER_NAME_IN_USE(301,"用户名已存在"),
    LOGIN_NAME_NOT_EXIST(302, "用户名不存在"),
    LOGIN_PASSWORD_ERROR(303, "登录密码错误"),
    LOGIN_VERIFY_CODE_ERROR(304,"验证码生成失败" ),
    LOGIN_CODE_ERROR_OR_TIMEOUT( 305, "验证失败或超时"),



    //4:服务器端的数据问题

    BAD_REQUEST(400, "错误的请求"),
    UNAUTHORIZED(401, "未授权的请求，请联系管理员授权"),
    RESULT_IS_EMPTY(402, "查询正常结束，查询结果为空"),
    FORBIDDEN(403, "资源不允许访问"),
    NOT_FOUND(404, "指定的资源不存在，或者URL错误"),

    //5:服务器端的底代码问题

    /**系统异常**/
    ERROR(500, "系统异常"),
    INTERNAL_SERVER_ERROR(501, "服务器发生异常"),
    INSERT_FAILED(502, "插入数据失败"),
    UPDATE_FAILED(503, "更新数据失败"),;




    /**响应码**/
    private final Integer code;

    /** 结果 **/
    private  final String  msg;

    ResponseEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    ResponseEnum() {
        this.code = 200;
        this.msg = "操作成功";
    }



    public static ResponseEnum getResultCode(Integer code){
        for (ResponseEnum value : ResponseEnum.values()) {
            if (code.equals(value.getCode())){
                return value;
            }
        }
        return ResponseEnum.ERROR;
    }

    /**
     * 简单测试一下
     * @param args
     */
    public static void main(String[] args) {
        ResponseEnum resultCode = ResponseEnum.getResultCode(100);
        System.out.println(resultCode);
    }

}
