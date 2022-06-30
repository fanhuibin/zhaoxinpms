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

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhaoxinms.base.exception.DataException;
import com.zhaoxinms.base.service.BillRuleService;
import com.zhaoxinms.base.util.StringUtil;
import com.zhaoxinms.base.util.UserProvider;
import com.zhaoxinms.baseconfig.entity.ConfigFeeItemEntity;
import com.zhaoxinms.baseconfig.service.ConfigFeeItemService;
import com.zhaoxinms.payment.entity.PaymentDepositEntity;
import com.zhaoxinms.payment.entity.PaymentPreAccountEntity;
import com.zhaoxinms.payment.entity.PaymentPreEntity;
import com.zhaoxinms.payment.mapper.PaymentPreMapper;
import com.zhaoxinms.payment.model.paymentpre.PaymentPrePagination;
import com.zhaoxinms.payment.model.paymentpre.PaymentPreRefundForm;
import com.zhaoxinms.payment.service.PaymentPayLogService;
import com.zhaoxinms.payment.service.PaymentPreAccountService;
import com.zhaoxinms.payment.service.PaymentPreService;
import com.zhaoxinms.util.CalculationUtil;
import com.zhaoxinms.util.ConstantsUtil;
import com.zhaoxinms.util.InputCheckUtil;
import com.zhaoxinms.util.ValidateUtil;

/**
 *
 * payment_pre 版本： V3.1.0 版权： 作者： CYCBERFORM 日期： 2021-10-13 18:27:17
 */
@Service

