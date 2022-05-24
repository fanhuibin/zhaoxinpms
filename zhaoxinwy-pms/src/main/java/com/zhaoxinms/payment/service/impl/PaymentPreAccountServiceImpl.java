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
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhaoxinms.base.exception.DataException;
import com.zhaoxinms.base.util.StringUtil;
import com.zhaoxinms.base.util.UserProvider;
import com.zhaoxinms.baseconfig.entity.ConfigHouseEntity;
import com.zhaoxinms.baseconfig.service.ConfigHouseService;
import com.zhaoxinms.event.ContractEvent;
import com.zhaoxinms.payment.entity.PaymentPreAccountEntity;
import com.zhaoxinms.payment.mapper.PaymentPreAccountMapper;
import com.zhaoxinms.payment.model.paymentpreaccount.PaymentPreAccountPagination;
import com.zhaoxinms.payment.service.PaymentPreAccountService;
import com.zhaoxinms.util.InputCheckUtil;

/**
 *
 * payment_pre_account 版本： V3.1.0 版权： 作者： CYCBERFORM 日期： 2021-10-13 16:51:26
 */
@Service

public class PaymentPreAccountServiceImpl extends ServiceImpl<PaymentPreAccountMapper, PaymentPreAccountEntity>
    implements PaymentPreAccountService {

    @Autowired
    private UserProvider userProvider;
    @Autowired
    private ConfigHouseService configHouseService;

    @Override
    public List<PaymentPreAccountEntity> getList(PaymentPreAccountPagination paymentPreAccountPagination) {
        String userId = "" + userProvider.get().getUserId();
        QueryWrapper<PaymentPreAccountEntity> queryWrapper = new QueryWrapper<>();
        if (InputCheckUtil.isNotEmpty(paymentPreAccountPagination.getResourceId())) {
            queryWrapper.lambda()
                .and(t -> t.eq(PaymentPreAccountEntity::getResourceId, paymentPreAccountPagination.getResourceId()));
        }
        if (InputCheckUtil.isNotEmpty(paymentPreAccountPagination.getResourceName())) {
            queryWrapper.lambda().and(
                t -> t.eq(PaymentPreAccountEntity::getResourceName, paymentPreAccountPagination.getResourceName()));
        }

        if (InputCheckUtil.isNotEmpty(paymentPreAccountPagination.getBlock())) {
            queryWrapper.lambda()
                .and(t -> t.eq(PaymentPreAccountEntity::getBlock, paymentPreAccountPagination.getBlock()));
        }

        if (InputCheckUtil.isNotEmpty(paymentPreAccountPagination.getFeeItemId())) {
            queryWrapper.lambda()
                .and(t -> t.eq(PaymentPreAccountEntity::getFeeItemId, paymentPreAccountPagination.getFeeItemId()));
        }

        if (InputCheckUtil.isNotEmpty(paymentPreAccountPagination.getAmt())) {
            queryWrapper.lambda().and(t -> t.eq(PaymentPreAccountEntity::getAmt, paymentPreAccountPagination.getAmt()));
        }

        if (StringUtil.isEmpty(paymentPreAccountPagination.getSidx())) {
            queryWrapper.lambda().orderByDesc(PaymentPreAccountEntity::getId);
        } else {
            queryWrapper = "asc".equals(paymentPreAccountPagination.getSort().toLowerCase())
                ? queryWrapper.orderByAsc(paymentPreAccountPagination.getSidx())
                : queryWrapper.orderByDesc(paymentPreAccountPagination.getSidx());
        }
        Page<PaymentPreAccountEntity> page =
            new Page<>(paymentPreAccountPagination.getCurrentPage(), paymentPreAccountPagination.getPageSize());
        IPage<PaymentPreAccountEntity> userIPage = this.page(page, queryWrapper);
        return paymentPreAccountPagination.setData(userIPage.getRecords(), userIPage.getTotal());
    }

    @Override
    public List<PaymentPreAccountEntity> getTypeList(PaymentPreAccountPagination paymentPreAccountPagination,
        String dataType) {
        QueryWrapper<PaymentPreAccountEntity> queryWrapper = new QueryWrapper<>();
        if (InputCheckUtil.isNotEmpty(paymentPreAccountPagination.getResourceId())) {
            queryWrapper.lambda()
                .and(t -> t.eq(PaymentPreAccountEntity::getResourceId, paymentPreAccountPagination.getResourceId()));
        }

        if (InputCheckUtil.isNotEmpty(paymentPreAccountPagination.getBlock())) {
            queryWrapper.lambda()
                .and(t -> t.eq(PaymentPreAccountEntity::getBlock, paymentPreAccountPagination.getBlock()));
        }

        if (InputCheckUtil.isNotEmpty(paymentPreAccountPagination.getFeeItemId())) {
            queryWrapper.lambda()
                .and(t -> t.eq(PaymentPreAccountEntity::getFeeItemId, paymentPreAccountPagination.getFeeItemId()));
        }

        if (InputCheckUtil.isNotEmpty(paymentPreAccountPagination.getAmt())) {
            queryWrapper.lambda().and(t -> t.eq(PaymentPreAccountEntity::getAmt, paymentPreAccountPagination.getAmt()));
        }

        if (StringUtil.isEmpty(paymentPreAccountPagination.getSidx())) {
            queryWrapper.lambda().orderByDesc(PaymentPreAccountEntity::getId);
        } else {
            queryWrapper = "asc".equals(paymentPreAccountPagination.getSort().toLowerCase())
                ? queryWrapper.orderByAsc(paymentPreAccountPagination.getSidx())
                : queryWrapper.orderByDesc(paymentPreAccountPagination.getSidx());
        }
        if ("0".equals(dataType)) {
            Page<PaymentPreAccountEntity> page =
                new Page<>(paymentPreAccountPagination.getCurrentPage(), paymentPreAccountPagination.getPageSize());
            IPage<PaymentPreAccountEntity> userIPage = this.page(page, queryWrapper);
            return paymentPreAccountPagination.setData(userIPage.getRecords(), userIPage.getTotal());
        } else {
            return this.list(queryWrapper);
        }
    }

    @Override
    public PaymentPreAccountEntity getInfo(String id) {
        QueryWrapper<PaymentPreAccountEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(PaymentPreAccountEntity::getId, id);
        return this.getOne(queryWrapper);
    }

    @Override
    public void create(PaymentPreAccountEntity entity) {
        this.save(entity);
    }

    /**
     * 查询某个商铺下的所有预存款信息
     * 
     * @return
     */
    @Override
    public List<PaymentPreAccountEntity> getAccountsByResourceId(String resourceId) {
        if (StringUtils.isEmpty(resourceId)) {
            throw new DataException("resourceId不能为空");
        }
        QueryWrapper<PaymentPreAccountEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().and(t -> t.eq(PaymentPreAccountEntity::getResourceId, resourceId));
        return this.list(queryWrapper);
    }

    /**
     * 扣费
     */
    @Override
    @Transactional(readOnly = false)
    public void charge(String resourceId, String feeItemId, String chargeAcmount) {
        QueryWrapper<PaymentPreAccountEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().and(t -> t.eq(PaymentPreAccountEntity::getResourceId, resourceId));
        if (!StringUtils.isEmpty(feeItemId)) {
            queryWrapper.lambda().and(t -> t.eq(PaymentPreAccountEntity::getFeeItemId, feeItemId));
            queryWrapper.lambda().and(t -> t.eq(PaymentPreAccountEntity::getUseFeeItem, "1"));
        } else {
            queryWrapper.lambda().and(t -> t.eq(PaymentPreAccountEntity::getUseFeeItem, "0"));
        }

        PaymentPreAccountEntity account = this.getOne(queryWrapper);

        int result = 0;
        // 会员是否有效
        if (account != null) {
            String accountBalance = account.getAmt();
            BigDecimal ab = new BigDecimal(accountBalance);
            BigDecimal ca = new BigDecimal(chargeAcmount);
            if (ab.compareTo(ca) < 0) {
                throw new DataException("账户的余额不足");
            } ;
            result = this.baseMapper.charge(resourceId, feeItemId, chargeAcmount);
            if (result <= 0) {
                throw new DataException("修改会员余额失败！");
            }
        } else {
            throw new DataException("查找账户信息失败！");
        }
    }

    /**
     * 预存
     */
    @Override
    @Transactional(readOnly = false)
    public void recharge(String resourceId, String feeItemId, String rechargeAmount) {
        QueryWrapper<PaymentPreAccountEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().and(t -> t.eq(PaymentPreAccountEntity::getResourceId, resourceId));
        if (!StringUtils.isEmpty(feeItemId)) {
            queryWrapper.lambda().and(t -> t.eq(PaymentPreAccountEntity::getFeeItemId, feeItemId));
            queryWrapper.lambda().and(t -> t.eq(PaymentPreAccountEntity::getUseFeeItem, "1"));
        } else {
            queryWrapper.lambda().and(t -> t.eq(PaymentPreAccountEntity::getUseFeeItem, "0"));
        }
        PaymentPreAccountEntity account = this.getOne(queryWrapper);

        // 账号不存在则创建账号
        if (account == null) {
            ConfigHouseEntity house = configHouseService.getById(resourceId);

            PaymentPreAccountEntity entity = new PaymentPreAccountEntity();
            entity.setAmt(rechargeAmount);
            entity.setResourceId(house.getId());
            entity.setResourceName(house.getName());
            entity.setBlock(house.getBlock());
            if (StringUtils.isEmpty(feeItemId)) {
                entity.setFeeItemId("");
                entity.setUseFeeItem("0");
            } else {
                entity.setFeeItemId(feeItemId);
                entity.setUseFeeItem("1");
            }
            this.save(entity);
        } else {
            int result = 0;
            String accountBalance = account.getAmt();
            BigDecimal ab = new BigDecimal(accountBalance);
            BigDecimal ra = new BigDecimal(rechargeAmount);
            result = this.baseMapper.recharge(resourceId, feeItemId, rechargeAmount);
            if (result <= 0) {
                throw new DataException("预存费用失败！");
            }
        }
    }

    @EventListener
    public void deleteAccount(ContractEvent event) {
        if (event.getState().equals(ContractEvent.STATE_CANCEL)) {
            String resourceId = event.getContract().getResourceId();
            List<PaymentPreAccountEntity> accounts = this.getAccountsByResourceId(resourceId);
            for (PaymentPreAccountEntity account : accounts) {
                if (new BigDecimal(account.getAmt()).compareTo(BigDecimal.ZERO) != 0) {
                    throw new DataException("该商铺下还有未使用的预存款，请先执行退款操作");
                }
            }
        }
    }
}