/**
 * Copyright 肇新智慧物业管理系统
 *
 * Licensed under AGPL开源协议
 *
 * gitee：https://gitee.com/fanhuibin1/zhaoxinpms
 * website：http://pms.zhaoxinms.com  wx： zhaoxinms
 *
 */
package com.zhaoxinms.payment.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhaoxinms.base.exception.DataException;
import com.zhaoxinms.base.util.JsonUtil;
import com.zhaoxinms.base.util.StringUtil;
import com.zhaoxinms.base.util.UserProvider;
import com.zhaoxinms.baseconfig.entity.ConfigFeeItemEntity;
import com.zhaoxinms.baseconfig.entity.ConfigHouseEntity;
import com.zhaoxinms.baseconfig.model.configfeeitem.ConfigFeeItemPagination;
import com.zhaoxinms.baseconfig.model.house.HousePagination;
import com.zhaoxinms.baseconfig.service.ConfigFeeItemService;
import com.zhaoxinms.baseconfig.service.ConfigHouseService;
import com.zhaoxinms.baseconfig.service.impl.ConfigFeeItemServiceImpl;
import com.zhaoxinms.payment.entity.PaymentMeterEntity;
import com.zhaoxinms.payment.entity.PaymentMeterIndexEntity;
import com.zhaoxinms.payment.mapper.PaymentMeterMapper;
import com.zhaoxinms.payment.model.paymentmeter.PaymentMeterImport;
import com.zhaoxinms.payment.model.paymentmeter.PaymentMeterPagination;
import com.zhaoxinms.payment.model.paymentmeter.PaymentMeterUpForm;
import com.zhaoxinms.payment.service.PaymentMeterIndexService;
import com.zhaoxinms.payment.service.PaymentMeterService;
import com.zhaoxinms.util.CalculationUtil;
import com.zhaoxinms.util.DateUtils;
import com.zhaoxinms.util.FeeCalculationUtil;
import com.zhaoxinms.util.InputCheckUtil;
import com.zhaoxinms.util.ValidateUtil;

/**
 *
 * payment_meter 版本： V3.1.0 版权： 作者： CYCBERFORM 日期： 2021-09-24 17:52:05
 */
@Service

