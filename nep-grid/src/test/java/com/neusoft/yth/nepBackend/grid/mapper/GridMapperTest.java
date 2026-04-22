package com.neusoft.yth.nepBackend.grid.mapper;

import com.neusoft.yth.nepBackend.grid.entity.Grid;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @ClassName GridMapperTest
 * @Description
 * @Author YTH
 * @Date 2024-05-29 14:39
 */
@SpringBootTest
class GridMapperTest {
    @Autowired
    private GridMapper gridMapper;

    @Test
    public void testSelectAll() {
        List<Grid> list = gridMapper.selectList(null);
        list.forEach(System.out::println);

    }

}