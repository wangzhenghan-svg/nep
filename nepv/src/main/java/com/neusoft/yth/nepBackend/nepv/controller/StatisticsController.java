package com.neusoft.yth.nepBackend.nepv.controller;

import com.neusoft.yth.nepBackend.nepv.dto.*;
import com.neusoft.yth.nepBackend.nepv.service.IStatisticsService;
import com.neusoft.yth.nepBackend.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/nepv/statistics")
@CrossOrigin
public class StatisticsController {

    @Autowired
    private IStatisticsService statisticsService;

    @GetMapping("/province/so2")
    public Result<List<ProvinceStatsDTO>> getProvinceSO2Stats() {
        return Result.success(statisticsService.getProvinceSO2Stats());
    }

    @GetMapping("/province/pm25")
    public Result<List<ProvinceStatsDTO>> getProvincePM25Stats() {
        return Result.success(statisticsService.getProvincePM25Stats());
    }

    @GetMapping("/province/co")
    public Result<List<ProvinceStatsDTO>> getProvinceCOStats() {
        return Result.success(statisticsService.getProvinceCOStats());
    }

    @GetMapping("/province/aqi")
    public Result<List<ProvinceStatsDTO>> getProvinceAQIStats() {
        return Result.success(statisticsService.getProvinceAQIStats());
    }

    @GetMapping("/aqi/level-distribution")
    public Result<List<AQILevelStatsDTO>> getAQILevelDistribution() {
        return Result.success(statisticsService.getAQILevelDistribution());
    }

    @GetMapping("/aqi/trend")
    public Result<List<AQITrendDTO>> getAQITrend() {
        return Result.success(statisticsService.getAQITrend());
    }

    @GetMapping("/realtime")
    public Result<RealtimeStatsDTO> getRealtimeStats() {
        return Result.success(statisticsService.getRealtimeStats());
    }

    @GetMapping("/grid-coverage")
    public Result<List<GridCoverageDTO>> getGridCoverage() {
        return Result.success(statisticsService.getGridCoverage());
    }

    @GetMapping("/dashboard")
    public Result<Map<String, Object>> getDashboardData() {
        Map<String, Object> data = new HashMap<>();
        data.put("provinceSO2", statisticsService.getProvinceSO2Stats());
        data.put("provincePM25", statisticsService.getProvincePM25Stats());
        data.put("provinceCO", statisticsService.getProvinceCOStats());
        data.put("provinceAQI", statisticsService.getProvinceAQIStats());
        data.put("aqiLevelDistribution", statisticsService.getAQILevelDistribution());
        data.put("aqiTrend", statisticsService.getAQITrend());
        data.put("realtimeStats", statisticsService.getRealtimeStats());
        data.put("gridCoverage", statisticsService.getGridCoverage());
        return Result.success(data);
    }
}
