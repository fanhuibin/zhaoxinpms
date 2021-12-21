package com.zhaoxinms.baseconfig.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhaoxinms.baseconfig.entity.ConfigFeeAlertEntity;
import com.zhaoxinms.baseconfig.model.configfeealert.ConfigFeeAlertPagination;

public interface ConfigFeeAlertService extends IService<ConfigFeeAlertEntity> {

    List<ConfigFeeAlertEntity> getList(ConfigFeeAlertPagination configFeeAlertPagination);

    List<ConfigFeeAlertEntity> getTypeList(ConfigFeeAlertPagination configFeeAlertPagination, String dataType);

    ConfigFeeAlertEntity getInfo(String id);

    void delete(ConfigFeeAlertEntity entity);

    void create(ConfigFeeAlertEntity entity);

    boolean update(String id, ConfigFeeAlertEntity entity);

    // 子表方法
}
