package com.hsq.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class SysUser implements Serializable {

    private List<SysRole> roles = new ArrayList<>();

    private Long id;

    private String username;

    private String password;

    private String salt;

    private String email;

    private String mobile;

    private Byte status;

    private Long deptId;

    private String createBy;

    private Date createTime;

    private String lastUpdateBy;

    private Date lastUpdateTime;

    private Byte delFlag;

    private String introduction;

    private String avatar;

    private static final long serialVersionUID = 1L;

}
