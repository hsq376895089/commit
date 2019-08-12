package com.hsq.service;


import com.hsq.entity.SysDept;

import java.util.List;
import java.util.Map;

public interface SysDeptService {
    /**
     * 查询分组的部门信息
     * @return
     */
    public List<SysDept> groupDept();


    /**
     * 查询所有部门
     * @param sysDept
     * @return
     */
    public Map<String,Object> deptAll(SysDept sysDept, Integer page, Integer limit);

    /**
     * 根据id删除
     * @param id
     * @return
     */
    public boolean deleteDeptById(Long id);

    /**
     * 根据id修改
     * @param sysDept
     * @return
     */
    public void updateDeptById(SysDept sysDept);

    /**
     * 添加
     * @param sysDept
     * @return
     */
    public void addDept(SysDept sysDept);

    /**
     * 查询所有数据库已拥有部门名字
     * @param name
     * @return
     */
    public boolean selectByDeptname(String dname);

    /**
     * 查询所有ID
     * @param parentId
     * @return
     */
    public boolean selectById(Long id);

    public SysDept findById(Long id);
}
