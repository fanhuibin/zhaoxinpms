package com.zhaoxinms.baseconfig.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhaoxinms.base.exception.DataException;
import com.zhaoxinms.base.util.StringUtil;
import com.zhaoxinms.base.util.UserProvider;
import com.zhaoxinms.baseconfig.entity.ConfigFeeItemEntity;
import com.zhaoxinms.baseconfig.mapper.ConfigFeeItemMapper;
import com.zhaoxinms.baseconfig.model.configfeeitem.ConfigFeeItemPagination;
import com.zhaoxinms.baseconfig.service.ConfigFeeItemService;
import com.zhaoxinms.common.core.domain.entity.SysUser;
import com.zhaoxinms.event.FeeEvent;
import com.zhaoxinms.util.DynamicExpressiontUtil;
import com.zhaoxinms.util.InputCheckUtil;

@Service
public class ConfigFeeItemServiceImpl extends ServiceImpl<ConfigFeeItemMapper, ConfigFeeItemEntity> implements ConfigFeeItemService {

    @Autowired
    private UserProvider userProvider;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public static final String TYPE_HOUSE = "house";
    public static final String TYPE_TEMPORARY = "temp";
    public static final String TYPE_DEPOSIT = "deposit";

    public static final String FORMULA_BASE = "base";
    public static final String FORMULA_EXPRESSION = "expression";

    public static final String Number_Flat = "flat";
    public static final String Number_PEOPLE = "people";
    public static final String Number_FLOOR = "floor";
    public static final String Number_BUILDINGAREA = "building_area";
    public static final String Number_USEAREA = "use_area";
    public static final String Number_METER = "meter";
    public static final String Number_RENT_FEE = "rent_fee";

    public static final String GENERATE_METER = "meter"; // 通过抄表功能生成收费数据
    public static final String GENERATE_BASE = "base"; // 通过通用的方法生成收费数据

    @Override
    public List<ConfigFeeItemEntity> getList(ConfigFeeItemPagination configFeeItemPagination) {
        String userId = "" + userProvider.get().getUserId();
        QueryWrapper<ConfigFeeItemEntity> queryWrapper = new QueryWrapper<>();
        if (InputCheckUtil.isNotEmpty(configFeeItemPagination.getType())) {
            queryWrapper.lambda().and(t -> t.eq(ConfigFeeItemEntity::getType, configFeeItemPagination.getType()));
        }

        if (InputCheckUtil.isNotEmpty(configFeeItemPagination.getName())) {
            queryWrapper.lambda().and(t -> t.like(ConfigFeeItemEntity::getName, configFeeItemPagination.getName()));
        }

        if (InputCheckUtil.isNotEmpty(configFeeItemPagination.getFormula())) {
            queryWrapper.lambda().and(t -> t.eq(ConfigFeeItemEntity::getFormula, configFeeItemPagination.getFormula()));
        }

        if (InputCheckUtil.isNotEmpty(configFeeItemPagination.getNumType())) {
            queryWrapper.lambda().and(t -> t.eq(ConfigFeeItemEntity::getNumType, configFeeItemPagination.getNumType()));
        }

        if (InputCheckUtil.isNotEmpty(configFeeItemPagination.getLateFeeEnable())) {
            queryWrapper.lambda().and(t -> t.eq(ConfigFeeItemEntity::getLateFeeEnable, configFeeItemPagination.getLateFeeEnable()));
        }

        if (InputCheckUtil.isNotEmpty(configFeeItemPagination.getLateFeeRate())) {
            queryWrapper.lambda().and(t -> t.eq(ConfigFeeItemEntity::getLateFeeRate, configFeeItemPagination.getLateFeeRate()));
        }

        if (InputCheckUtil.isNotEmpty(configFeeItemPagination.getLateFeeDays())) {
            queryWrapper.lambda().and(t -> t.eq(ConfigFeeItemEntity::getLateFeeDays, configFeeItemPagination.getLateFeeDays()));
        }
        queryWrapper.lambda().and(t -> t.eq(ConfigFeeItemEntity::getEnabledMark, "1"));

        // 排序
        if (StringUtil.isEmpty(configFeeItemPagination.getSidx())) {
            queryWrapper.lambda().orderByDesc(ConfigFeeItemEntity::getId);
        } else {
            queryWrapper = "asc".equals(configFeeItemPagination.getSort().toLowerCase()) ? queryWrapper.orderByAsc(configFeeItemPagination.getSidx())
                : queryWrapper.orderByDesc(configFeeItemPagination.getSidx());
        }
        Page<ConfigFeeItemEntity> page = new Page<>(configFeeItemPagination.getCurrentPage(), configFeeItemPagination.getPageSize());
        IPage<ConfigFeeItemEntity> userIPage = this.page(page, queryWrapper);
        return configFeeItemPagination.setData(userIPage.getRecords(), userIPage.getTotal());
    }