public class PaymentPreServiceImpl extends ServiceImpl<PaymentPreMapper, PaymentPreEntity>
    implements PaymentPreService {

    @Autowired
    private UserProvider userProvider;
    @Autowired
    private PaymentPreAccountService paymentPreAccountService;
    @Autowired
    private ConfigFeeItemService configFeeItemService;
    @Autowired
    private BillRuleService billRuleService;
    @Autowired
    private PaymentPayLogService paymentPayLogService;

    @Override
    public List<PaymentPreEntity> getList(PaymentPrePagination paymentPrePagination) {
        String userId = "" + userProvider.get().getUserId();
        QueryWrapper<PaymentPreEntity> queryWrapper = new QueryWrapper<>();
        if (InputCheckUtil.isNotEmpty((String.valueOf(paymentPrePagination.getResourceName())))) {
            queryWrapper.lambda()
                .and(t -> t.eq(PaymentPreEntity::getResourceName, paymentPrePagination.getResourceName()));
        }

        if (InputCheckUtil.isNotEmpty((paymentPrePagination.getResourceId()))) {
            queryWrapper.lambda().and(t -> t.eq(PaymentPreEntity::getResourceId, paymentPrePagination.getResourceId()));
        }

        if (InputCheckUtil.isNotEmpty((paymentPrePagination.getBlock()))) {
            queryWrapper.lambda().and(t -> t.eq(PaymentPreEntity::getBlock, paymentPrePagination.getBlock()));
        }

        if (InputCheckUtil.isNotEmpty((paymentPrePagination.getFeeItemId()))) {
            queryWrapper.lambda().and(t -> t.eq(PaymentPreEntity::getFeeItemId, paymentPrePagination.getFeeItemId()));
        }

        if (InputCheckUtil.isNotEmpty((paymentPrePagination.getPayType()))) {
            queryWrapper.lambda().and(t -> t.eq(PaymentPreEntity::getPayType, paymentPrePagination.getPayType()));
        }

        if (InputCheckUtil.isNotEmpty((paymentPrePagination.getType()))) {
            queryWrapper.lambda().and(t -> t.eq(PaymentPreEntity::getType, paymentPrePagination.getType()));
        }

        if (StringUtil.isEmpty(paymentPrePagination.getSidx())) {
            queryWrapper.lambda().orderByDesc(PaymentPreEntity::getId);
        } else {
            queryWrapper = "asc".equals(paymentPrePagination.getSort().toLowerCase())
                ? queryWrapper.orderByAsc(paymentPrePagination.getSidx())
                : queryWrapper.orderByDesc(paymentPrePagination.getSidx());
        }
        Page<PaymentPreEntity> page =
            new Page<>(paymentPrePagination.getCurrentPage(), paymentPrePagination.getPageSize());
        IPage<PaymentPreEntity> userIPage = this.page(page, queryWrapper);
        return paymentPrePagination.setData(userIPage.getRecords(), userIPage.getTotal());
    }

    @Override
    public List<PaymentPreEntity> getTypeList(PaymentPrePagination paymentPrePagination, String dataType) {
        QueryWrapper<PaymentPreEntity> queryWrapper = new QueryWrapper<>();

        if (InputCheckUtil.isNotEmpty((paymentPrePagination.getResourceName()))) {
            queryWrapper.lambda()
                .and(t -> t.eq(PaymentPreEntity::getResourceName, paymentPrePagination.getResourceName()));
        }

        if (InputCheckUtil.isNotEmpty((paymentPrePagination.getResourceId()))) {
            queryWrapper.lambda().and(t -> t.eq(PaymentPreEntity::getResourceId, paymentPrePagination.getResourceId()));
        }

        if (InputCheckUtil.isNotEmpty((paymentPrePagination.getBlock()))) {
            queryWrapper.lambda().and(t -> t.eq(PaymentPreEntity::getBlock, paymentPrePagination.getBlock()));
        }

        if (InputCheckUtil.isNotEmpty((paymentPrePagination.getFeeItemId()))) {
            queryWrapper.lambda().and(t -> t.eq(PaymentPreEntity::getFeeItemId, paymentPrePagination.getFeeItemId()));
        }

        if (InputCheckUtil.isNotEmpty((paymentPrePagination.getPayType()))) {
            queryWrapper.lambda().and(t -> t.eq(PaymentPreEntity::getPayType, paymentPrePagination.getPayType()));
        }

        if (InputCheckUtil.isNotEmpty((paymentPrePagination.getType()))) {
            queryWrapper.lambda().and(t -> t.eq(PaymentPreEntity::getType, paymentPrePagination.getType()));
        }

        if (StringUtil.isEmpty(paymentPrePagination.getSidx())) {
            queryWrapper.lambda().orderByDesc(PaymentPreEntity::getId);
        } else {
            queryWrapper = "asc".equals(paymentPrePagination.getSort().toLowerCase())
                ? queryWrapper.orderByAsc(paymentPrePagination.getSidx())
                : queryWrapper.orderByDesc(paymentPrePagination.getSidx());
        }
        if ("0".equals(dataType)) {
            Page<PaymentPreEntity> page =
                new Page<>(paymentPrePagination.getCurrentPage(), paymentPrePagination.getPageSize());
            IPage<PaymentPreEntity> userIPage = this.page(page, queryWrapper);
            return paymentPrePagination.setData(userIPage.getRecords(), userIPage.getTotal());
        } else {
            return this.list(queryWrapper);
        }
    }

    @Override
    public PaymentPreEntity getInfo(String id) {
        QueryWrapper<PaymentPreEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(PaymentPreEntity::getId, id);
        return this.getOne(queryWrapper);
    }

    @Override
    @Transactional
    public void create(PaymentPreEntity entity, String type) {

        if (!type.equals(ConstantsUtil.PAY_PRE_TYPE_ADD) && !type.equals(ConstantsUtil.PAY_PRE_TYPE_PAYADD)) {
            throw new DataException("类型不正确");
        }
        if (!ValidateUtil.PositiveFloatOrNum(entity.getAmt())) {
            throw new DataException("预存款金额格式不正确");
        }
        if (StringUtils.isEmpty(entity.getResourceId()) || StringUtils.isEmpty(entity.getResourceName())) {
            throw new DataException("商铺信息不能为空");
        }
        if (StringUtils.isEmpty(entity.getBlock())) {
            throw new DataException("商业区信息不能为空");
        }
        if (!entity.getUseFeeItem().equals("1") && !entity.getUseFeeItem().equals("0")) {
            throw new DataException("useFeeItem格式不正确");
        }

        String userId = "" + userProvider.get().getUserId();
        if (!StringUtils.isEmpty(entity.getFeeItemId())) {
            ConfigFeeItemEntity item = configFeeItemService.getById(entity.getFeeItemId());
            entity.setFeeItemName(item.getName());
        } else {
            entity.setFeeItemName("不指定收费项");
        }
        entity.setType(type);
        entity.setOperateUser(userId);
        String payNo = billRuleService.getBillNumber("pay_no", false);
        entity.setPayNo(payNo);
        // 账户存入金额
        paymentPreAccountService.recharge(entity.getResourceId(), entity.getFeeItemId(), entity.getAmt());
        
        this.save(entity);
    }

    @Override
    @Transactional
    public void refund(PaymentPreRefundForm paymentPreRefundForm) {

        if (StringUtils.isEmpty(paymentPreRefundForm.getResourceId())
            || StringUtils.isEmpty(paymentPreRefundForm.getResourceName())
            || StringUtils.isEmpty(paymentPreRefundForm.getFeeUser())) {
            throw new DataException("请求的参数不完整");
        }
        if (StringUtils.isEmpty(paymentPreRefundForm.getFeeUser())
            || StringUtils.isEmpty(paymentPreRefundForm.getPayType())) {
            throw new DataException("请求的参数不完整");
        }
        if (paymentPreRefundForm.getRefundItems().size() == 0) {
            throw new DataException("退款的金额不能为0");
        }
        String payNo = billRuleService.getBillNumber("pay_no", false);
        List<PaymentPreAccountEntity> accounts =
            paymentPreAccountService.getAccountsByResourceId(paymentPreRefundForm.getResourceId());
        for (Map<String, String> map : paymentPreRefundForm.getRefundItems()) {
            String changeMoney = map.get("changeMoney");
            String amt = map.get("amt");
            String feeItemId = map.get("feeItemId");
            String block = map.get("block");
            String resourceId = map.get("resourceId");
            String resourceName = map.get("resourceName");

            if (new BigDecimal(map.get("amt")).compareTo(BigDecimal.ZERO) <= 0) {
                continue;
            } ;
            if (CalculationUtil.compareTo(amt, changeMoney, 2, 0) < 0) {
                throw new DataException("退款金额不能大于账户余额");
            }

            PaymentPreEntity entity = new PaymentPreEntity();
            entity.setOperateTime(new Date(paymentPreRefundForm.getOperateTime()));
            entity.setAmt("-" + changeMoney);
            entity.setBlock(block);
            entity.setFeeItemId(feeItemId);
            entity.setResourceId(resourceId);
            entity.setFeeUser(paymentPreRefundForm.getFeeUser());
            entity.setPayType(paymentPreRefundForm.getPayType());
            entity.setResourceName(resourceName);
            entity.setPayNo(payNo);
            minus(entity, ConstantsUtil.PAY_PRE_TYPE_REFUND);
        }
    }

    @Override
    public void minus(PaymentPreEntity entity, String type) {
        String userId = "" + userProvider.get().getUserId();
        if (!type.equals(ConstantsUtil.PAY_PRE_TYPE_PAY) && !type.equals(ConstantsUtil.PAY_PRE_TYPE_REFUND)) {
            throw new DataException("类型不正确");
        }

        entity.setType(type);
        if (ValidateUtil.PositiveFloatOrNum(entity.getAmt())) {
            throw new DataException("金额格式不正确");
        }
        if (StringUtils.isEmpty(entity.getResourceId()) || StringUtils.isEmpty(entity.getResourceName())) {
            throw new DataException("商铺信息不能为空");
        }
        if (StringUtils.isEmpty(entity.getBlock())) {
            throw new DataException("商业区信息不能为空");
        }
        entity.setOperateUser(userId);
        if (!StringUtils.isEmpty(entity.getFeeItemId())) {
            ConfigFeeItemEntity item = configFeeItemService.getById(entity.getFeeItemId());
            entity.setFeeItemName(item.getName());
            entity.setUseFeeItem("1");
        } else {
            entity.setFeeItemName("不指定收费项");
            entity.setUseFeeItem("0");
        }
        if (!entity.getUseFeeItem().equals("1") && !entity.getUseFeeItem().equals("0")) {
            throw new DataException("useFeeItem格式不正确");
        }
        this.save(entity);
        // 账户支出
        paymentPreAccountService.charge(entity.getResourceId(), entity.getFeeItemId(),
            "" + Math.abs(Double.valueOf(entity.getAmt())));
    }
    
    @Override
    public PaymentPreEntity getByPayNo(String payNo) {
        QueryWrapper<PaymentPreEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().and(t -> t.eq(PaymentPreEntity::getPayNo, payNo));
        return this.getOne(queryWrapper);
    }
}