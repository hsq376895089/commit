package com.hsq.service.impl;

import com.hsq.entity.BasZoneCustomInfo;
import com.hsq.mapper.BasZoneCustomInfoMapper;
import com.hsq.service.BasZoneCustomInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BasZoneCustomInfoServiceImpl implements BasZoneCustomInfoService {

    @Autowired
    private BasZoneCustomInfoMapper basZoneCustomInfoMapper;

    @Override
    public BasZoneCustomInfo findById(Integer id) {
        return basZoneCustomInfoMapper.selectByPrimaryKey(id);
    }
}
