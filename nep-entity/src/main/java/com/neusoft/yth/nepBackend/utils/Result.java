package com.neusoft.yth.nepBackend.utils;

import com.neusoft.yth.nepBackend.enums.ResponseEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 控制器返回通用类型
 * 泛型类 T作用：在软件运行时确定类型
 * @param <T>
 * @author YTH
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 56665257248936049L;
    /**
     * 响应码
     **/
    private Integer code;

    /**
     * 返回消息
     **/
    private String msg;

    /**
     * 返回数据
     **/
    private T result;

    /**
     * 操作成功ok方法
     * @param data
     * @return
     */
    public static <T> Result<T> success(T data) {
        Result<T> response = new Result<>();
        response.setCode(ResponseEnum.SUCCESS.getCode());
        response.setMsg(ResponseEnum.SUCCESS.getMsg());
        response.setResult(data);
        return response;
    }


    /**
     * 请求处理失败方法
     */
    public static <T> Result<T> fail(Integer errCode, String errMessage, T data) {
        Result<T> response = new Result<>();
        response.setCode(errCode);
        response.setMsg(errMessage);
        response.setResult(data);
        return response;
    }


    /**
     * 请求处理失败方法
     */
    public static <T> Result<T> fail(ResponseEnum errCode) {
        Result<T> response = new Result<>();
        response.setCode(errCode.getCode());
        response.setMsg(errCode.getMsg());
        response.setResult(null);
        return response;
    }

}
