package com.hsq.service;


import com.hsq.entity.SysMenu;

import java.util.Map;

public interface SysMenuService {
    /**
     * 查询所有 分页 模糊查询
     * @param sysMenu
     * @param page
     * @param limit
     * @return
     */
    public Map<String,Object> menuAll(SysMenu sysMenu, Integer page, Integer limit);

    /**
     * 根据id删除
     * @param id
     * @return
     */
    public boolean deleteMenuById(Long id);

    /**
     * 添加
     * @param sysMenu
     * @return
     */
    public void addMenu(SysMenu sysMenu);

    /**
     * 根据id修改
     * @param sysMenu
     * @return
     */
    public void updateMenuById(SysMenu sysMenu);


    /**
     * 查询所有数据库已拥有部门名字
     * @param name
     * @return
     */
    public boolean selectByMenuname(String mname);
}
