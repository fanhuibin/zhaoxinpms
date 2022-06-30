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

    //通过payLogId查询付款记录
    List<PaymentBillEntity> getBillsByLogId(String payLogId);
    
    //通过resourceNames查询未付款记录
    List<PaymentBillEntity> getUnpaiedListByResources(List<String> resourceNames);

    //通过contract信息查询付款记录
    List<PaymentBillEntity> getPaiedListByContracts(List<String> contracts);
    
    //通过order信息查询
    List<PaymentBillEntity> getListByOrders(List<String> orders);

    //通过resourceNames查询在线缴费中的数据
    List<PaymentBillEntity> getPayingListByResource(String resourceName);

    //分页查询未付款或者付款中的bill
    List<PaymentBillEntity> getUnpaiedAndPayingListByResourceLike(PaymentBillPagination paymentBillPagination);
}
