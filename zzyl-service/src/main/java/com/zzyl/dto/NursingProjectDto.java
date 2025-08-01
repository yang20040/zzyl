package com.zzyl.dto;

import lombok.Data;
import java.math.BigDecimal;

/**
 * 新增护理项目DTO
 */
@Data
public class NursingProjectDto {
    /**
     * 名称
     */
    private String name;

    /**
     * 排序号
     */
    private Integer orderNo;

    /**
     * 单位
     */
    private String unit;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 图片URL
     */
    private String image;

    /**
     * 护理要求
     */
    private String nursingRequirement;

    /**
     * 状态（0：禁用，1：启用）
     */
    private Integer status = 1;  // 默认启用

    /**
     * 备注
     */
    private String remark;
}