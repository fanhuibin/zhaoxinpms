/**
 * Copyright 肇新智慧物业管理系统
 *
 * Licensed under AGPL开源协议
 *
 * gitee：https://gitee.com/fanhuibin1/zhaoxinpms website：http://pms.zhaoxinms.com wx： zhaoxinms
 *
 */
package com.zhaoxinms.payment.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhaoxinms.base.exception.DataException;
import com.zhaoxinms.baseconfig.service.ConfigHouseService;
import com.zhaoxinms.event.OrderEvent;
import com.zhaoxinms.payment.entity.PaymentBillEntity;
import com.zhaoxinms.payment.entity.PaymentOrder;
import com.zhaoxinms.payment.entity.PaymentPayLogEntity;
import com.zhaoxinms.payment.entity.pagination.PaymentOrderPagination;
import com.zhaoxinms.payment.mapper.PaymentOrderMapper;
import com.zhaoxinms.payment.model.paymentbill.PaymentBillRefundForm;
import com.zhaoxinms.payment.service.PaymentBillService;
import com.zhaoxinms.payment.service.PaymentOrderService;
import com.zhaoxinms.payment.service.PaymentPayLogService;
import com.zhaoxinms.util.CalculationUtil;
import com.zhaoxinms.util.ConstantsUtil;
import com.zhaoxinms.util.ValidateUtil;

@Service
public class PaymentOrderServiceImpl extends ServiceImpl<PaymentOrderMapper, PaymentOrder> implements PaymentOrderService {
    @Autowired
    public ConfigHouseService houseService;
    @Autowired
    private PaymentPayLogService paymentPayLogService;
    @Autowired
    private PaymentBillService paymentBillService;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    @Transactional
    public void refundBill(PaymentBillRefundForm refundForm) {
        PaymentBillEntity bill = paymentBillService.getById(refundForm.getBillId());

        if(!bill.getPayState().equals(""+ConstantsUtil.PAY_BILL_PAY_STATE_PAIED)) {
            throw new DataException("未付款的缴费单不能退款");
        }
        if (bill == null || StringUtils.isEmpty(bill.getId())) {
            throw new DataException("查询缴费单失败");
        }

        if (!ValidateUtil.PositiveFloatOrNum(refundForm.getCurrentRefundAmount())) {
            throw new DataException("退款金额格式不正确");
        }
        if (CalculationUtil.compareTo(refundForm.getCurrentRefundAmount(), "0", 2, 0) == 0) {
            throw new DataException("退款金额不能为0");
        }

        String total = CalculationUtil.add(bill.getRefundAmount(), refundForm.getCurrentRefundAmount());

        PaymentPayLogEntity payLog =
            paymentPayLogService.refundBill(bill, refundForm.getPayMethod(), refundForm.getCurrentRefundAmount(), refundForm.getRefundComment());
        bill.setRefundTimes(bill.getRefundTimes() + 1);
        bill.setRefundAmount(total);

        if (CalculationUtil.compareTo(bill.getReceivable(), total, 2, 0) > 0) {
            bill.setRefundState(ConstantsUtil.PAY_BILL_REFUND_STATE_PARTIAL);
        } else if (CalculationUtil.compareTo(bill.getReceivable(), total, 2, 0) == 0) {
            bill.setRefundState(ConstantsUtil.PAY_BILL_REFUND_STATE_ALL);
        } else {
            throw new DataException("退款金额不能大于剩余可退款金额");
        }
        paymentBillService.updateById(bill);
        
        //更新order的refund信息
        PaymentOrder order = this.getById(bill.getOrderId());
        List<String> list = new ArrayList<String>();
        list.add(order.getId());
        List<PaymentBillEntity> bills = paymentBillService.getListByOrders(list);
        String refundTotal = "0";
        for(PaymentBillEntity entity : bills) {
            refundTotal = CalculationUtil.add(refundTotal, entity.getRefundAmount());
        }
        
        if (CalculationUtil.compareTo(order.getAmount().toString(), refundTotal, 2, 0) > 0) {
            order.setRefundState(ConstantsUtil.PAY_BILL_REFUND_STATE_PARTIAL);
        } else if (CalculationUtil.compareTo(bill.getReceivable(), total, 2, 0) == 0) {
            order.setRefundState(ConstantsUtil.PAY_BILL_REFUND_STATE_ALL);
        } else {
            throw new DataException("退款金额不能大于付款金额");
        }
        order.setRefundAmount(new BigDecimal(refundTotal));
        this.updateById(order);
    }
    
    @Override
    public List<PaymentOrder> getList(PaymentOrderPagination pagination) {
        LambdaQueryWrapper<PaymentOrder> lqw = buildQueryWrapper(pagination);
        
        //只查询init 和 success状态的订单
        lqw.in(PaymentOrder::getState, new Integer[] {PaymentOrder.STATE_INIT, PaymentOrder.STATE_SUCCESS});
        lqw.orderByDesc(PaymentOrder::getCreateTime);

        Page<PaymentOrder> page = new Page<>(pagination.getCurrentPage(), pagination.getPageSize());
        IPage<PaymentOrder> userIPage = this.page(page, lqw);
        return pagination.setData(userIPage.getRecords(), userIPage.getTotal());
    }

    @Override
    public PaymentOrder getInfo(String id) {
        QueryWrapper<PaymentOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(PaymentOrder::getId, id);
        return this.getOne(queryWrapper);
    }
    
    private LambdaQueryWrapper<PaymentOrder> buildQueryWrapper(PaymentOrderPagination pagination) {
        LambdaQueryWrapper<PaymentOrder> lqw = Wrappers.lambdaQuery();
        lqw.eq(pagination.getState() != null, PaymentOrder::getState, pagination.getState());
        lqw.eq(StringUtils.isNotBlank(pagination.getOpenId()), PaymentOrder::getOpenId, pagination.getOpenId());
        lqw.eq(pagination.getRefundState() != null, PaymentOrder::getRefundState, pagination.getRefundState());
        lqw.eq(StringUtils.isNotBlank(pagination.getErrCode()), PaymentOrder::getErrCode, pagination.getErrCode());
        lqw.eq(pagination.getExpiredTime() != null, PaymentOrder::getExpiredTime, pagination.getExpiredTime());
        lqw.eq(pagination.getSuccessTime() != null, PaymentOrder::getSuccessTime, pagination.getSuccessTime());
        lqw.eq(StringUtils.isNotBlank(pagination.getUserId()), PaymentOrder::getUserId, pagination.getUserId());
        lqw.eq(StringUtils.isNotBlank(pagination.getType()), PaymentOrder::getType, pagination.getType());
        lqw.eq(StringUtils.isNotBlank(pagination.getWayCode()), PaymentOrder::getWayCode, pagination.getWayCode());
        if (pagination.getPayTimeRange().size() > 0) {
            lqw.between(PaymentOrder::getSuccessTime, new Date(pagination.getPayTimeRange().get(0)), new Date(pagination.getPayTimeRange().get(1)));
        }
        return lqw;
    }

    @Override
    public void paySave(PaymentOrder paymentOrder) {
        this.save(paymentOrder);
        OrderEvent event = new OrderEvent(this, paymentOrder, OrderEvent.ACTION_CREATE);
        applicationEventPublisher.publishEvent(event);
    }
}