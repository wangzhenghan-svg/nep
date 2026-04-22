package com.neusoft.yth.nepBackend.member.controller;

import com.neusoft.yth.nepBackend.entity.Member;
import com.neusoft.yth.nepBackend.member.service.IMemberService;
import com.neusoft.yth.nepBackend.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author YTH
 */
@RestController
@CrossOrigin("*")
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private IMemberService memberService;

    /**
     * 获取所有成员列表
     * @return
     */
    @RequestMapping("/select/all")
    public Result<List<Member>> selectAll() {
        return memberService.selectAll();
    }


    /**
     * 账号密码登录
     * @param logInID
     * @param logInPwd
     * @return
     */
    @PostMapping("/login")
    public Result<Member> login(String logInID,String logInPwd) {
        return memberService.login( logInID, logInPwd);
    }

    /**
     * 账号、验证码登录
     * @param member
     * @return
     */
    @PostMapping("/login/code")
    public Result loginCode(@RequestBody Member member) {
        return memberService.loginCode(member);
    }

    /**
     * 获取验证码
     * @param tel
     * @return
     */
    @GetMapping("/login/code/get/{tel}")
    public Result getCode(@PathVariable String tel) {
        return memberService.getCode(tel);
    }




    /**
     * 注册
     * @param member
     * @return
     */
    @PostMapping("/register")
    public Result<Member> register(@RequestBody Member member) {
        return memberService.register(member);
    }

}
