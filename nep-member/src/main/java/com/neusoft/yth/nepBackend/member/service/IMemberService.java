package com.neusoft.yth.nepBackend.member.service;

import com.neusoft.yth.nepBackend.entity.Member;
import com.neusoft.yth.nepBackend.utils.Result;

import java.util.List;

/**
 * @author YTH
 */
public interface IMemberService {
    /**
     * 查询所有用户
     * @return List<Member>
     */
    Result<List<Member>> selectAll();


    /**
     * 公众监督员登录
     * @param logInID
     * @param logInPwd
     * @return
     */
    Result<Member> login(String logInID, String logInPwd);


    /**
     * 公众监督员注册
     * @param member
     * @return
     */
    Result<Member> register(Member member);


    /**
     * 公众监督员登录验证码
     * @param tel
     * @return
     */
    Result getCode(String tel);

    /**
     * 公众监督员验证码登录
     * @param member
     * @return
     */
    Result loginCode(Member member);
}
