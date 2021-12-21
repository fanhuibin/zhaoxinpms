package com.zhaoxinms.baseconfig.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhaoxinms.base.util.UserProvider;
import com.zhaoxinms.baseconfig.entity.ConfigFeeAlertEntity;
import com.zhaoxinms.baseconfig.entity.ConfigFeeSettingEntity;
import com.zhaoxinms.baseconfig.mapper.ConfigFeeSettingMapper;
import com.zhaoxinms.baseconfig.model.configfeesetting.ConfigFeeSettingPagination;
import com.zhaoxinms.baseconfig.service.ConfigFeeSettingService;
import com.zhaoxinms.event.FeeEvent;

@Service
public class ConfigFeeSettingServiceImpl extends ServiceImpl<ConfigFeeSettingMapper, ConfigFeeSettingEntity> implements ConfigFeeSettingService {

    @Autowired
    private UserProvider userProvider;

    @Override
    public List<ConfigFeeSettingEntity> getList(ConfigFeeSettingPagination configFeeSettingPagination) {
        QueryWrapper<ConfigFeeSettingEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ConfigFeeSettingEntity::getType, configFeeSettingPagination.getType());
        return this.list(queryWrapper);
    }

    @Override
    public boolean update(String type, List<ConfigFeeSettingEntity> list) {
        ConfigFeeSettingPagination page = new ConfigFeeSettingPagination();
        page.setType(type);
        List<ConfigFeeSettingEntity> entitys = this.getList(page);
        for (ConfigFeeSettingEntity e : entitys) {
            this.baseMapper.deleteById(e.getId());
        }
        
        //去除重复的收费项
        Map<String,String> addedFee = new HashMap<String,String>(); 
        List<ConfigFeeSettingEntity> addList = new ArrayList<ConfigFeeSettingEntity>();
        for (ConfigFeeSettingEntity fee : list) {
            if (!addedFee.containsKey(fee.getFeeItemId())) {
               addList.add(fee); 
               addedFee.put(fee.getFeeItemId(), "");
            }
        }
        
        for (ConfigFeeSettingEntity entity : addList) {
            entity.setType(type);
        }

        this.saveBatch(addList);
        return false;
    }

    @EventListener
    public void delete(FeeEvent event) {
        if (event.getState().equals(FeeEvent.STATE_DELETE)) {
            // 同步删除setting
            String feeItemId = event.getItem().getId();
            QueryWrapper<ConfigFeeSettingEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().and(t -> t.eq(ConfigFeeSettingEntity::getFeeItemId, feeItemId));
            List<ConfigFeeSettingEntity> settings = this.list(queryWrapper);
            for (ConfigFeeSettingEntity setting : settings) {
                this.removeById(setting.getId());
            }
        }
    }
}