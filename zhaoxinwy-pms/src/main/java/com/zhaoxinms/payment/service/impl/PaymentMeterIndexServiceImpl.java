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

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhaoxinms.base.util.StringUtil;
import com.zhaoxinms.base.util.UserProvider;
import com.zhaoxinms.payment.entity.PaymentMeterIndexEntity;
import com.zhaoxinms.payment.mapper.PaymentMeterIndexMapper;
import com.zhaoxinms.payment.model.paymentmeterindex.PaymentMeterIndexPagination;
import com.zhaoxinms.payment.service.PaymentMeterIndexService;
import com.zhaoxinms.util.InputCheckUtil;

/**
 *
 * payment_meter_index 版本： V3.1.0 版权： 作者： CYCBERFORM 日期： 2021-09-26 15:51:42
 */
@Service

public class PaymentMeterIndexServiceImpl extends ServiceImpl<PaymentMeterIndexMapper, PaymentMeterIndexEntity>
    implements PaymentMeterIndexService {

    @Autowired
    private UserProvider userProvider;

    @Override
    public List<PaymentMeterIndexEntity> getList(PaymentMeterIndexPagination paymentMeterIndexPagination) {
        String userId = "" + userProvider.get().getUserId();
        QueryWrapper<PaymentMeterIndexEntity> queryWrapper = new QueryWrapper<>();
        if (InputCheckUtil.isNotEmpty(paymentMeterIndexPagination.getResourceId())) {
            queryWrapper.lambda()
                .and(t -> t.eq(PaymentMeterIndexEntity::getResourceId, paymentMeterIndexPagination.getResourceId()));
        }

        if (InputCheckUtil.isNotEmpty(paymentMeterIndexPagination.getResourceName())) {
            queryWrapper.lambda().and(
                t -> t.eq(PaymentMeterIndexEntity::getResourceName, paymentMeterIndexPagination.getResourceName()));
        }

        if (InputCheckUtil.isNotEmpty(paymentMeterIndexPagination.getFeeItemId())) {
            queryWrapper.lambda()
                .and(t -> t.eq(PaymentMeterIndexEntity::getFeeItemId, paymentMeterIndexPagination.getFeeItemId()));
        }

        if (InputCheckUtil.isNotEmpty(paymentMeterIndexPagination.getCurrentIndex())) {
            queryWrapper.lambda().and(
                t -> t.eq(PaymentMeterIndexEntity::getCurrentIndex, paymentMeterIndexPagination.getCurrentIndex()));
        }

        if (InputCheckUtil.isNotEmpty(paymentMeterIndexPagination.getCurrentIndexDate())) {
            queryWrapper.lambda().and(t -> t.eq(PaymentMeterIndexEntity::getCurrentIndexDate,
                paymentMeterIndexPagination.getCurrentIndexDate()));
        }

        if (StringUtil.isEmpty(paymentMeterIndexPagination.getSidx())) {
            queryWrapper.lambda().orderByDesc(PaymentMeterIndexEntity::getId);
        } else {
            queryWrapper = "asc".equals(paymentMeterIndexPagination.getSort().toLowerCase())
                ? queryWrapper.orderByAsc(paymentMeterIndexPagination.getSidx())
                : queryWrapper.orderByDesc(paymentMeterIndexPagination.getSidx());
        }
        Page<PaymentMeterIndexEntity> page =
            new Page<>(paymentMeterIndexPagination.getCurrentPage(), paymentMeterIndexPagination.getPageSize());
        IPage<PaymentMeterIndexEntity> userIPage = this.page(page, queryWrapper);
        return paymentMeterIndexPagination.setData(userIPage.getRecords(), userIPage.getTotal());
    }

    @Override
    public List<PaymentMeterIndexEntity> getTypeList(PaymentMeterIndexPagination paymentMeterIndexPagination,
        String dataType) {
        QueryWrapper<PaymentMeterIndexEntity> queryWrapper = new QueryWrapper<>();
        if (InputCheckUtil.isNotEmpty(paymentMeterIndexPagination.getResourceId())) {
            queryWrapper.lambda()
                .and(t -> t.eq(PaymentMeterIndexEntity::getResourceId, paymentMeterIndexPagination.getResourceId()));
        }

        if (InputCheckUtil.isNotEmpty(paymentMeterIndexPagination.getResourceName())) {
            queryWrapper.lambda().and(
                t -> t.eq(PaymentMeterIndexEntity::getResourceName, paymentMeterIndexPagination.getResourceName()));
        }

        if (InputCheckUtil.isNotEmpty(paymentMeterIndexPagination.getFeeItemId())) {
            queryWrapper.lambda()
                .and(t -> t.eq(PaymentMeterIndexEntity::getFeeItemId, paymentMeterIndexPagination.getFeeItemId()));
        }

        if (InputCheckUtil.isNotEmpty(paymentMeterIndexPagination.getCurrentIndex())) {
            queryWrapper.lambda().and(
                t -> t.eq(PaymentMeterIndexEntity::getCurrentIndex, paymentMeterIndexPagination.getCurrentIndex()));
        }

        if (InputCheckUtil.isNotEmpty(paymentMeterIndexPagination.getCurrentIndexDate())) {
            queryWrapper.lambda().and(t -> t.eq(PaymentMeterIndexEntity::getCurrentIndexDate,
                paymentMeterIndexPagination.getCurrentIndexDate()));
        }

        if (StringUtil.isEmpty(paymentMeterIndexPagination.getSidx())) {
            queryWrapper.lambda().orderByDesc(PaymentMeterIndexEntity::getId);
        } else {
            queryWrapper = "asc".equals(paymentMeterIndexPagination.getSort().toLowerCase())
                ? queryWrapper.orderByAsc(paymentMeterIndexPagination.getSidx())
                : queryWrapper.orderByDesc(paymentMeterIndexPagination.getSidx());
        }
        if ("0".equals(dataType)) {
            Page<PaymentMeterIndexEntity> page =
                new Page<>(paymentMeterIndexPagination.getCurrentPage(), paymentMeterIndexPagination.getPageSize());
            IPage<PaymentMeterIndexEntity> userIPage = this.page(page, queryWrapper);
            return paymentMeterIndexPagination.setData(userIPage.getRecords(), userIPage.getTotal());
        } else {
            return this.list(queryWrapper);
        }
    }

    @Override
    public PaymentMeterIndexEntity getInfo(String id) {
        QueryWrapper<PaymentMeterIndexEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(PaymentMeterIndexEntity::getId, id);
        return this.getOne(queryWrapper);
    }

    @Override
    public void createOrUpdateIndex(PaymentMeterIndexEntity entity) {
        // 同一个resourceId,同一个feeItemId下 只有一条记录
        QueryWrapper<PaymentMeterIndexEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(PaymentMeterIndexEntity::getResourceId, entity.getResourceId());
        queryWrapper.lambda().eq(PaymentMeterIndexEntity::getFeeItemId, entity.getFeeItemId());
        PaymentMeterIndexEntity searchResult = this.getOne(queryWrapper);

        if (searchResult == null) {
            this.save(entity);
        } else {
            searchResult.setCurrentIndex(entity.getCurrentIndex());
            searchResult.setCurrentIndexDate(entity.getCurrentIndexDate());
            this.updateById(searchResult);
        }
    }

    @Override
    public boolean update(String id, PaymentMeterIndexEntity entity) {
        entity.setId(id);
        return this.updateById(entity);
    }

    @Override
    public void delete(PaymentMeterIndexEntity entity) {
        if (entity != null) {
            this.removeById(entity.getId());
        }
    }
}