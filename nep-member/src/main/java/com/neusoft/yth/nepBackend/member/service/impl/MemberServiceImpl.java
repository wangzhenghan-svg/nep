package com.neusoft.yth.nepBackend.member.service.impl;

import com.neusoft.yth.nepBackend.entity.Member;
import com.neusoft.yth.nepBackend.enums.ResponseEnum;
import com.neusoft.yth.nepBackend.member.mapper.MemberMapper;
import com.neusoft.yth.nepBackend.member.service.IMemberService;
import com.neusoft.yth.nepBackend.utils.Result;
import com.neusoft.yth.nepBackend.utils.VerifyCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

/**
 * @author YTH
 * @create 2024-05-26 16:01
 */
@Service
public class MemberServiceImpl implements IMemberService {

    @Autowired
    private MemberMapper memberMapper;
    /**
     * 查询所有用户
     *
     * @return List<Member>
     */
    @Override
    public Result<List<Member>> selectAll() {
        List<Member> mls=memberMapper.selectAll();
        return  Result.success(mls);
    }

    /**
     * 公众监督员登录
     *
     * @param logInID
     * @param logInPwd
     * @return
     */
    @Override
    public Result<Member> login(String logInID, String logInPwd) {
        Member member = memberMapper.selectByNumber(logInID);
        if (member!=null){
            if (member.getMemPwd().equals(logInPwd)){
                return Result.success(member);
            }else{
                return Result.fail(ResponseEnum.LOGIN_PASSWORD_ERROR);
            }
        }else{
            return Result.fail(ResponseEnum.LOGIN_NAME_NOT_EXIST);
        }
    }


    /**
     * 公众监督员注册
     *  注册成功，返回新成员唯一ID
     *  注册失败，返回-1
     * @param member
     * @return
     */
    @Override
    public Result register(Member member) {
        //检查number是否已经存在
        int count = memberMapper.selectCountByNumber(member.getMemNumber());
        if (count>0){
            // 账号已经存在
            return Result.fail(ResponseEnum.REGISTER_NAME_IN_USE);
        }

        try {
            count =memberMapper.insert(member);
            if (count==1){
                return Result.success(member.getMemId());
            }else{
//              数据库插入失败
                return Result.fail(ResponseEnum.INSERT_FAILED);
            }
        }catch (Exception e){
            return Result.fail(500,"注册失败——数据库插入异常",e.getMessage());
        }
    }

    /**
     * 公众监督员登录验证码
     * @param tel
     * @return
     */
    @Override
    public Result getCode(String tel) {
        Member member = memberMapper.selectByNumber(tel);
        //检查number是否已经存在
        if(member==null){
            return Result.fail(ResponseEnum.LOGIN_NAME_NOT_EXIST);
        }
        //生成验证码
        String code = VerifyCode.getCode();
        // 获取当前时间
        Calendar calendar = Calendar.getInstance();
        Timestamp date = new Timestamp(calendar.getTime().getTime());
        member.setMemNumber(tel);
        member.setVerifyCode(code);
        member.setCodeTime(date);
        System.out.println(member);
        try {
            int count = memberMapper.updateMember(member);
//        发送短信 ，使用kafka发短信
            if (count==1){
                return Result.success(member);
            }else {
                return Result.fail(ResponseEnum.LOGIN_VERIFY_CODE_ERROR);
            }
        }catch (Exception e){
            return Result.fail(500,"验证码发送失败——数据库插入异常",e.getMessage());
        }
    }

    /**
     * 公众监督员验证码登录
     *
     * @param member
     * @return
     */
    @Override
    public Result loginCode(Member member) {
        //获取时间
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE,-5);
        member.setCodeTime(new Timestamp(calendar.getTime().getTime()));

        int count = memberMapper.selectCountByTime(member);
        if (count>0){
            return Result.success(member);
        }else {
            return Result.fail(ResponseEnum.LOGIN_CODE_ERROR_OR_TIMEOUT);
        }

    }

}
