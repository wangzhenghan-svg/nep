package com.neusoft.yth.nepBackend.neps.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;

/**
 * @ClassName HistoryFeedbackVO
 * @Description 历史反馈信息视图对象
 * @Author YTH
 * @Date 2024-05-29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class HistoryFeedbackVO {
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp feedbackTime;

    private String province;
    private String city;
    private int aqiLevel;
    private String airQualityDescription;
}
