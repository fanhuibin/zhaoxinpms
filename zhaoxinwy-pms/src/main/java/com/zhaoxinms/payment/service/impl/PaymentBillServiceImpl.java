/**
 * Copyright 肇新智慧物业管理系统
 *
 * Licensed under AGPL开源协议
 *
 * gitee：https://gitee.com/fanhuibin1/zhaoxinpms website：http://pms.zhaoxinms.com wx： zhaoxinms
 *
 */
package com.zhaoxinms.payment.service.impl;

import java.util.ArrayList;
import java.util.Date;
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
import com.zhaoxinms.base.util.UserProvider;
import com.zhaoxinms.baseconfig.entity.ConfigFeeItemEntity;
import com.zhaoxinms.baseconfig.service.ConfigFeeItemService;
import com.zhaoxinms.baseconfig.service.ConfigHouseService;
import com.zhaoxinms.event.ContractEvent;
import com.zhaoxinms.payment.entity.PaymentBillEntity;
import com.zhaoxinms.payment.entity.PaymentContractEntity;
import com.zhaoxinms.payment.entity.PaymentPayLogEntity;
import com.zhaoxinms.payment.mapper.PaymentBillMapper;
import com.zhaoxinms.payment.model.paymentbill.PaymentBillPagination;
import com.zhaoxinms.payment.model.paymentbill.PaymentBillRefundForm;
import com.zhaoxinms.payment.service.PaymentBillService;
import com.zhaoxinms.payment.service.PaymentPayLogService;
import com.zhaoxinms.util.CalculationUtil;
import com.zhaoxinms.util.ConstantsUtil;
import com.zhaoxinms.util.DateUtils;
import com.zhaoxinms.util.FeeCalculationUtil;
import com.zhaoxinms.util.InputCheckUtil;
import com.zhaoxinms.util.ValidateUtil;

@Service
public class PaymentBillServiceImpl extends ServiceImpl<PaymentBillMapper, PaymentBillEntity> implements PaymentBillService {
    @Autowired
    public ConfigHouseService houseService;
    @Autowired
    private UserProvider userProvider;
    @Autowired
    private PaymentPayLogService paymentPayLogService;
    @Autowired
    private ConfigFeeItemService configFeeItemService;

    @Override
    public List<PaymentBillEntity> getList(PaymentBillPagination paymentBillPagination) {
        String userId = "" + userProvider.get().getUserId();
        QueryWrapper<PaymentBillEntity> queryWrapper = new QueryWrapper<>();

        if (InputCheckUtil.isNotEmpty(paymentBillPagination.getPayBeginTime()) && InputCheckUtil.isNotEmpty(paymentBillPagination.getPayEndTime())) {
            Date beginDate = new Date(Long.valueOf(paymentBillPagination.getPayBeginTime()));
            Date endDate = new Date(Long.valueOf(paymentBillPagination.getPayBeginTime()));
            String beginDateString = DateUtils.formatDate(beginDate, "yyyy-MM-dd 00:00:00");
            String endDateString = DateUtils.formatDate(endDate, "yyyy-MM-dd 23:59:59");
            queryWrapper.lambda().and(t -> t.between(PaymentBillEntity::getPayTime, beginDateString, endDateString));
        }
        if (InputCheckUtil.isNotEmpty(paymentBillPagination.getBeginTime()) && InputCheckUtil.isNotEmpty(paymentBillPagination.getEndTime())) {
            Date beginDate = new Date(Long.valueOf(paymentBillPagination.getBeginTime()));
            Date endDate = new Date(Long.valueOf(paymentBillPagination.getEndTime()));
            String beginDateString = DateUtils.formatDate(beginDate, "yyyy-MM-dd 00:00:00");
            String endDateString = DateUtils.formatDate(endDate, "yyyy-MM-dd 23:59:59");
            queryWrapper.lambda().and(t -> t.between(PaymentBillEntity::getBeginDate, beginDateString, endDateString));
        }
        if (InputCheckUtil.isNotEmpty(paymentBillPagination.getResourceName())) {
            queryWrapper.lambda().and(t -> t.like(PaymentBillEntity::getResourceName, paymentBillPagination.getResourceName()));
        }

        if (InputCheckUtil.isNotEmpty(paymentBillPagination.getResourceId())) {
            queryWrapper.lambda().and(t -> t.eq(PaymentBillEntity::getResourceId, paymentBillPagination.getResourceId()));
        }

        if (InputCheckUtil.isNotEmpty(paymentBillPagination.getFeeItemId())) {
            queryWrapper.lambda().and(t -> t.eq(PaymentBillEntity::getFeeItemId, paymentBillPagination.getFeeItemId()));
        }

        if (InputCheckUtil.isNotEmpty(paymentBillPagination.getFeeItemName())) {
            queryWrapper.lambda().and(t -> t.like(PaymentBillEntity::getFeeItemName, paymentBillPagination.getFeeItemName()));
        }

        if (InputCheckUtil.isNotEmpty(paymentBillPagination.getPayLogNo())) {
            queryWrapper.lambda().and(t -> t.eq(PaymentBillEntity::getPayLogNo, paymentBillPagination.getPayLogNo()));
        }

        if (InputCheckUtil.isNotEmpty(paymentBillPagination.getPayState())) {
            queryWrapper.lambda().and(t -> t.eq(PaymentBillEntity::getPayState, paymentBillPagination.getPayState()));
        }

        queryWrapper.lambda().and(t -> t.eq(PaymentBillEntity::getEnabledMark, 1));
        queryWrapper.lambda().orderByDesc(PaymentBillEntity::getCreatorTime);

        Page<PaymentBillEntity> page = new Page<>(paymentBillPagination.getCurrentPage(), paymentBillPagination.getPageSize());
        IPage<PaymentBillEntity> userIPage = this.page(page, queryWrapper);
        return paymentBillPagination.setData(userIPage.getRecords(), userIPage.getTotal());
    }

