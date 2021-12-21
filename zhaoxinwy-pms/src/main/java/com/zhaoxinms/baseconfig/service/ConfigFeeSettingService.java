package com.zhaoxinms.baseconfig.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhaoxinms.baseconfig.entity.ConfigFeeSettingEntity;
import com.zhaoxinms.baseconfig.model.configfeesetting.ConfigFeeSettingPagination;

import java.util.*;
/**
 *
 * config_fee_setting
 * 版本： V3.1.0
 * 版权： 
 * 作者： CYCBERFORM
 * 日期： 2021-11-08 10:00:55
 */
public interface ConfigFeeSettingService extends IService<ConfigFeeSettingEntity> {

    List<ConfigFeeSettingEntity> getList(ConfigFeeSettingPagination configFeeSettingPagination);

    boolean update( String type, List<ConfigFeeSettingEntity> entity);
}
