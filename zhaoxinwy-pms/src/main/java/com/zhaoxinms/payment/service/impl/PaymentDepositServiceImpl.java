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

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhaoxinms.base.exception.DataException;
import com.zhaoxinms.base.util.StringUtil;
import com.zhaoxinms.base.util.UserProvider;
import com.zhaoxinms.baseconfig.entity.ConfigFeeItemEntity;
import com.zhaoxinms.baseconfig.service.ConfigFeeItemService;
import com.zhaoxinms.payment.entity.PaymentDepositEntity;
import com.zhaoxinms.payment.entity.PaymentPayLogEntity;
import com.zhaoxinms.payment.entity.PaymentTempEntity;
import com.zhaoxinms.payment.mapper.PaymentDepositMapper;
import com.zhaoxinms.payment.model.paymentdeposit.PaymentDepositPagination;
import com.zhaoxinms.payment.model.paymentdeposit.PaymentDepositRefundForm;
import com.zhaoxinms.payment.service.PaymentDepositService;
import com.zhaoxinms.payment.service.PaymentPayLogService;
import com.zhaoxinms.util.ConstantsUtil;
import com.zhaoxinms.util.InputCheckUtil;
import com.zhaoxinms.util.ValidateUtil;

/**
 *
 * payment_deposit 版本： V3.1.0 版权： 作者： CYCBERFORM 日期： 2021-09-29 17:54:49
 */
@Service

