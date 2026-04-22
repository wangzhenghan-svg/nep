package com.neusoft.yth.nepBackend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @ClassName Aqi
 * @Description
 * @Author YTH
 * @Date 2024-05-29 12:52
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Aqi {

    /**
     * 空气质量指数级别（共六级）
     */
    private int aqiId;
    /**
     * 空气质量指数级别汉字描述
     */
    private String chineseExplain;
    /**
     * 空气质量指数级别描述
     */
    private String aqiExplain;
    /**
     * 空气质量指数级别标识颜色
     */
    private String color;
    /**
     * 对健康影响情况
     */
    private String healthImpact;
    /**
     * 建议采取措施
     */
    private String takeSteps;
    /**
     * 本级别二氧化硫浓度最小限值
     */
    private int so2Min;
    /**
     * 本级别二氧化硫浓度最大限值
     */
    private int so2Max;
    /**
     * 本级别一氧化碳浓度最小限值
     */
    private int coMin;
    /**
     * 本级别一氧化碳浓度最大限值
     */
    private int coMax;
    /**
     * 本级别悬浮颗粒物浓度最小限值
     */
    private int spmMin;
    /**
     * 本级别悬浮颗粒物浓度最大限值
     */
    private int spmMax;
    /**
     * 级别最低AQI
     */
    private int aqiMin;
    /**
     * 级别最高AQI
     */
    private int aqiMax;
    /**
     * 备注
     */
    private String remarks;
}
