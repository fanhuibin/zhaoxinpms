package com.zhaoxinms.baseconfig.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhaoxinms.base.util.StringUtil;
import com.zhaoxinms.base.util.UserProvider;
import com.zhaoxinms.baseconfig.entity.ConfigFeeAlertEntity;
import com.zhaoxinms.baseconfig.mapper.ConfigFeeAlertMapper;
import com.zhaoxinms.baseconfig.model.configfeealert.ConfigFeeAlertPagination;
import com.zhaoxinms.baseconfig.service.ConfigFeeAlertService;
import com.zhaoxinms.event.FeeEvent;
import com.zhaoxinms.util.InputCheckUtil;

@Service
public class ConfigFeeAlertServiceImpl extends ServiceImpl<ConfigFeeAlertMapper, ConfigFeeAlertEntity> implements ConfigFeeAlertService {

    @Autowired
    private UserProvider userProvider;

    @Override
    public List<ConfigFeeAlertEntity> getList(ConfigFeeAlertPagination configFeeAlertPagination) {
        Long userId = userProvider.get().getUserId();
        QueryWrapper<ConfigFeeAlertEntity> queryWrapper = new QueryWrapper<>();
        if (InputCheckUtil.isNotEmpty(configFeeAlertPagination.getFeeId())) {
            queryWrapper.lambda().and(t -> t.eq(ConfigFeeAlertEntity::getFeeId, configFeeAlertPagination.getFeeId()));
        }

        if (InputCheckUtil.isNotEmpty(configFeeAlertPagination.getDay())) {
            queryWrapper.lambda().and(t -> t.eq(ConfigFeeAlertEntity::getDay, configFeeAlertPagination.getDay()));
        }

        // 排序
        if (StringUtil.isEmpty(configFeeAlertPagination.getSidx())) {
            queryWrapper.lambda().orderByDesc(ConfigFeeAlertEntity::getId);
        } else {
            queryWrapper = "asc".equals(configFeeAlertPagination.getSort().toLowerCase()) ? queryWrapper.orderByAsc(configFeeAlertPagination.getSidx())
                : queryWrapper.orderByDesc(configFeeAlertPagination.getSidx());
        }
        Page<ConfigFeeAlertEntity> page = new Page<>(configFeeAlertPagination.getCurrentPage(), configFeeAlertPagination.getPageSize());
        IPage<ConfigFeeAlertEntity> userIPage = this.page(page, queryWrapper);
        return configFeeAlertPagination.setData(userIPage.getRecords(), userIPage.getTotal());
    }

    @Override
    public List<ConfigFeeAlertEntity> getTypeList(ConfigFeeAlertPagination configFeeAlertPagination, String dataType) {
        QueryWrapper<ConfigFeeAlertEntity> queryWrapper = new QueryWrapper<>();
        if (InputCheckUtil.isNotEmpty(configFeeAlertPagination.getFeeId())) {
            queryWrapper.lambda().and(t -> t.eq(ConfigFeeAlertEntity::getFeeId, configFeeAlertPagination.getFeeId()));
        }

        if (InputCheckUtil.isNotEmpty(configFeeAlertPagination.getDay())) {
            queryWrapper.lambda().and(t -> t.eq(ConfigFeeAlertEntity::getDay, configFeeAlertPagination.getDay()));
        }

        // 排序
        if (StringUtil.isEmpty(configFeeAlertPagination.getSidx())) {
            queryWrapper.lambda().orderByDesc(ConfigFeeAlertEntity::getId);
        } else {
            queryWrapper = "asc".equals(configFeeAlertPagination.getSort().toLowerCase()) ? queryWrapper.orderByAsc(configFeeAlertPagination.getSidx())
                : queryWrapper.orderByDesc(configFeeAlertPagination.getSidx());
        }
        if ("0".equals(dataType)) {
            Page<ConfigFeeAlertEntity> page = new Page<>(configFeeAlertPagination.getCurrentPage(), configFeeAlertPagination.getPageSize());
            IPage<ConfigFeeAlertEntity> userIPage = this.page(page, queryWrapper);
            return configFeeAlertPagination.setData(userIPage.getRecords(), userIPage.getTotal());
        } else {
            return this.list(queryWrapper);
        }
    }

    @Override
    public ConfigFeeAlertEntity getInfo(String id) {
        QueryWrapper<ConfigFeeAlertEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ConfigFeeAlertEntity::getId, id);
        return this.getOne(queryWrapper);
    }

    @Override
    public void create(ConfigFeeAlertEntity entity) {
        this.save(entity);
    }

    @Override
    public boolean update(String id, ConfigFeeAlertEntity entity) {
        entity.setId(id);
        return this.updateById(entity);
    }

    @Override
    public void delete(ConfigFeeAlertEntity entity) {
        if (entity != null) {
            this.removeById(entity.getId());
        }
    }

    @EventListener
    public void deleteFee(FeeEvent event) {
        if (event.getState().equals(FeeEvent.STATE_DELETE)) {
            // 同步删除alert
            String feeItemId = event.getItem().getId();
            QueryWrapper<ConfigFeeAlertEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().and(t -> t.eq(ConfigFeeAlertEntity::getFeeId, feeItemId));
            List<ConfigFeeAlertEntity> alerts = this.list(queryWrapper);
            for (ConfigFeeAlertEntity alert : alerts) {
                this.delete(alert);
            }
        }
    }
}