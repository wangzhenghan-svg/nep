package com.neusoft.yth.nepBackend.neps.service;

import com.neusoft.yth.nepBackend.utils.Result;
import com.neusoft.yth.nepBackend.neps.dto.FeedbackDTO;
import com.neusoft.yth.nepBackend.neps.dto.LoginDTO;
import com.neusoft.yth.nepBackend.neps.dto.RegisterDTO;

/**
 * @ClassName INepsService
 * @Description NEPS业务逻辑接口
 * @Author YTH
 * @Date 2024-05-29
 */
public interface INepsService {
    /**
     * 用户注册
     */
    Result register(RegisterDTO registerDTO);

    /**
     * 检查手机号是否已注册
     */
    Result checkPhoneExists(String phoneNumber);

    /**
     * 用户登录
     */
    Result login(LoginDTO loginDTO);

    /**
     * 提交反馈信息
     */
    Result submitFeedback(FeedbackDTO feedbackDTO);

    /**
     * 获取历史反馈记录
     */
    Result getHistoryFeedback(String phoneNumber);
}