    @Override
    public List<ConfigFeeItemEntity> getTypeList(ConfigFeeItemPagination configFeeItemPagination, String dataType) {
        QueryWrapper<ConfigFeeItemEntity> queryWrapper = new QueryWrapper<>();
        if (InputCheckUtil.isNotEmpty(configFeeItemPagination.getType())) {
            queryWrapper.lambda().and(t -> t.eq(ConfigFeeItemEntity::getType, configFeeItemPagination.getType()));
        }

        if (InputCheckUtil.isNotEmpty(configFeeItemPagination.getName())) {
            queryWrapper.lambda().and(t -> t.like(ConfigFeeItemEntity::getName, configFeeItemPagination.getName()));
        }

        if (InputCheckUtil.isNotEmpty(configFeeItemPagination.getFormula())) {
            queryWrapper.lambda().and(t -> t.eq(ConfigFeeItemEntity::getFormula, configFeeItemPagination.getFormula()));
        }

        if (InputCheckUtil.isNotEmpty(configFeeItemPagination.getNumType())) {
            queryWrapper.lambda().and(t -> t.eq(ConfigFeeItemEntity::getNumType, configFeeItemPagination.getNumType()));
        }

        if (InputCheckUtil.isNotEmpty(configFeeItemPagination.getLateFeeEnable())) {
            queryWrapper.lambda().and(t -> t.eq(ConfigFeeItemEntity::getLateFeeEnable, configFeeItemPagination.getLateFeeEnable()));
        }

        if (InputCheckUtil.isNotEmpty(configFeeItemPagination.getLateFeeRate())) {
            queryWrapper.lambda().and(t -> t.eq(ConfigFeeItemEntity::getLateFeeRate, configFeeItemPagination.getLateFeeRate()));
        }

        if (InputCheckUtil.isNotEmpty(configFeeItemPagination.getLateFeeDays())) {
            queryWrapper.lambda().and(t -> t.eq(ConfigFeeItemEntity::getLateFeeDays, configFeeItemPagination.getLateFeeDays()));
        }

        // 排序
        if (StringUtil.isEmpty(configFeeItemPagination.getSidx())) {
            queryWrapper.lambda().orderByDesc(ConfigFeeItemEntity::getId);
        } else {
            queryWrapper = "asc".equals(configFeeItemPagination.getSort().toLowerCase()) ? queryWrapper.orderByAsc(configFeeItemPagination.getSidx())
                : queryWrapper.orderByDesc(configFeeItemPagination.getSidx());
        }
        if ("0".equals(dataType)) {
            Page<ConfigFeeItemEntity> page = new Page<>(configFeeItemPagination.getCurrentPage(), configFeeItemPagination.getPageSize());
            IPage<ConfigFeeItemEntity> userIPage = this.page(page, queryWrapper);
            return configFeeItemPagination.setData(userIPage.getRecords(), userIPage.getTotal());
        } else {
            return this.list(queryWrapper);
        }
    }

    @Override
    public ConfigFeeItemEntity getInfo(String id) {
        QueryWrapper<ConfigFeeItemEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ConfigFeeItemEntity::getId, id);
        return this.getOne(queryWrapper);
    }

    @Override
    public void create(ConfigFeeItemEntity entity) {
        dataCheck(entity);
        // 收费项名不能重复
        QueryWrapper<ConfigFeeItemEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().and(t -> t.eq(ConfigFeeItemEntity::getName, entity.getName()));
        List<ConfigFeeItemEntity> list = this.list(queryWrapper);
        if (list.size() > 0) {
            throw new DataException("该收费项已存在，创建失败");
        }
        if (entity.getNumType().equals(this.Number_METER)) {
            entity.setGenerateType(this.GENERATE_METER);
        } else {
            entity.setGenerateType(this.GENERATE_BASE);
        }
        this.save(entity);
    }

    private void dataCheck(ConfigFeeItemEntity entity) {
        if (entity.getType().equals(ConfigFeeItemServiceImpl.TYPE_HOUSE)) {
            if (new BigDecimal(entity.getPrice()).compareTo(BigDecimal.ZERO) < 0) {
                throw new DataException("单价不能小于0");
            }
        }
        if (entity.getType().equals(ConfigFeeItemServiceImpl.TYPE_DEPOSIT) || entity.getType().equals(ConfigFeeItemServiceImpl.TYPE_TEMPORARY)) {
            entity.setPrice("0");
            entity.setLateFeeEnable("0");
            entity.setPeriod("1");
            entity.setFormula("");
            entity.setNumType("");
        }
        if (entity.getLateFeeEnable().equals("0")) {
            entity.setLateFeeDays("0");
            entity.setLateFeeRate("0");
        }
        if (entity.getFormula().equals(ConfigFeeItemServiceImpl.FORMULA_EXPRESSION)) {
            // 验证脚本格式
            try {
                DynamicExpressiontUtil.validateExpress(entity.getFormulaExpression());
            } catch (Exception e) {
                e.printStackTrace();
                throw new DataException("验证自定义公式失败");
            }
        } else {
            entity.setFormulaExpression("");
        }
    }

    @Override
    public boolean update(String id, ConfigFeeItemEntity entity) {
        entity.setId(id);
        
        //收费项不能修改类型
        ConfigFeeItemEntity oldEntity = this.getById(id);
        if(!oldEntity.getType().equals(entity.getType())) {
            throw new DataException("收费项不能更改类型");
        }
        dataCheck(entity);
        
        // 收费项名不能重复
        QueryWrapper<ConfigFeeItemEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().and(t -> t.eq(ConfigFeeItemEntity::getName, entity.getName()));
        List<ConfigFeeItemEntity> list = this.list(queryWrapper);
        if (list.size() == 1) {
            if (!list.get(0).getId().equals(entity.getId())) {
                throw new DataException("该收费项名已存在，更新失败");
            }
        } else if (list.size() > 1) {
            throw new DataException("该收费项名已存在，更新失败");
        }
        if (entity.getNumType().equals(this.Number_METER)) {
            entity.setGenerateType(this.GENERATE_METER);
        } else {
            entity.setGenerateType(this.GENERATE_BASE);
        }

        return this.updateById(entity);
    }

    @Override
    public void delete(ConfigFeeItemEntity entity) {
        SysUser userInfo = userProvider.get();
        // 广播删除事件
        applicationEventPublisher.publishEvent(new FeeEvent(this, entity, FeeEvent.STATE_DELETE));
        if (entity != null) {
            entity.setEnabledMark(0);
            entity.setDeleteTime(new Date());
            entity.setDeleteUserId("" + userInfo.getUserId());
            this.updateById(entity);
        }
    }
}