public class PaymentDepositServiceImpl extends ServiceImpl<PaymentDepositMapper, PaymentDepositEntity>
    implements PaymentDepositService {

    @Autowired
    private UserProvider userProvider;
    @Autowired
    private ConfigFeeItemService configFeeItemService;
    @Autowired
    private PaymentPayLogService paymentPayLogService;

    @Override
    public List<PaymentDepositEntity> getList(PaymentDepositPagination paymentDepositPagination) {
        String userId = "" + userProvider.get().getUserId();
        QueryWrapper<PaymentDepositEntity> queryWrapper = new QueryWrapper<>();
        if (InputCheckUtil.isNotEmpty(paymentDepositPagination.getBlock())) {
            queryWrapper.lambda().and(t -> t.eq(PaymentDepositEntity::getBlock, paymentDepositPagination.getBlock()));
        }

        if (InputCheckUtil.isNotEmpty(paymentDepositPagination.getResourceName())) {
            queryWrapper.lambda()
                .and(t -> t.eq(PaymentDepositEntity::getResourceName, paymentDepositPagination.getResourceName()));
        }

        if (InputCheckUtil.isNotEmpty(paymentDepositPagination.getFeeUser())) {
            queryWrapper.lambda()
                .and(t -> t.eq(PaymentDepositEntity::getFeeUser, paymentDepositPagination.getFeeUser()));
        }

        if (InputCheckUtil.isNotEmpty(paymentDepositPagination.getFeeItemId())) {
            queryWrapper.lambda()
                .and(t -> t.eq(PaymentDepositEntity::getFeeItemId, paymentDepositPagination.getFeeItemId()));
        }

        if (InputCheckUtil.isNotEmpty(paymentDepositPagination.getPayType())) {
            queryWrapper.lambda()
                .and(t -> t.eq(PaymentDepositEntity::getPayType, paymentDepositPagination.getPayType()));
        }

        if (InputCheckUtil.isNotEmpty(paymentDepositPagination.getState())) {
            queryWrapper.lambda().and(t -> t.eq(PaymentDepositEntity::getState, paymentDepositPagination.getState()));
        }

        // 排序
        if (StringUtil.isEmpty(paymentDepositPagination.getSidx())) {
            queryWrapper.lambda().orderByDesc(PaymentDepositEntity::getId);
        } else {
            queryWrapper = "asc".equals(paymentDepositPagination.getSort().toLowerCase())
                ? queryWrapper.orderByAsc(paymentDepositPagination.getSidx())
                : queryWrapper.orderByDesc(paymentDepositPagination.getSidx());
        }
        Page<PaymentDepositEntity> page =
            new Page<>(paymentDepositPagination.getCurrentPage(), paymentDepositPagination.getPageSize());
        IPage<PaymentDepositEntity> userIPage = this.page(page, queryWrapper);
        return paymentDepositPagination.setData(userIPage.getRecords(), userIPage.getTotal());
    }

    @Override
    public List<PaymentDepositEntity> getTypeList(PaymentDepositPagination paymentDepositPagination, String dataType) {
        QueryWrapper<PaymentDepositEntity> queryWrapper = new QueryWrapper<>();
        if (InputCheckUtil.isNotEmpty(paymentDepositPagination.getBlock())) {
            queryWrapper.lambda().and(t -> t.eq(PaymentDepositEntity::getBlock, paymentDepositPagination.getBlock()));
        }

        if (InputCheckUtil.isNotEmpty(paymentDepositPagination.getResourceName())) {
            queryWrapper.lambda()
                .and(t -> t.eq(PaymentDepositEntity::getResourceName, paymentDepositPagination.getResourceName()));
        }

        if (InputCheckUtil.isNotEmpty(paymentDepositPagination.getFeeUser())) {
            queryWrapper.lambda()
                .and(t -> t.eq(PaymentDepositEntity::getFeeUser, paymentDepositPagination.getFeeUser()));
        }

        if (InputCheckUtil.isNotEmpty(paymentDepositPagination.getFeeItemId())) {
            queryWrapper.lambda()
                .and(t -> t.eq(PaymentDepositEntity::getFeeItemId, paymentDepositPagination.getFeeItemId()));
        }

        if (InputCheckUtil.isNotEmpty(paymentDepositPagination.getPayType())) {
            queryWrapper.lambda()
                .and(t -> t.eq(PaymentDepositEntity::getPayType, paymentDepositPagination.getPayType()));
        }

        if (InputCheckUtil.isNotEmpty(paymentDepositPagination.getState())) {
            queryWrapper.lambda().and(t -> t.eq(PaymentDepositEntity::getState, paymentDepositPagination.getState()));
        }

        // 排序
        if (StringUtil.isEmpty(paymentDepositPagination.getSidx())) {
            queryWrapper.lambda().orderByDesc(PaymentDepositEntity::getId);
        } else {
            queryWrapper = "asc".equals(paymentDepositPagination.getSort().toLowerCase())
                ? queryWrapper.orderByAsc(paymentDepositPagination.getSidx())
                : queryWrapper.orderByDesc(paymentDepositPagination.getSidx());
        }
        if ("0".equals(dataType)) {
            Page<PaymentDepositEntity> page =
                new Page<>(paymentDepositPagination.getCurrentPage(), paymentDepositPagination.getPageSize());
            IPage<PaymentDepositEntity> userIPage = this.page(page, queryWrapper);
            return paymentDepositPagination.setData(userIPage.getRecords(), userIPage.getTotal());
        } else {
            return this.list(queryWrapper);
        }
    }

    @Override
    public PaymentDepositEntity getInfo(String id) {
        QueryWrapper<PaymentDepositEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(PaymentDepositEntity::getId, id);
        return this.getOne(queryWrapper);
    }

    @Override
    public void create(PaymentDepositEntity entity) {
        if (!ValidateUtil.PositiveFloatOrNum(entity.getAmt())) {
            throw new DataException("收费金额格式不正确");
        }
        if (StringUtils.isEmpty(entity.getResourceId())) {
            entity.setResourceId("");
            entity.setResourceName("");
            entity.setBlock("");
        }
        if (StringUtils.isNotEmpty(entity.getResourceName())) {
            if (StringUtils.isEmpty(entity.getResourceId())) {
                throw new DataException("商铺信息不能为空");
            }
            if (StringUtils.isEmpty("block")) {
                throw new DataException("商业区信息不能为空");
            }
        }

        String userId = "" + userProvider.get().getUserId();
        ConfigFeeItemEntity item = configFeeItemService.getById(entity.getFeeItemId());
        entity.setFeeItemName(item.getName());
        entity.setOperateUser(userId);
        entity.setState(ConstantsUtil.DEPOSIT_STATE_PAIED);
        PaymentPayLogEntity payLog = paymentPayLogService.createDepositPayLog(entity, entity.getPayType());
        entity.setPayNo(payLog.getPayNo());

        this.save(entity);
    }

    @Override
    public boolean refund(String id, PaymentDepositRefundForm form) {
        // 判断当前的状态，只有未退款状态的才能退款
        PaymentDepositEntity deposit = this.getById(id);
        if (deposit == null || StringUtils.isEmpty(deposit.getId())) {
            throw new DataException("查询押金付款信息失败");
        }
        if (!deposit.getState().equals(ConstantsUtil.DEPOSIT_STATE_PAIED)) {
            throw new DataException("该押金不是未退款状态，退款失败");
        }
        if (form.getRefundTime() < deposit.getOperateTime().getTime()) {
            throw new DataException("退款时间不能小于收款时间");
        }

        String userId = "" + userProvider.get().getUserId();
        deposit.setRefundUser(userId);
        deposit.setRefundType(form.getRefundType());
        deposit.setRefundTime(new Date(form.getRefundTime()));
        deposit.setRemark(form.getRemark());
        deposit.setState(ConstantsUtil.DEPOSIT_STATE_REFUND);
        PaymentPayLogEntity payLog = paymentPayLogService.createDepositRefundLog(deposit, deposit.getRefundType());
        deposit.setRefundNo(payLog.getPayNo());

        return this.updateById(deposit);
    }

    @Override
    public void delete(PaymentDepositEntity entity) {
        if (entity != null) {
            this.removeById(entity.getId());
        }
    }

    @Override
    public PaymentDepositEntity getByPayNo(String payNo) {
        QueryWrapper<PaymentDepositEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().and(t -> t.eq(PaymentDepositEntity::getPayNo, payNo));
        return this.getOne(queryWrapper);
    }
    
    @Override
    public PaymentDepositEntity getByRefundNo(String refundNo) {
        QueryWrapper<PaymentDepositEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().and(t -> t.eq(PaymentDepositEntity::getRefundNo, refundNo));
        return this.getOne(queryWrapper);
    }
}