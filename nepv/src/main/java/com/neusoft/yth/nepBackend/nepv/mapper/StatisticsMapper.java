package com.neusoft.yth.nepBackend.nepv.mapper;

import com.neusoft.yth.nepBackend.nepv.dto.*;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface StatisticsMapper {

    List<ProvinceStatsDTO> getProvinceSO2Stats();

    List<ProvinceStatsDTO> getProvincePM25Stats();

    List<ProvinceStatsDTO> getProvinceCOStats();

    List<ProvinceStatsDTO> getProvinceAQIStats();

    List<AQILevelStatsDTO> getAQILevelDistribution();

    List<AQITrendDTO> getAQITrend();

    RealtimeStatsDTO getRealtimeStats();

    List<GridCoverageDTO> getGridCoverage();
}