    @Override
    public List<PaymentBillEntity> getTypeList(PaymentBillPagination paymentBillPagination, String dataType) {
        QueryWrapper<PaymentBillEntity> queryWrapper = new QueryWrapper<>();
        if (InputCheckUtil.isNotEmpty(paymentBillPagination.getPayBeginTime()) && InputCheckUtil.isNotEmpty(paymentBillPagination.getPayEndTime())) {
            Date beginDate = new Date(Long.valueOf(paymentBillPagination.getPayBeginTime()));
            Date endDate = new Date(Long.valueOf(paymentBillPagination.getPayBeginTime()));
            String beginDateString = DateUtils.formatDate(beginDate, "yyyy-MM-dd 00:00:00");
            String endDateString = DateUtils.formatDate(endDate, "yyyy-MM-dd 23:59:59");
            queryWrapper.lambda().and(t -> t.between(PaymentBillEntity::getPayTime, beginDateString, endDateString));
        }
        if (InputCheckUtil.isNotEmpty(paymentBillPagination.getBeginTime()) && InputCheckUtil.isNotEmpty(paymentBillPagination.getEndTime())) {
            Date beginDate = new Date(Long.valueOf(paymentBillPagination.getBeginTime()));
            Date endDate = new Date(Long.valueOf(paymentBillPagination.getEndTime()));
            String beginDateString = DateUtils.formatDate(beginDate, "yyyy-MM-dd 00:00:00");
            String endDateString = DateUtils.formatDate(endDate, "yyyy-MM-dd 23:59:59");
            queryWrapper.lambda().and(t -> t.between(PaymentBillEntity::getBeginDate, beginDateString, endDateString));
        }
        if (InputCheckUtil.isNotEmpty(paymentBillPagination.getResourceName())) {
            queryWrapper.lambda().and(t -> t.like(PaymentBillEntity::getResourceName, paymentBillPagination.getResourceName()));
        }

        if (InputCheckUtil.isNotEmpty(paymentBillPagination.getResourceId())) {
            queryWrapper.lambda().and(t -> t.eq(PaymentBillEntity::getResourceId, paymentBillPagination.getResourceId()));
        }

        if (InputCheckUtil.isNotEmpty(paymentBillPagination.getFeeItemId())) {
            queryWrapper.lambda().and(t -> t.eq(PaymentBillEntity::getFeeItemId, paymentBillPagination.getFeeItemId()));
        }

        if (InputCheckUtil.isNotEmpty(paymentBillPagination.getFeeItemName())) {
            queryWrapper.lambda().and(t -> t.like(PaymentBillEntity::getFeeItemName, paymentBillPagination.getFeeItemName()));
        }

        if (InputCheckUtil.isNotEmpty(paymentBillPagination.getPayLogNo())) {
            queryWrapper.lambda().and(t -> t.eq(PaymentBillEntity::getPayLogNo, paymentBillPagination.getPayLogNo()));
        }

        queryWrapper.lambda().and(t -> t.eq(PaymentBillEntity::getEnabledMark, 1));
        queryWrapper.lambda().orderByDesc(PaymentBillEntity::getCreatorTime);
        if ("0".equals(dataType)) {
            Page<PaymentBillEntity> page = new Page<>(paymentBillPagination.getCurrentPage(), paymentBillPagination.getPageSize());
            IPage<PaymentBillEntity> userIPage = this.page(page, queryWrapper);
            return paymentBillPagination.setData(userIPage.getRecords(), userIPage.getTotal());
        } else {
            return this.list(queryWrapper);
        }
    }

