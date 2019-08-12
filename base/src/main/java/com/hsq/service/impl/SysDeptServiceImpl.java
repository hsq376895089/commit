package com.hsq.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hsq.entity.SysDept;
import com.hsq.entity.SysDeptExample;
import com.hsq.mapper.SysDeptMapper;
import com.hsq.service.SysDeptService;
import com.hsq.utils.SysDeptGroupUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysDeptServiceImpl implements SysDeptService {
    @Autowired
    private SysDeptMapper sysDeptMapper;

    @Override
    public List<SysDept> groupDept() {
        // 查询出所有的部门信息
        SysDeptExample example = new SysDeptExample();
        SysDeptExample.Criteria criteria = example.createCriteria();
        // 查询所有状态为0的
        criteria.andDelFlagEqualTo(new Byte("0"));
        List<SysDept> list = sysDeptMapper.selectByExample(example);
        // 使用部门分组工具类来对所有部门进行分组
        SysDeptGroupUtil util = new SysDeptGroupUtil();
        list = util.getFirstDept(list);
        return list;
    }

    /**
     * 分页
     * 查询所有
     * @return
     */
    @Override
    public Map<String,Object> deptAll(SysDept sysDept, Integer page, Integer limit) {
        Map<String, Object> map = new HashMap<>();
        Page p = PageHelper.startPage(page,limit,true);
        SysDeptExample example = new SysDeptExample();
        if (sysDept!=null && sysDept.getName()!=null&&sysDept.getName()!=""){
            example.createCriteria().andNameLike("%"+sysDept.getName()+"%");
        }
        example.createCriteria().andDelFlagEqualTo(new Byte("0"));
        List list= sysDeptMapper.selectByExample(example);
        map.put("row",list);
        map.put("total",p.getTotal());
        return map;
//        List<SysDept> list = sysDeptMapper.selectByExample(null);
//        return list;
    }

    /**
     * 根据id软删除
     * @param id
     * @return
     */
    @Override
    public boolean deleteDeptById(Long id) {
        SysDeptExample example = new SysDeptExample();
        example.createCriteria().andParentIdEqualTo(id);
        List<SysDept> list = sysDeptMapper.selectByExample(example);
        if (list!=null&&list.size()>=1){

            System.out.println("删除失败");
            return false;
        }else {
            sysDeptMapper.deleteDeptById(id);
            return true;
        }

    }

    /**
     * 根据id修改
     * @param sysDept
     * @return
     */
    @Override
    public void updateDeptById(SysDept sysDept) {
        sysDeptMapper.updateByPrimaryKeySelective(sysDept);
    }

    /**
     * 添加
     * @param sysDept
     * @return
     */
    @Override
    public void addDept(SysDept sysDept) {
        sysDeptMapper.insertSelective(sysDept);
    }

    /**
     * 查询所有已拥有部门名
     * @param dname
     * @return
     */
    @Override
    public boolean selectByDeptname(String dname) {
        SysDeptExample example = new SysDeptExample();
        example.createCriteria().andNameEqualTo(dname);
        List<SysDept> list = sysDeptMapper.selectByExample(example);
        if (list!=null && list.size()==1){
            return false;
        }
        return true;
    }

    /**
     * 查询所有ID
     * @param parentId
     * @return
     */
    @Override
    public boolean selectById(Long id) {
        SysDeptExample example = new SysDeptExample();
        example.createCriteria().andIdEqualTo(id);
        List<SysDept> list = sysDeptMapper.selectByExample(example);
        if (list!=null && list.size()==1){
            return false;
        }
        return true;
    }

    @Override
    public SysDept findById(Long id) {
        return sysDeptMapper.selectByPrimaryKey(id);
    }


}
