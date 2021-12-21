package com.zhaoxinms.baseconfig.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhaoxinms.base.exception.DataException;
import com.zhaoxinms.base.util.UserProvider;
import com.zhaoxinms.baseconfig.entity.ConfigHouseBlockEntity;
import com.zhaoxinms.baseconfig.mapper.ConfigHouseBlockMapper;
import com.zhaoxinms.baseconfig.model.confighouseblock.ConfigHouseBlockPagination;
import com.zhaoxinms.baseconfig.service.ConfigHouseBlockService;
import com.zhaoxinms.event.BlockEvent;
import com.zhaoxinms.util.InputCheckUtil;

@Service
public class ConfigHouseBlockServiceImpl extends ServiceImpl<ConfigHouseBlockMapper, ConfigHouseBlockEntity> implements ConfigHouseBlockService {

    @Autowired
    private UserProvider userProvider;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public List<ConfigHouseBlockEntity> getList(ConfigHouseBlockPagination configHouseBlockPagination) {
        String userId = "" + userProvider.get().getUserId();
        QueryWrapper<ConfigHouseBlockEntity> queryWrapper = new QueryWrapper<>();
        if (InputCheckUtil.isNotEmpty(configHouseBlockPagination.getCode())) {
            queryWrapper.lambda().and(t -> t.eq(ConfigHouseBlockEntity::getCode, configHouseBlockPagination.getCode()));
        }

        if (InputCheckUtil.isNotEmpty(configHouseBlockPagination.getName())) {
            queryWrapper.lambda().and(t -> t.like(ConfigHouseBlockEntity::getName, configHouseBlockPagination.getName()));
        }

        queryWrapper.lambda().orderByAsc(ConfigHouseBlockEntity::getCreatorTime);
        Page<ConfigHouseBlockEntity> page = new Page<>(configHouseBlockPagination.getCurrentPage(), configHouseBlockPagination.getPageSize());
        IPage<ConfigHouseBlockEntity> userIPage = this.page(page, queryWrapper);
        return configHouseBlockPagination.setData(userIPage.getRecords(), userIPage.getTotal());
    }

    @Override
    public List<ConfigHouseBlockEntity> getTypeList(ConfigHouseBlockPagination configHouseBlockPagination, String dataType) {
        QueryWrapper<ConfigHouseBlockEntity> queryWrapper = new QueryWrapper<>();
        if (InputCheckUtil.isNotEmpty(configHouseBlockPagination.getCode())) {
            queryWrapper.lambda().and(t -> t.eq(ConfigHouseBlockEntity::getCode, configHouseBlockPagination.getCode()));
        }

        if (InputCheckUtil.isNotEmpty(configHouseBlockPagination.getName())) {
            queryWrapper.lambda().and(t -> t.like(ConfigHouseBlockEntity::getName, configHouseBlockPagination.getName()));
        }

        queryWrapper.lambda().orderByAsc(ConfigHouseBlockEntity::getCreatorTime);
        if ("0".equals(dataType)) {
            Page<ConfigHouseBlockEntity> page = new Page<>(configHouseBlockPagination.getCurrentPage(), configHouseBlockPagination.getPageSize());
            IPage<ConfigHouseBlockEntity> userIPage = this.page(page, queryWrapper);
            return configHouseBlockPagination.setData(userIPage.getRecords(), userIPage.getTotal());
        } else {
            return this.list(queryWrapper);
        }
    }

    @Override
    public ConfigHouseBlockEntity getInfo(String id) {
        QueryWrapper<ConfigHouseBlockEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ConfigHouseBlockEntity::getId, id);
        return this.getOne(queryWrapper);
    }

    @Override
    public void create(ConfigHouseBlockEntity entity) {
        // 商业区的code和name都不能重复
        QueryWrapper<ConfigHouseBlockEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ConfigHouseBlockEntity::getCode, entity.getCode());
        ConfigHouseBlockEntity result = this.getOne(queryWrapper);
        if (result != null && StringUtils.isNotEmpty(result.getCode())) {
            throw new DataException("该编号已经存在，创建失败");
        }
        
        queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ConfigHouseBlockEntity::getName, entity.getName());
        result = this.getOne(queryWrapper);
        if (result != null && StringUtils.isNotEmpty(result.getCode())) {
            throw new DataException("该商业区名已经存在，创建失败");
        }

        this.save(entity);
    }

    @Override
    public boolean update(String id, ConfigHouseBlockEntity entity) {
        // 修改的时候不能修改编号
        ConfigHouseBlockEntity oldEntity = this.getById(entity.getId());
        if (!oldEntity.getCode().equals(entity.getCode())) {
            throw new DataException("商业区的编号不允许修改");
        }

        entity.setId(id);
        return this.updateById(entity);
    }

    @Override
    public void delete(ConfigHouseBlockEntity entity) {
        applicationEventPublisher.publishEvent(new BlockEvent(this, entity, BlockEvent.STATE_DELETE));
        this.removeById(entity.getId());
    }
}