    @Override
    public PaymentBillEntity getInfo(String id) {
        QueryWrapper<PaymentBillEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(PaymentBillEntity::getId, id);
        return this.getOne(queryWrapper);
    }

    /**
     * 缴费单的更新只能更新周期和费用
     */
    @Override
    public boolean update(String id, PaymentBillEntity entity) {
        PaymentBillEntity oldEntity = this.getById(id);
        if (oldEntity.getPayState().equals("" + ConstantsUtil.PAY_BILL_PAY_STATE_PAIED)) {
            throw new DataException("该缴费单已经付费了，修改失败");
        }
        if (oldEntity.getPayState().equals("" + ConstantsUtil.PAY_BILL_PAY_STATE_PAYING)) {
            throw new DataException("该缴费单正在缴费中，修改失败");
        }
        oldEntity.setReceivable(entity.getReceivable());
        oldEntity.setBeginDate(entity.getBeginDate());
        oldEntity.setEndDate(entity.getEndDate());
        return this.updateById(oldEntity);
    }

    @Override
    public void delete(PaymentBillEntity entity) {
        if (entity.getPayState().equals("" + ConstantsUtil.PAY_BILL_PAY_STATE_PAIED)) {
            throw new DataException("该缴费单已经付费了，删除失败");
        }
        if (entity.getPayState().equals("" + ConstantsUtil.PAY_BILL_PAY_STATE_PAYING)) {
            throw new DataException("该缴费单正在缴费中，删除失败");
        }
        if (entity != null) {
            String userId = "" + userProvider.get().getUserId();
            entity.setDeleteTime(new Date());
            entity.setDeleteUserId(userId);
            entity.setEnabledMark(0);
            this.updateById(entity);
        }
    }

    @Override
    public void disableByResourceId(String resourceId) {
        throw new DataException("该方式暂不支持");
        // String userId = "" + userProvider.get().getUserId();
        // QueryWrapper<PaymentBillEntity> queryWrapper = new QueryWrapper<>();
        // queryWrapper.lambda().and(t -> t.like(PaymentBillEntity::getEnabledMark, 1));
        // List<PaymentBillEntity> list = this.list(queryWrapper);
        //
        // for (PaymentBillEntity entity : list) {
        // entity.setDeleteTime(new Date());
        // entity.setDeleteUserId(userId);
        // }
        // this.updateBatchById(list);
    }

    @Override
    public List<PaymentBillEntity> getUnpaiedAndPayingListByResourceLike(PaymentBillPagination paymentBillPagination) {
        QueryWrapper<PaymentBillEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().and(t -> t.in(PaymentBillEntity::getPayState,
            new String[] {"" + ConstantsUtil.PAY_BILL_PAY_STATE_UNPAIED, "" + ConstantsUtil.PAY_BILL_PAY_STATE_PAYING}));
        queryWrapper.lambda().and(t -> t.eq(PaymentBillEntity::getEnabledMark, 1));
        if (StringUtils.isNotEmpty(paymentBillPagination.getFeeItemId())) {

            queryWrapper.lambda().and(t -> t.eq(PaymentBillEntity::getFeeItemId, paymentBillPagination.getFeeItemId()));

        }
        if (StringUtils.isNotEmpty(paymentBillPagination.getResourceName())) {
            queryWrapper.lambda().and(t -> t.likeRight(PaymentBillEntity::getResourceName, paymentBillPagination.getResourceName()));
        }
        queryWrapper.lambda().orderByAsc(PaymentBillEntity::getResourceId);
        return this.list(queryWrapper);
    }

    @Override
    public List<PaymentBillEntity> getUnpaiedListByResource(String resourceName) {
        QueryWrapper<PaymentBillEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().and(t -> t.eq(PaymentBillEntity::getResourceName, resourceName));
        queryWrapper.lambda().and(t -> t.eq(PaymentBillEntity::getPayState, ConstantsUtil.PAY_BILL_PAY_STATE_UNPAIED));
        queryWrapper.lambda().and(t -> t.eq(PaymentBillEntity::getEnabledMark, 1));
        return this.list(queryWrapper);
    }

    @Override
    public List<PaymentBillEntity> getPayingListByResource(String resourceName) {
        QueryWrapper<PaymentBillEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().and(t -> t.eq(PaymentBillEntity::getResourceName, resourceName));
        queryWrapper.lambda().and(t -> t.eq(PaymentBillEntity::getPayState, ConstantsUtil.PAY_BILL_PAY_STATE_PAYING));
        queryWrapper.lambda().and(t -> t.eq(PaymentBillEntity::getEnabledMark, 1));
        return this.list(queryWrapper);
    }

