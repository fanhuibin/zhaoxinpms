package com.zhaoxinms.statistics.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhaoxinms.baseconfig.entity.ConfigHouseEntity;
import com.zhaoxinms.baseconfig.model.house.HouseContractListVO;
import com.zhaoxinms.baseconfig.model.house.HouseContractPagination;

public interface HouseStatisticsMapper extends BaseMapper<ConfigHouseEntity> {

    @Select("SELECT count(1) FROM config_house where state = #{state}")
    int getHouseCountByState(@Param("state") String state);
}
