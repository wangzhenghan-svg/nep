package com.neusoft.yth.nepBackend.aqi.dao.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neusoft.yth.nepBackend.aqi.dao.IAqiDao;
import com.neusoft.yth.nepBackend.aqi.mapper.AqiMapper;
import com.neusoft.yth.nepBackend.entity.Aqi;
import org.springframework.stereotype.Service;

/**
 * @ClassName AqiDaoImpl
 * @Description
 * @Author YTH
 * @Date 2024-05-29 12:56
 */
@Service
public class AqiDaoImpl extends ServiceImpl<AqiMapper, Aqi> implements IAqiDao {
}