    @Override
    public List<PaymentBillEntity> getUnpaiedListByResources(List<String> resourceNames) {
        if (resourceNames.size() > 0) {
            QueryWrapper<PaymentBillEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().and(t -> t.in(PaymentBillEntity::getResourceName, resourceNames));
            queryWrapper.lambda().and(t -> t.eq(PaymentBillEntity::getPayState, ConstantsUtil.PAY_BILL_PAY_STATE_UNPAIED));
            queryWrapper.lambda().and(t -> t.eq(PaymentBillEntity::getEnabledMark, 1));
            return this.list(queryWrapper);
        } else {
            return new ArrayList<PaymentBillEntity>();
        }
    }

    @Override
    public List<PaymentBillEntity> getPaiedListByContracts(List<String> contracts) {
        if (contracts.size() > 0) {
            QueryWrapper<PaymentBillEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().and(t -> t.in(PaymentBillEntity::getContractId, contracts));
            queryWrapper.lambda().and(t -> t.eq(PaymentBillEntity::getPayState, ConstantsUtil.PAY_BILL_PAY_STATE_PAIED));
            queryWrapper.lambda().and(t -> t.eq(PaymentBillEntity::getEnabledMark, 1));
            return this.list(queryWrapper);
        } else {
            return new ArrayList<PaymentBillEntity>();
        }
    }

    @Override
    public List<PaymentBillEntity> getListByOrders(List<String> orders) {
        if (orders.size() > 0) {
            QueryWrapper<PaymentBillEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().and(t -> t.in(PaymentBillEntity::getOrderId, orders));
            queryWrapper.lambda().and(t -> t.eq(PaymentBillEntity::getEnabledMark, 1));
            return this.list(queryWrapper);
        } else {
            return new ArrayList<PaymentBillEntity>();
        }
    }

    @Override
    public List<PaymentBillEntity> getUnpaiedListByFeeItemId(String paymentFeeItemId) {
        QueryWrapper<PaymentBillEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().and(t -> t.eq(PaymentBillEntity::getFeeItemId, paymentFeeItemId));
        queryWrapper.lambda().and(t -> t.eq(PaymentBillEntity::getPayState, ConstantsUtil.PAY_BILL_PAY_STATE_UNPAIED));
        queryWrapper.lambda().and(t -> t.like(PaymentBillEntity::getEnabledMark, 1));
        return this.list(queryWrapper);
    }

    @Override
    public void updateFee(PaymentBillEntity entity) throws IllegalAccessException {
        if (StringUtils.isEmpty(entity.getId())) {
            throw new DataException("请求的数据不合法");
        }
        PaymentBillEntity oldEntity = this.getById(entity.getId());
        if (entity.getTotal() != entity.getTotal()) {
            throw new DataException("数据异常，请求失败");
        }

        ConfigFeeItemEntity fee = configFeeItemService.getById(entity.getFeeItemId());
        entity.setLateFee(FeeCalculationUtil.getLateFee(entity.getTotal(), entity.getDeadline(), fee));
        if (StringUtils.isEmpty(entity.getDiscount()) || entity.getDiscount().equals("0")) {
            entity.setDiscount("0");
        }
        entity.setReceivable(FeeCalculationUtil.getReceivable(entity.getTotal(), entity.getLateFee(), entity.getDiscount()));
        this.save(entity);
    }

    // 商铺下的所有缴费单也禁用掉
    @EventListener
    public void ContractDelete(ContractEvent event) {
        if (event.getState().equals(event.STATE_CANCEL)) {
            PaymentContractEntity contract = event.getContract();
            String resourceName = contract.getResourceName();

            // 查询商铺下的未缴费和缴费中的订单，如果有则报错
            List<PaymentBillEntity> unpaiedBills = this.getUnpaiedListByResource(resourceName);
            if (unpaiedBills.size() > 0) {
                throw new DataException("当前商铺下有未缴纳的物业费，不能解绑");
            }

            List<PaymentBillEntity> payingBills = this.getPayingListByResource(resourceName);
            if (payingBills.size() > 0) {
                throw new DataException("当前商铺下有缴费中的数据，不能解绑");
            }
        }
    }

    @Override
    public List<PaymentBillEntity> getBillsByLogId(String payLogId) {
        QueryWrapper<PaymentBillEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().and(t -> t.eq(PaymentBillEntity::getPayLogId, payLogId));
        queryWrapper.lambda().and(t -> t.eq(PaymentBillEntity::getPayState, ConstantsUtil.PAY_BILL_PAY_STATE_PAIED));
        queryWrapper.lambda().and(t -> t.eq(PaymentBillEntity::getEnabledMark, 1));
        return this.list(queryWrapper);
    }

}