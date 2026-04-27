
package com.neusoft.yth.nepBackend.nepv.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProvinceStatsDTO {
    private String provinceCode;
    private String provinceName;
    private Integer so2ExceedCount;
    private Integer pm25ExceedCount;
    private Integer coExceedCount;
    private Integer aqiExceedCount;
}
