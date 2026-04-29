package com.neusoft.yth.nepBackend.neps.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @ClassName FeedbackDTO
 * @Description 反馈信息数据传输对象
 * @Author YTH
 * @Date 2024-05-29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FeedbackDTO {
    private String phoneNumber;
    private String provinceId;
    private String cityId;
    private String specificAddress;
    private int aqiLevel;
    private String airQualityDescription;
    private String remarks;
}