public class PaymentMeterServiceImpl extends ServiceImpl<PaymentMeterMapper, PaymentMeterEntity>
    implements PaymentMeterService {

    @Autowired
    private UserProvider userProvider;
    @Autowired
    private ConfigHouseService configHouseService;
    @Autowired
    private ConfigFeeItemService configFeeItemService;
    @Autowired
    private PaymentMeterIndexService paymentMeterIndexService;

    @Override
    public List<PaymentMeterEntity> getList(PaymentMeterPagination paymentMeterPagination) {
        String userId = "" + userProvider.get().getUserId();
        QueryWrapper<PaymentMeterEntity> queryWrapper = new QueryWrapper<>();
        if (InputCheckUtil.isNotEmpty(paymentMeterPagination.getFeeItemId())) {
            queryWrapper.lambda()
                .and(t -> t.eq(PaymentMeterEntity::getFeeItemId, paymentMeterPagination.getFeeItemId()));
        }

        if (InputCheckUtil.isNotEmpty(paymentMeterPagination.getResourceName())) {
            queryWrapper.lambda()
                .and(t -> t.eq(PaymentMeterEntity::getResourceName, paymentMeterPagination.getResourceName()));
        }

        // 排序
        if (StringUtil.isEmpty(paymentMeterPagination.getSidx())) {
            queryWrapper.lambda().orderByDesc(PaymentMeterEntity::getId);
        } else {
            queryWrapper = "asc".equals(paymentMeterPagination.getSort().toLowerCase())
                ? queryWrapper.orderByAsc(paymentMeterPagination.getSidx())
                : queryWrapper.orderByDesc(paymentMeterPagination.getSidx());
        }
        Page<PaymentMeterEntity> page =
            new Page<>(paymentMeterPagination.getCurrentPage(), paymentMeterPagination.getPageSize());
        IPage<PaymentMeterEntity> userIPage = this.page(page, queryWrapper);
        return paymentMeterPagination.setData(userIPage.getRecords(), userIPage.getTotal());
    }

    @Override
    public List<PaymentMeterEntity> getTypeList(PaymentMeterPagination paymentMeterPagination, String dataType) {
        QueryWrapper<PaymentMeterEntity> queryWrapper = new QueryWrapper<>();
        if (InputCheckUtil.isNotEmpty(paymentMeterPagination.getFeeItemId())) {
            queryWrapper.lambda()
                .and(t -> t.eq(PaymentMeterEntity::getFeeItemId, paymentMeterPagination.getFeeItemId()));
        }

        if (InputCheckUtil.isNotEmpty(paymentMeterPagination.getResourceName())) {
            queryWrapper.lambda()
                .and(t -> t.eq(PaymentMeterEntity::getResourceName, paymentMeterPagination.getResourceName()));
        }

        // 排序
        if (StringUtil.isEmpty(paymentMeterPagination.getSidx())) {
            queryWrapper.lambda().orderByDesc(PaymentMeterEntity::getId);
        } else {
            queryWrapper = "asc".equals(paymentMeterPagination.getSort().toLowerCase())
                ? queryWrapper.orderByAsc(paymentMeterPagination.getSidx())
                : queryWrapper.orderByDesc(paymentMeterPagination.getSidx());
        }
        if ("0".equals(dataType)) {
            Page<PaymentMeterEntity> page =
                new Page<>(paymentMeterPagination.getCurrentPage(), paymentMeterPagination.getPageSize());
            IPage<PaymentMeterEntity> userIPage = this.page(page, queryWrapper);
            return paymentMeterPagination.setData(userIPage.getRecords(), userIPage.getTotal());
        } else {
            return this.list(queryWrapper);
        }
    }

    @Override
    public PaymentMeterEntity getInfo(String id) {
        QueryWrapper<PaymentMeterEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(PaymentMeterEntity::getId, id);
        return this.getOne(queryWrapper);
    }

    @Override
    public void create(PaymentMeterEntity entity) {
        this.save(entity);
    }

    @Override
    public boolean update(String id, PaymentMeterEntity entity) {
        throw new DataException("该方法未实现");
    }

    @Override
    public void delete(PaymentMeterEntity entity) {
        if (entity != null) {
            this.removeById(entity.getId());
        }
    }

    @Override
    public int importMeter(List<PaymentMeterImport> meterImportList) {
        List<PaymentMeterEntity> meterList = new ArrayList<PaymentMeterEntity>();
        // 查询所有的商铺
        HousePagination search = new HousePagination();
        List<ConfigHouseEntity> houses = configHouseService.getTypeList(search, "1");

        // 查询所有的收费项
        ConfigFeeItemPagination itemSearch = new ConfigFeeItemPagination();
        itemSearch.setNumType(ConfigFeeItemServiceImpl.Number_METER);
        List<ConfigFeeItemEntity> items = configFeeItemService.getTypeList(itemSearch, "1");

        // 第一部分验证数据&数据格式转换
        for (int i = 0; i < meterImportList.size(); i++) {
            PaymentMeterImport meter = meterImportList.get(i);
            String resourceId = "";
            String resourceName = "";
            String feeItemName = "";
            String feeItemId = "";
            Date currentDate = null;
            Date lastDate = null;

            // 校验商铺数据
            for (ConfigHouseEntity entity : houses) {
                if (entity.getBlock().equals(meter.getBlock()) && entity.getCode().equals(meter.getCode())) {
                    resourceId = entity.getId();
                    resourceName = entity.getName();
                }
            }
            if (StringUtils.isEmpty(resourceId) || StringUtils.isEmpty(resourceName)) {
                throw new DataException("校验第" + (i + 1) + "条数据失败，商铺信息有误");
            }

            // 校验收费项信息
            for (ConfigFeeItemEntity item : items) {
                if (item.getName().equals(meter.getFeeItemName())) {
                    feeItemId = item.getId();
                    feeItemName = item.getName();
                }
            }
            if (StringUtils.isEmpty(feeItemId) || StringUtils.isEmpty(feeItemName)) {
                throw new DataException("校验第" + (i + 1) + "条数据失败，解析费用类型失败");
            }

            if (!StringUtils.isEmpty(meter.getLastIndexDate()) && !ValidateUtil.validDateStr(meter.getLastIndexDate(), "yyyy-MM-dd")) {
                throw new DataException("校验第" + (i + 1) + "条数据失败，上次读表时间格式不正确");
            }
            if (!ValidateUtil.validDateStr(meter.getCurrentIndexDate(), "yyyy-MM-dd")) {
                throw new DataException("校验第" + (i + 1) + "条数据失败，本次读表时间格式不正确");
            }
            try {
                if (!StringUtils.isEmpty(meter.getLastIndexDate())) {
                    lastDate = DateUtils.parseDate(meter.getLastIndexDate());
                }
                currentDate = DateUtils.parseDate(meter.getCurrentIndexDate());
            } catch (Exception e) {
                throw new DataException("校验第" + (i + 1) + "条数据失败，日期格式不正确");
            }

            if (!ValidateUtil.PositiveFloatOrNum(meter.getCurrentIndex())) {
                throw new DataException("校验第" + (i + 1) + "条数据失败，本期度数格式不正确");
            }
            
            if (!ValidateUtil.PositiveFloatOrNum(meter.getLastIndex())) {
                throw new DataException("校验第" + (i + 1) + "条数据失败，上期度数格式不正确");
            }

            if (!ValidateUtil.Posttive_float(meter.getMultiple())
                && !ValidateUtil.PositiveNumber(meter.getMultiple())) {
                throw new DataException("校验第" + (i + 1) + "条数据失败，倍率格式不正确");
            }

            if (!ValidateUtil.Posttive_float(meter.getLoss()) && !ValidateUtil.PositiveNumber(meter.getLoss())) {
                throw new DataException("校验第" + (i + 1) + "条数据失败，损耗格式不正确");
            }

            if (CalculationUtil.compareTo(meter.getCurrentIndex(), meter.getLastIndex(), 2, 0) < 0) {
                throw new DataException("校验第" + (i + 1) + "条数据失败，本期度数不能小于上期度数");
            }
            if (CalculationUtil.compareTo(meter.getCurrentIndex(), meter.getLastIndex(), 2, 0) == 0) {
                //本期读数与上期读数差值为0， 则直接跳过
                continue;
            }

            PaymentMeterEntity meterEntity = new PaymentMeterEntity();
            meterEntity.setBlockCode(meter.getBlock());
            meterEntity.setResourceId(resourceId);
            meterEntity.setResourceName(resourceName);
            meterEntity.setFeeItemId(feeItemId);
            meterEntity.setResourceType("house");
            meterEntity.setCurrentIndex(meter.getCurrentIndex());
            meterEntity.setCurrentIndexDate(currentDate);
            meterEntity.setLastIndex(meter.getLastIndex());
            meterEntity.setLastIndexDate(lastDate);
            meterEntity.setFeeItemName(meter.getFeeItemName());
            meterEntity.setLoss(meter.getLoss());
            meterEntity.setMultiple(meter.getMultiple());
            meterEntity.setResult(FeeCalculationUtil.getMeterNum(meterEntity.getLastIndex(),
                meterEntity.getCurrentIndex(), meterEntity.getMultiple(), meterEntity.getLoss()));
            meterList.add(meterEntity);
        }

        // 查询所有的已有抄表数据，已经存在的覆盖，不存在的新增
        QueryWrapper<PaymentMeterEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(PaymentMeterEntity::getEnabledMark, "1");
        List<PaymentMeterEntity> oldList = this.list(queryWrapper);
        List<PaymentMeterEntity> updateList = new ArrayList<PaymentMeterEntity>();
        List<PaymentMeterEntity> insertList = new ArrayList<PaymentMeterEntity>();

        for (PaymentMeterEntity i : meterList) {
            boolean isUpdate = false;
            for (PaymentMeterEntity o : oldList) {
                // 同一个费用，同一个资源 仅能存在一个抄表记录
                if (i.getResourceName().equals(o.getResourceName()) && i.getFeeItemId().equals(o.getFeeItemId())) {
                    isUpdate = true;
                    i.setId(o.getId());
                }
            }
            if (isUpdate) {
                updateList.add(i);
            } else {
                insertList.add(i);
            }
        }

        this.saveBatch(insertList);
        this.updateBatchById(updateList);
        return meterList.size();
    }

    @Override
    public void updateById(String id, @Valid PaymentMeterUpForm paymentMeterUpForm) {
        PaymentMeterEntity entity = this.getInfo(id);
        if (entity != null) {
            if (!ValidateUtil.Posttive_float(paymentMeterUpForm.getCurrentIndex())
                && !ValidateUtil.PositiveNumber(paymentMeterUpForm.getCurrentIndex())) {
                throw new DataException("本期度数格式不正确");
            }
            if (!ValidateUtil.Posttive_float(paymentMeterUpForm.getLastIndex())
                && !ValidateUtil.PositiveNumber(paymentMeterUpForm.getLastIndex())) {
                throw new DataException("上期度数格式不正确");
            }
            if (paymentMeterUpForm.getLastIndexDate() != null && paymentMeterUpForm.getLastIndexDate() > 0) {
                Date lastIndexDate = new Date(paymentMeterUpForm.getLastIndexDate());
                entity.setLastIndexDate(lastIndexDate);
            }

            if (!ValidateUtil.Posttive_float(paymentMeterUpForm.getMultiple())
                && !ValidateUtil.PositiveNumber(paymentMeterUpForm.getMultiple())) {
                throw new DataException("倍率格式不正确");
            }

            if (!ValidateUtil.Posttive_float(paymentMeterUpForm.getLoss())
                && !ValidateUtil.PositiveNumber(paymentMeterUpForm.getLoss())) {
                throw new DataException("损耗格式不正确");
            }

            if (CalculationUtil.compareTo(paymentMeterUpForm.getCurrentIndex(), paymentMeterUpForm.getLastIndex(), 2,
                0) < 0) {
                throw new DataException("本期度数不能小于上期度数");
            }
            entity.setMultiple(paymentMeterUpForm.getMultiple());
            entity.setLoss(paymentMeterUpForm.getLoss());
            entity.setCurrentIndex(paymentMeterUpForm.getCurrentIndex());
            entity.setCurrentIndexDate(new Date(paymentMeterUpForm.getCurrentIndexDate()));
            entity.setLastIndex(paymentMeterUpForm.getLastIndex());
            entity.setResult(FeeCalculationUtil.getMeterNum(entity.getLastIndex(), entity.getCurrentIndex(),
                entity.getMultiple(), entity.getLoss()));
            this.updateById(entity);
        } else {
            throw new DataException("更新失败");
        }
    }

    @Override
    public void updateIndex(PaymentMeterEntity entity) {
        PaymentMeterIndexEntity index = JsonUtil.getJsonToBean(entity, PaymentMeterIndexEntity.class);
        paymentMeterIndexService.createOrUpdateIndex(index);
        this.delete(entity);
    }

    @Override
    public List<PaymentMeterEntity> getByFeeId(String feeItemId) {
        QueryWrapper<PaymentMeterEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().and(t -> t.eq(PaymentMeterEntity::getFeeItemId, feeItemId));
        return this.list(queryWrapper);
    }
}