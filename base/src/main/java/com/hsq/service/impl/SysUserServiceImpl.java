package com.hsq.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hsq.entity.SysUser;
import com.hsq.entity.SysUserExample;
import com.hsq.mapper.SysUserMapper;
import com.hsq.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;
    @Override
    public SysUser login(SysUser sysUser) {
        SysUserExample example = new SysUserExample();
        SysUserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(sysUser.getUsername());
        criteria.andPasswordEqualTo(sysUser.getPassword());
        List<SysUser> list =  sysUserMapper.selectByExample(example);
        if(list!=null && list.size()==1){
            return list.get(0);
        }
        return  null;
    }

    @Override
    public SysUser findUserByUserName(String username) {
        SysUserExample example = new SysUserExample();
        SysUserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<SysUser> list =  sysUserMapper.selectByExample(example);
        if(list!=null && list.size()==1){
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<String> findUserRoles(String username) {
        List<String> roles = sysUserMapper.findUserRoles(username);
        return roles;
    }

    /**
     * 分页
     * 查询所有
     * @return
     */
    @Override
    public Map<String,Object> userAll(SysUser sysUser, Integer page, Integer limit) {
        Map<String, Object> map = new HashMap<>();
        Page p = PageHelper.startPage(page,limit,true);
        SysUserExample example = new SysUserExample();
        if (sysUser!=null && sysUser.getUsername()!=null&&sysUser.getUsername()!=""){
            example.createCriteria().andUsernameLike("%"+sysUser.getUsername()+"%");
        }
        example.createCriteria().andDelFlagEqualTo(new Byte("0"));
        List list= sysUserMapper.selectByExample(example);
        map.put("row",list);
        map.put("total",p.getTotal());
        return map;
//        List<SysUser> list = sysUserMapper.selectByExample(null);
//        return list;
    }

    /**
     * 根据id软删除
     * @param id
     * @return
     */
    @Override
    public void deleteUserById(Long id) {
        sysUserMapper.deleteUserById(id);
    }

    /**
     * 根据id修改
     * @param sysUser
     * @return
     */
    @Override
    public void updateUserById(SysUser sysUser) {
        sysUserMapper.updateByPrimaryKeySelective(sysUser);
    }

    /**
     * 添加
     * @param sysUser
     * @return
     */
    @Override
    public void addUser(SysUser sysUser) {
        sysUserMapper.insertSelective(sysUser);
    }

    /**
     * 查询所有已注册用户名
     * @param username
     * @return
     */
    @Override
    public boolean selectByUsername(String name) {
        SysUserExample example = new SysUserExample();
        example.createCriteria().andUsernameEqualTo(name);
        List<SysUser> list = sysUserMapper.selectByExample(example);
        if (list!=null && list.size()==1){
            return false;
        }
        return true;
    }


    /**
     * 根据用户名查找用户信息（角色，权限）
     * @param username
     * @return
     */
    @Override
    public SysUser findUserByNameAndRoleAndMenu(String username) {
        List<SysUser> list = sysUserMapper.findUserByNameAndRoleAndMenu(username);
        if(list!=null && list.size()==1){
            return list.get(0);
        }
        return null;
    }

    @Override
    public SysUser findById(Long id) {
       return sysUserMapper.selectByPrimaryKey(id);
    }
}
