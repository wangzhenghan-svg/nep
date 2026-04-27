package com.neusoft.yth.nepBackend.nepv.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AQITrendDTO {
    private String month;
    private Integer exceedCount;
}
