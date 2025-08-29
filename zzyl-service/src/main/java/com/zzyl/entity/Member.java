package com.zzyl.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * 会员信息实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Member {
    /**
     * 主键ID
     */
    private Long id;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 名称
     */
    private String name;

    /**
     * 头像URL
     */
    private String avatar;

    /**
     * 微信OpenID
     */
    private String openId;

    /**
     * 性别（0:男，1:女）
     */
    private Integer gender;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 创建人ID
     */
    private Long createBy;

    /**
     * 更新人ID
     */
    private Long updateBy;

    /**
     * 备注
     */
    private String remark;
}