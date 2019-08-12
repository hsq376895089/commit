package com.hsq.mapper;

import com.hsq.entity.BasZoneInfo;
import com.hsq.entity.BasZoneInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BasZoneInfoMapper {
    long countByExample(BasZoneInfoExample example);

    int deleteByExample(BasZoneInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BasZoneInfo record);

    int insertSelective(BasZoneInfo record);

    List<BasZoneInfo> selectByExample(BasZoneInfoExample example);

    BasZoneInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BasZoneInfo record, @Param("example") BasZoneInfoExample example);

    int updateByExample(@Param("record") BasZoneInfo record, @Param("example") BasZoneInfoExample example);

    int updateByPrimaryKeySelective(BasZoneInfo record);

    int updateByPrimaryKey(BasZoneInfo record);
}
