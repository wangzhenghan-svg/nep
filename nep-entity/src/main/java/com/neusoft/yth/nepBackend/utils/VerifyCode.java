package com.neusoft.yth.nepBackend.utils;

import java.util.Random;

/**
 * @ClassName VerifyCode
 * @Description
 * @Author YTH
 * @Date 2024-05-28 19:01
 */
public class VerifyCode {
    private static Random random = new Random();

    //验证码长度
    private static int size=6;

    /**
     * 获得六位随机的整数
     * @return
     */
    public static String getCode(){
        String code = "";
        for (int i = 0; i < size; i++) {
            code += random.nextInt(10);
        }
        return code;
    }
}
