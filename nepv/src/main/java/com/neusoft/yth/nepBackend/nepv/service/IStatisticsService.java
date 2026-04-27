package com.neusoft.yth.nepBackend.nepv.service;

import com.neusoft.yth.nepBackend.nepv.dto.*;
import java.util.List;

public interface IStatisticsService {
    List<ProvinceStatsDTO> getProvinceSO2Stats();
    List<ProvinceStatsDTO> getProvincePM25Stats();
    List<ProvinceStatsDTO> getProvinceCOStats();
    List<ProvinceStatsDTO> getProvinceAQIStats();
    List<AQILevelStatsDTO> getAQILevelDistribution();
    List<AQITrendDTO> getAQITrend();
    RealtimeStatsDTO getRealtimeStats();
    List<GridCoverageDTO> getGridCoverage();
}
