package com.hsq.utils;


import com.hsq.entity.SysDept;

import java.util.ArrayList;
import java.util.List;

/**
 * 递归对部门进行分组
 */
public class SysDeptGroupUtil {

    /**
     * 获得一级部门
     * @param list
     * @return
     */
    public List<SysDept> getFirstDept(List<SysDept> list){
        List<SysDept> firstDept = new ArrayList<>();
        //查询所有的parentId为0的部门
        for (SysDept dept:list){
            if(dept.getParentId().longValue()==0){
                dept.setItems(getChildrenDept(dept.getId(),list));
                firstDept.add(dept);
            }
        }
        return firstDept;
    }

    /**
     * 递归调用获得所有部门的子集
     * @param parentId
     * @param list
     * @return
     */
    public List<SysDept> getChildrenDept(Long parentId,List<SysDept> list){
        List<SysDept> childrenDept = new ArrayList<>();
        for (SysDept dept:list){
            // 一级部门不需要加进去
            if(dept.getParentId().longValue()==0){
                continue;
            }
            if(dept.getParentId().longValue()==parentId){
               dept.setItems(getChildrenDept(dept.getId(),list));
                childrenDept.add(dept);
            }
        }
        return childrenDept;
    }
}
