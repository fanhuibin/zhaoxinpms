package com.zhaoxinms.baseconfig.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhaoxinms.baseconfig.entity.ConfigFeeItemEntity;
import com.zhaoxinms.baseconfig.model.configfeeitem.ConfigFeeItemPagination;

import java.util.*;
/**
 *
 * config_fee_item
 * 版本： V3.1.0
 * 版权： 
 * 作者： CYCBERFORM
 * 日期： 2021-09-15 07:10:17
 */
public interface ConfigFeeItemService extends IService<ConfigFeeItemEntity> {

    List<ConfigFeeItemEntity> getList(ConfigFeeItemPagination configFeeItemPagination);

    List<ConfigFeeItemEntity> getTypeList(ConfigFeeItemPagination configFeeItemPagination,String dataType);

    ConfigFeeItemEntity getInfo(String id);

    void delete(ConfigFeeItemEntity entity);

    void create(ConfigFeeItemEntity entity);

    boolean update( String id, ConfigFeeItemEntity entity);
}
