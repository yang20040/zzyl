package com.zzyl.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 护理项目VO
 */
@Data
@ApiModel("护理项目视图对象")
public class NursingProjectVO {
    @ApiModelProperty("编号")
    private Long id;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("排序号")
    private Integer orderNo;

    @ApiModelProperty("单位")
    private String unit;

    @ApiModelProperty("价格")
    private BigDecimal price;

    @ApiModelProperty("图片")
    private String image;

    @ApiModelProperty("护理要求")
    private String nursingRequirement;

    @ApiModelProperty("状态（0：禁用，1：启用）")
    private Integer status;

    @ApiModelProperty("状态描述")
    private String statusDesc;

    @ApiModelProperty("创建人")
    private String createBy;

    @ApiModelProperty("更新人")
    private String updateBy;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("创建人")
    private String creator;
}