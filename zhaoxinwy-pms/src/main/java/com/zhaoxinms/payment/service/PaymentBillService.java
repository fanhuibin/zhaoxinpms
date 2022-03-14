/**
 * Copyright 肇新智慧物业管理系统
 *
 * Licensed under AGPL开源协议
 *
 * gitee：https://gitee.com/fanhuibin1/zhaoxinpms
 * website：http://pms.zhaoxinms.com  wx： zhaoxinms
 *
 */
package com.zhaoxinms.payment.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhaoxinms.payment.entity.PaymentBillEntity;
import com.zhaoxinms.payment.entity.PaymentPayLogEntity;
import com.zhaoxinms.payment.model.paymentbill.PaymentBillPagination;
import com.zhaoxinms.payment.model.paymentbill.PaymentBillPayForm;
import com.zhaoxinms.payment.model.paymentbill.PaymentBillRefundForm;

public interface PaymentBillService extends IService<PaymentBillEntity> {
    List<PaymentBillEntity> getList(PaymentBillPagination paymentBillPagination);

    List<PaymentBillEntity> getTypeList(PaymentBillPagination paymentBillPagination, String dataType);

    PaymentBillEntity getInfo(String id);

    void delete(PaymentBillEntity entity);

    boolean update(String id, PaymentBillEntity entity);

    // 通过资源名禁用缴费单
    void disableByResourceId(String resourceId);

    // 查询某一个资源下的所有未缴费缴费单
    List<PaymentBillEntity> getUnpaiedListByResource(String resourceName);

    // 通过收费项查询未缴费的缴费单
    public List<PaymentBillEntity> getUnpaiedListByFeeItemId(String paymentFeeItemId);

    // 更新折扣和滞纳金
    void updateFee(PaymentBillEntity entity) throws IllegalAccessException;

    // 付款数据验证
    void payCheck(PaymentBillPayForm payForm);

    // 计算付款金额
    void payCalc(PaymentBillPayForm payForm) throws IllegalAccessException;
    
    // 已支付的bill，发起退款
    void refundBill(PaymentBillRefundForm refundForm);

    // 保存付款
    PaymentPayLogEntity paySave(PaymentBillPayForm payForm);

    //通过resourceNames查询未付款记录
    List<PaymentBillEntity> getUnpaiedListByResources(List<String> resourceNames);

    //通过contract信息查询付款记录
    List<PaymentBillEntity> getPaiedListByContracts(List<String> contracts);
}
