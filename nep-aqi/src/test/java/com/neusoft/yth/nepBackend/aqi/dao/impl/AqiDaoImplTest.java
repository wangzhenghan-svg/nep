package com.neusoft.yth.nepBackend.aqi.dao.impl;

import com.neusoft.yth.nepBackend.aqi.mapper.AqiMapper;
import com.neusoft.yth.nepBackend.entity.Aqi;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @ClassName AqiDaoImplTest
 * @Description
 * @Author YTH
 * @Date 2024-05-29 13:00
 */
@SpringBootTest
class AqiDaoImplTest {

    @Autowired
    private AqiMapper aqiMapper;

    @Test
    public void testSelectAll() {
        List<Aqi> aqiList = aqiMapper.selectList(null);
        aqiList.forEach(System.out::println);
    }


}