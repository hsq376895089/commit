package com.hsq.service;



import com.hsq.entity.SysRole;

import java.util.Map;

public interface SysRoleService {
    /**
     * 查询所有 分页 模糊查询
     * @param sysRole
     * @param page
     * @param limit
     * @return
     */
    public Map<String,Object> roleAll(SysRole sysRole, Integer page, Integer limit);

    /**
     * 根据id删除
     * @param id
     * @return
     */
    public void deleteRoleById(Long id);

    /**
     * 根据id修改
     * @param sysRole
     * @return
     */
    public void updateRoleById(SysRole sysRole);

    /**
     * 添加
     * @param sysRole
     * @return
     */
    public void addRole(SysRole sysRole);

    /**
     * 查询数据库已拥有角色名
     * @param rname
     * @return
     */
    public boolean selectByName(String rname);
}


