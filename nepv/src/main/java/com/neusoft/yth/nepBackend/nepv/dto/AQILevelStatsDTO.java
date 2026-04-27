package com.neusoft.yth.nepBackend.nepv.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AQILevelStatsDTO {
    private Integer level;
    private String levelName;
    private Integer count;
    private Double percentage;
}
