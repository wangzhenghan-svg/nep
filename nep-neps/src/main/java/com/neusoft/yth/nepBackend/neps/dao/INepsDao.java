package com.neusoft.yth.nepBackend.neps.dao;

import com.neusoft.yth.nepBackend.entity.PublicSupervisorFeedback;

import java.util.List;

/**
 * @ClassName INepsDao
 * @Description NEPS数据访问层接口
 * @Author YTH
 * @Date 2024-05-29
 */
public interface INepsDao {
    /**
     * 检查手机号是否已注册
     */
    boolean isPhoneExists(String phoneNumber);

    /**
     * 根据手机号获取用户ID
     */
    Integer getMemberIdByPhone(String phoneNumber);

    /**
     * 登录验证
     */
    boolean loginVerify(String phoneNumber, String password);

    /**
     * 插入反馈记录
     */
    boolean insertFeedback(PublicSupervisorFeedback feedback);

    /**
     * 查询用户历史反馈记录
     */
    List<PublicSupervisorFeedback> getHistoryFeedback(String phoneNumber);
}
