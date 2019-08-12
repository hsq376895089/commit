package com.hsq.service;

import com.hsq.entity.SysUser;

import java.util.List;
import java.util.Map;

public interface SysUserService {
    /**
     * 用户登录
     * @param sysUser
     * @return
     */
    public SysUser login(SysUser sysUser);

    /**
     * 根据用户名查询用户信息
     * @return
     */
    public SysUser findUserByUserName(String username);

    /**
     * 根据用户名获得角色的集合
     * @param username
     * @return
     */
    public List<String> findUserRoles(String username);

    /**
     * 查询所有用户
     * @param sysUser
     * @return
     */
    public Map<String,Object> userAll(SysUser sysUser, Integer page, Integer limit);

    /**
     * 根据id删除
     * @param id
     * @return
     */
    public void deleteUserById(Long id);

    /**
     * 根据id修改
     * @param sysUser
     * @return
     */
    public void updateUserById(SysUser sysUser);

    /**
     * 添加
     * @param sysUser
     * @return
     */
    public void addUser(SysUser sysUser);

    /**
     * 查询所有数据库已拥有用户名
     * @param name
     * @return
     */
    public boolean selectByUsername(String name);

    public SysUser findUserByNameAndRoleAndMenu(String username);

    public SysUser findById(Long id);
}
