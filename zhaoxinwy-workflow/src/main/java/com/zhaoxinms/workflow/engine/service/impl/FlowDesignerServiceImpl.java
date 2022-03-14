package com.zhaoxinms.workflow.engine.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhaoxinms.base.exception.DataException;
import com.zhaoxinms.base.util.StringUtil;
import com.zhaoxinms.base.util.UserProvider;
import com.zhaoxinms.common.utils.StringUtils;
import com.zhaoxinms.workflow.engine.entity.FlowDesignerEntity;
import com.zhaoxinms.workflow.engine.mapper.FlowDesignerMapper;
import com.zhaoxinms.workflow.engine.model.flowDesigner.PaginationFlowDesigner;
import com.zhaoxinms.workflow.engine.service.FlowDesignerService;


@Service
public class FlowDesignerServiceImpl extends ServiceImpl<FlowDesignerMapper, FlowDesignerEntity> implements FlowDesignerService {

    @Autowired
    private UserProvider userProvider;
    @Autowired
    private FlowDesignerService flowEngineService;

    @Override
    public List<FlowDesignerEntity> getList(PaginationFlowDesigner pagination) {
        QueryWrapper<FlowDesignerEntity> queryWrapper = new QueryWrapper<>();
        if (StringUtil.isNotEmpty(pagination.getKeyword())) {
            queryWrapper.lambda().like(FlowDesignerEntity::getFullName, pagination.getKeyword());
        }
        if (StringUtil.isNotEmpty(pagination.getEnabledMark())) {
            queryWrapper.lambda().like(FlowDesignerEntity::getEnabledMark, pagination.getEnabledMark());
        }
        if (StringUtil.isNotEmpty(pagination.getFullName())) {
            queryWrapper.lambda().like(FlowDesignerEntity::getFullName, pagination.getFullName());
        }
        queryWrapper.lambda().orderByAsc(FlowDesignerEntity::getSortCode);
        return this.list(queryWrapper);
    }

    @Override
    public FlowDesignerEntity getInfo(String id) throws DataException {
        QueryWrapper<FlowDesignerEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(FlowDesignerEntity::getId, id);
        FlowDesignerEntity flowEngineEntity = this.getOne(queryWrapper);
        if (flowEngineEntity == null) {
            throw new DataException("未找到流程引擎");
        }
        return flowEngineEntity;
    }

    @Override
    public FlowDesignerEntity getInfoByEnCode(String enCode) throws DataException {
        QueryWrapper<FlowDesignerEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(FlowDesignerEntity::getEnCode, enCode).eq(FlowDesignerEntity::getEnabledMark, 1);
        FlowDesignerEntity flowEngineEntity = this.getOne(queryWrapper);
        if (flowEngineEntity == null) {
            throw new DataException("未找到流程引擎");
        }
        return flowEngineEntity;
    }

    @Override
    public boolean isExistByFullName(String fullName, String id) {
        QueryWrapper<FlowDesignerEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(FlowDesignerEntity::getFullName, fullName);
        if (!StringUtils.isEmpty(id)) {
            queryWrapper.lambda().ne(FlowDesignerEntity::getId, id);
        }
        return this.count(queryWrapper) > 0 ? true : false;
    }

    @Override
    public boolean isExistByEnCode(String enCode, String id) {
        QueryWrapper<FlowDesignerEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(FlowDesignerEntity::getEnCode, enCode);
        if (!StringUtils.isEmpty(id)) {
            queryWrapper.lambda().ne(FlowDesignerEntity::getId, id);
        }
        return this.count(queryWrapper) > 0 ? true : false;
    }

    @Override
    public void delete(FlowDesignerEntity entity) {
        this.removeById(entity.getId());
    }

    @Override
    public boolean update(String id, FlowDesignerEntity entity) {
        entity.setId(id);
        entity.setLastModifyTime(new Date());
        entity.setLastModifyUserId(""+userProvider.get().getUserId());
        return this.updateById(entity);
    }

    @Override
    public void create(FlowDesignerEntity entity) {
        this.save(entity);
    }
}
