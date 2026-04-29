package com.neusoft.yth.nepBackend.neps.dao.impl;

import com.neusoft.yth.nepBackend.entity.PublicSupervisorFeedback;
import com.neusoft.yth.nepBackend.neps.dao.INepsDao;
import com.neusoft.yth.nepBackend.neps.mapper.NepsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName NepsDaoImpl
 * @Description NEPS数据访问层实现
 * @Author YTH
 * @Date 2024-05-29
 */
@Repository
public class NepsDaoImpl implements INepsDao {

    @Autowired
    private NepsMapper nepsMapper;

    @Override
    public boolean isPhoneExists(String phoneNumber) {
        return nepsMapper.countByPhoneNumber(phoneNumber) > 0;
    }

    @Override
    public Integer getMemberIdByPhone(String phoneNumber) {
        return nepsMapper.getMemberIdByPhone(phoneNumber);
    }

    @Override
    public boolean loginVerify(String phoneNumber, String password) {
        return nepsMapper.loginVerify(phoneNumber, password) > 0;
    }

    @Override
    public boolean insertFeedback(PublicSupervisorFeedback feedback) {
        return nepsMapper.insertFeedback(feedback) > 0;
    }

    @Override
    public List<PublicSupervisorFeedback> getHistoryFeedback(String phoneNumber) {
        return nepsMapper.selectHistoryByPhone(phoneNumber);
    }
}
