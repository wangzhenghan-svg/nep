package com.neusoft.yth.nepBackend.nepv.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RealtimeStatsDTO {
    private Integer totalCount;
    private Integer passCount;
    private Integer exceedCount;
}
