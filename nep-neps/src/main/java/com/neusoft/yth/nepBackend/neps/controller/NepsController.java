package com.neusoft.yth.nepBackend.neps.controller;

import com.neusoft.yth.nepBackend.utils.Result;
import com.neusoft.yth.nepBackend.neps.dto.FeedbackDTO;
import com.neusoft.yth.nepBackend.neps.dto.LoginDTO;
import com.neusoft.yth.nepBackend.neps.dto.RegisterDTO;
import com.neusoft.yth.nepBackend.neps.service.INepsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName NepsController
 * @Description NEPS公众监督员端控制器
 * @Author YTH
 * @Date 2024-05-29
 */
@RestController
@RequestMapping("/neps")
public class NepsController {

    @Autowired
    private INepsService nepsService;

    /**
     * 用户注册
     */
    @PostMapping("/register")
    public Result register(@RequestBody RegisterDTO registerDTO) {
        return nepsService.register(registerDTO);
    }

    /**
     * 验证手机号是否已注册
     */
    @GetMapping("/checkPhone")
    public Result checkPhone(@RequestParam String phoneNumber) {
        return nepsService.checkPhoneExists(phoneNumber);
    }

    /**
     * 用户登录
     */
    @PostMapping("/login")
    public Result login(@RequestBody LoginDTO loginDTO) {
        return nepsService.login(loginDTO);
    }

    /**
     * 提交空气质量监督信息
     */
    @PostMapping("/submitFeedback")
    public Result submitFeedback(@RequestBody FeedbackDTO feedbackDTO) {
        return nepsService.submitFeedback(feedbackDTO);
    }

    /**
     * 获取用户历史反馈记录
     */
    @GetMapping("/historyFeedback")
    public Result getHistoryFeedback(@RequestParam String phoneNumber) {
        return nepsService.getHistoryFeedback(phoneNumber);
    }
}
