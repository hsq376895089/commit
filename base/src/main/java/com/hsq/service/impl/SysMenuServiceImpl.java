package com.hsq.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hsq.entity.SysMenu;
import com.hsq.entity.SysMenuExample;
import com.hsq.mapper.SysMenuMapper;
import com.hsq.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysMenuServiceImpl implements SysMenuService {

    @Autowired
    private SysMenuMapper sysMenuMapper;

    /**
     * 分页  模糊查询  查询所有
     * @param sysMenu
     * @param page
     * @param limit
     * @return
     */
    @Override
    public Map<String, Object> menuAll(SysMenu sysMenu, Integer page, Integer limit) {
        Map<String, Object> map = new HashMap<>();
        Page p = PageHelper.startPage(page,limit,true);
        SysMenuExample example = new SysMenuExample();
        if (sysMenu!=null && sysMenu.getName()!=null&&sysMenu.getName()!=""){
            example.createCriteria().andNameLike("%"+sysMenu.getName()+"%");
        }
        example.createCriteria().andDelFlagEqualTo(new Byte("0"));
        List list= sysMenuMapper.selectByExample(example);
        map.put("row",list);
        map.put("total",p.getTotal());
        return map;
    }

    /**
     * 根据id软删除
     * @param id
     */
    @Override
    public boolean deleteMenuById(Long id) {
        SysMenuExample example = new SysMenuExample();
        example.createCriteria().andParentIdEqualTo(id);
        List<SysMenu> list = sysMenuMapper.selectByExample(example);
        if (list!=null&&list.size()>=1){

            System.out.println("删除失败");
            return false;
        }else {
            sysMenuMapper.deleteMenuById(id);
            return true;
        }
    }

    /**
     * 添加
     * @param sysMenu
     */
    @Override
    public void addMenu(SysMenu sysMenu) {
        sysMenuMapper.insertSelective(sysMenu);
    }


    /**
     * 根据id修改
     * @param sysMenu
     */
    @Override
    public void updateMenuById(SysMenu sysMenu) {
        sysMenuMapper.updateByPrimaryKeySelective(sysMenu);
    }

    /**
     * 查询所有已拥有菜单名
     * @param mname
     * @return
     */
    @Override
    public boolean selectByMenuname(String mname) {
        SysMenuExample example = new SysMenuExample();
        example.createCriteria().andNameEqualTo(mname);
        List<SysMenu> list = sysMenuMapper.selectByExample(example);
        if (list!=null && list.size()==1){
            return false;
        }
        return true;
    }
}
