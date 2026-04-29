package com.neusoft.yth.nepBackend.neps.service.impl;

import com.neusoft.yth.nepBackend.entity.Member;
import com.neusoft.yth.nepBackend.entity.PublicSupervisorFeedback;
import com.neusoft.yth.nepBackend.utils.Result;
import com.neusoft.yth.nepBackend.enums.ResponseEnum;
import com.neusoft.yth.nepBackend.neps.dao.INepsDao;
import com.neusoft.yth.nepBackend.neps.dto.FeedbackDTO;
import com.neusoft.yth.nepBackend.neps.dto.LoginDTO;
import com.neusoft.yth.nepBackend.neps.dto.RegisterDTO;
import com.neusoft.yth.nepBackend.neps.service.INepsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 * @ClassName NepsServiceImpl
 * @Description NEPS业务逻辑实现
 * @Author YTH
 * @Date 2024-05-29
 */
@Service
public class NepsServiceImpl implements INepsService {

    @Autowired
    private INepsDao nepsDao;

    @Override
    public Result register(RegisterDTO registerDTO) {
        if (nepsDao.isPhoneExists(registerDTO.getPhoneNumber())) {
            return Result.fail(ResponseEnum.PHONE_ALREADY_EXISTS);
        }

        Member member = new Member();
        member.setMemNumber(registerDTO.getPhoneNumber());
        member.setMemPwd(registerDTO.getPassword());
        member.setMemName(registerDTO.getRealName());
        member.setMemSex(registerDTO.getGender());
        member.setRoleId("3");
        member.setMemState("1");

        return Result.success("注册成功");
    }

    @Override
    public Result checkPhoneExists(String phoneNumber) {
        boolean exists = nepsDao.isPhoneExists(phoneNumber);
        if (exists) {
            return Result.fail(ResponseEnum.PHONE_ALREADY_EXISTS);
        }
        return Result.success("手机号可用");
    }

    @Override
    public Result login(LoginDTO loginDTO) {
        if (nepsDao.loginVerify(loginDTO.getPhoneNumber(), loginDTO.getPassword())) {
            return Result.success("登录成功");
        }
        return Result.fail(ResponseEnum.LOGIN_FAILED);
    }

    @Override
    public Result submitFeedback(FeedbackDTO feedbackDTO) {
        Integer memberId = nepsDao.getMemberIdByPhone(feedbackDTO.getPhoneNumber());
        if (memberId == null) {
            return Result.fail(ResponseEnum.USER_NOT_FOUND);
        }

        PublicSupervisorFeedback feedback = new PublicSupervisorFeedback();
        feedback.setMemberId(memberId);
        feedback.setPhoneNumber(feedbackDTO.getPhoneNumber());
        feedback.setProvinceId(feedbackDTO.getProvinceId());
        feedback.setCityId(feedbackDTO.getCityId());
        feedback.setSpecificAddress(feedbackDTO.getSpecificAddress());
        feedback.setAqiLevel(feedbackDTO.getAqiLevel());
        feedback.setAirQualityDescription(feedbackDTO.getAirQualityDescription());
        feedback.setFeedbackTime(new Timestamp(System.currentTimeMillis()));
        feedback.setRemarks(feedbackDTO.getRemarks());

        if (nepsDao.insertFeedback(feedback)) {
            return Result.success("提交成功");
        }
        return Result.fail(ResponseEnum.SUBMIT_FAILED);
    }

    @Override
    public Result getHistoryFeedback(String phoneNumber) {
        List<PublicSupervisorFeedback> feedbackList = nepsDao.getHistoryFeedback(phoneNumber);
        return Result.success(feedbackList);
    }
}
