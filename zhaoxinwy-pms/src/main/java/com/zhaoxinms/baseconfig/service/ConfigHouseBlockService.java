package com.zhaoxinms.baseconfig.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhaoxinms.baseconfig.entity.ConfigHouseBlockEntity;
import com.zhaoxinms.baseconfig.model.confighouseblock.ConfigHouseBlockPagination;

import java.util.*;
/**
 *
 * config_house_block
 * 版本： V3.1.0
 * 版权： 
 * 作者： CYCBERFORM
 * 日期： 2021-09-15 15:28:22
 */
public interface ConfigHouseBlockService extends IService<ConfigHouseBlockEntity> {

    List<ConfigHouseBlockEntity> getList(ConfigHouseBlockPagination configHouseBlockPagination);

    List<ConfigHouseBlockEntity> getTypeList(ConfigHouseBlockPagination configHouseBlockPagination,String dataType);

    ConfigHouseBlockEntity getInfo(String id);

    void delete(ConfigHouseBlockEntity entity);

    void create(ConfigHouseBlockEntity entity);

    boolean update( String id, ConfigHouseBlockEntity entity);
    
}
