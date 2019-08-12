package com.hsq.entity;

import lombok.Data;

import java.io.Serializable;
@Data
/**
 * 定区信息表
 */
public class BasZoneInfo implements Serializable {
    // 部门
    private SysDept dept;

    // 定区客户信息
    private BasZoneCustomInfo basInfo;

    private Integer id;

    private String zonecode;

    private String zonename;

    private Integer zonepeople;

    private String telphone;

    private Integer subordinateunit;

    private static final long serialVersionUID = 1L;
}
