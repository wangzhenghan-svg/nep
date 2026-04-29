package com.neusoft.yth.nepBackend.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PublicSupervisorFeedback {
    private int feedbackId;
    private int memberId;
    private String phoneNumber;
    private String provinceId;
    private String cityId;
    private String specificAddress;
    private int aqiLevel;
    private String airQualityDescription;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp feedbackTime;

    private String remarks;
}
