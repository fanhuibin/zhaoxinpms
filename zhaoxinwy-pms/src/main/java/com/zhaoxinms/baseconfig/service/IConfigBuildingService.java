package com.zhaoxinms.baseconfig.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhaoxinms.baseconfig.entity.ConfigBuilding;
import com.zhaoxinms.baseconfig.entity.pagination.ConfigBuildingPagination;

/**
 * 楼栋管理Service接口
 * 
 * @author fanhuibin
 * @date 2022-06-22
 */
public interface IConfigBuildingService extends IService<ConfigBuilding>
{

    List<ConfigBuilding> getList(ConfigBuildingPagination pagination);

    ConfigBuilding getInfo(String id);

    void delete(ConfigBuilding entity);

    void create(ConfigBuilding entity);

    boolean update(String id, ConfigBuilding entity);
}
