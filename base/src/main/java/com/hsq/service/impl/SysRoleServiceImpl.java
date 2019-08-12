package com.hsq.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hsq.entity.SysRole;
import com.hsq.entity.SysRoleExample;
import com.hsq.mapper.SysRoleMapper;
import com.hsq.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysRoleServiceImpl implements SysRoleService {
    @Autowired
    private SysRoleMapper sysRoleMapper;

    /**
     * 分页  模糊查询  查询所有
     * @param sysRole
     * @param page
     * @param limit
     * @return
     */
    @Override
    public Map<String, Object> roleAll(SysRole sysRole, Integer page, Integer limit) {
        Map<String, Object> map = new HashMap<>();
        Page p = PageHelper.startPage(page,limit,true);
        SysRoleExample example = new SysRoleExample();
        if (sysRole!=null && sysRole.getName()!=null&&sysRole.getName()!=""){
            example.createCriteria().andNameLike("%"+sysRole.getName()+"%");
        }
        example.createCriteria().andDelFlagEqualTo(new Byte("0"));
        List list= sysRoleMapper.selectByExample(example);
        map.put("row",list);
        map.put("total",p.getTotal());
        return map;
    }

    /**
     * 根据id软删除
     * @param id
     */
    @Override
    public void deleteRoleById(Long id) {
        sysRoleMapper.deleteRoleById(id);
    }

    /**
     * 根据id修改
     * @param sysRole
     */
    @Override
    public void updateRoleById(SysRole sysRole) {
        sysRoleMapper.updateByPrimaryKeySelective(sysRole);
    }

    /**
     * 添加
     * @param sysRole
     */
    @Override
    public void addRole(SysRole sysRole) {
        sysRoleMapper.insertSelective(sysRole);
    }

    /**
     * 查询校验数据库已注册用户名
     * @param rname
     * @return
     */
    @Override
    public boolean selectByName(String rname) {
        SysRoleExample example = new SysRoleExample();
        example.createCriteria().andNameEqualTo(rname);
        List<SysRole> list = sysRoleMapper.selectByExample(example);
        if (list!=null && list.size()==1){
            return false;
        }
        return true;
    }
}
