package com.neusoft.yth.nepBackend.nepv.service.impl;

import com.neusoft.yth.nepBackend.nepv.dto.*;
import com.neusoft.yth.nepBackend.nepv.mapper.StatisticsMapper;
import com.neusoft.yth.nepBackend.nepv.service.IStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticsServiceImpl implements IStatisticsService {

    @Autowired
    private StatisticsMapper statisticsMapper;

    @Override
    public List<ProvinceStatsDTO> getProvinceSO2Stats() {
        return statisticsMapper.getProvinceSO2Stats();
    }

    @Override
    public List<ProvinceStatsDTO> getProvincePM25Stats() {
        return statisticsMapper.getProvincePM25Stats();
    }

    @Override
    public List<ProvinceStatsDTO> getProvinceCOStats() {
        return statisticsMapper.getProvinceCOStats();
    }

    @Override
    public List<ProvinceStatsDTO> getProvinceAQIStats() {
        return statisticsMapper.getProvinceAQIStats();
    }

    @Override
    public List<AQILevelStatsDTO> getAQILevelDistribution() {
        return statisticsMapper.getAQILevelDistribution();
    }

    @Override
    public List<AQITrendDTO> getAQITrend() {
        return statisticsMapper.getAQITrend();
    }

    @Override
    public RealtimeStatsDTO getRealtimeStats() {
        return statisticsMapper.getRealtimeStats();
    }

    @Override
    public List<GridCoverageDTO> getGridCoverage() {
        return statisticsMapper.getGridCoverage();
    }
}
