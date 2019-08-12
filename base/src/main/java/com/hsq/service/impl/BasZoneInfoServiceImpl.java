package com.hsq.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hsq.entity.BasZoneCustomInfo;
import com.hsq.entity.BasZoneInfo;
import com.hsq.entity.BasZoneInfoExample;
import com.hsq.entity.SysDept;
import com.hsq.mapper.BasZoneInfoMapper;
import com.hsq.service.BasZoneCustomInfoService;
import com.hsq.service.BasZoneInfoService;
import com.hsq.service.SysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BasZoneInfoServiceImpl implements BasZoneInfoService {

    @Autowired
    private BasZoneInfoMapper basZoneInfoMapper;

    @Autowired
    private SysDeptService sysDeptService;

    @Autowired
    private BasZoneCustomInfoService basZoneCustomInfoService;

    /**
     * 分页  模糊查询  查询所有
     * @param basZoneInfo
     * @param page
     * @param limit
     * @return
     */
    @Override
    public Map basZoneInfoAll(BasZoneInfo basZoneInfo, Integer page, Integer limit) {
        Map<String, Object> map = new HashMap<>();
        Page p = PageHelper.startPage(page,limit,true);
        BasZoneInfoExample example = new BasZoneInfoExample();
        List<BasZoneInfo> list= basZoneInfoMapper.selectByExample(example);
        for(BasZoneInfo bd : list){
            SysDept sysDept= sysDeptService.findById(Long.valueOf(bd.getSubordinateunit()));
            bd.setDept(sysDept);
            BasZoneCustomInfo basZoneCustomInfo = basZoneCustomInfoService.findById(bd.getZonepeople());
            bd.setBasInfo(basZoneCustomInfo);
        }
        map.put("row",list);
        map.put("total",p.getTotal());
        return map;
    }

    /**
     * 根据id删除
     * @param id
     */
    @Override
    public void deleteById(Integer id) {
        basZoneInfoMapper.deleteByPrimaryKey(id);
    }

    /**
     * 根据id修改
     * @param basZoneInfo
     */
    @Override
    public void updateById(BasZoneInfo basZoneInfo) {
        basZoneInfoMapper.updateByPrimaryKeySelective(basZoneInfo);
    }

    /**
     * 添加
     * @param basZoneInfo
     */
    @Override
    public void add(BasZoneInfo basZoneInfo) {
        basZoneInfoMapper.insertSelective(basZoneInfo);
    }
}
