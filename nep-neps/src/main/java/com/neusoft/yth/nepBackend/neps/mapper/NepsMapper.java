package com.neusoft.yth.nepBackend.neps.mapper;

import com.neusoft.yth.nepBackend.entity.Member;
import com.neusoft.yth.nepBackend.entity.PublicSupervisorFeedback;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName NepsMapper
 * @Description NEPS数据访问接口
 * @Author YTH
 * @Date 2024-05-29
 */
@Mapper
public interface NepsMapper {
    /**
     * 检查手机号是否已注册
     */
    int countByPhoneNumber(@Param("phoneNumber") String phoneNumber);

    /**
     * 根据手机号查询用户ID
     */
    Integer getMemberIdByPhone(@Param("phoneNumber") String phoneNumber);

    /**
     * 登录验证
     */
    Integer loginVerify(@Param("phoneNumber") String phoneNumber, @Param("password") String password);

    /**
     * 插入新用户
     */
    int insertMember(Member member);

    /**
     * 插入反馈记录
     */
    int insertFeedback(PublicSupervisorFeedback feedback);

    /**
     * 查询用户历史反馈记录
     */
    List<PublicSupervisorFeedback> selectHistoryByPhone(@Param("phoneNumber") String phoneNumber);
}
