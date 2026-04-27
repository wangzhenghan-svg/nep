package com.neusoft.yth.nepBackend.nepv.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GridCoverageDTO {
    private String regionType;
    private String regionName;
    private Double coverageRate;
}
