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

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhaoxinms.payment.entity.PaymentPreAccountEntity;
import com.zhaoxinms.payment.entity.PaymentPreEntity;
import com.zhaoxinms.payment.model.paymentpreaccount.PaymentPreAccountPagination;

import java.util.*;

/**
 *
 * payment_pre_account 版本： V3.1.0 版权： 作者： CYCBERFORM 日期： 2021-10-13 16:51:26
 */
public interface PaymentPreAccountService extends IService<PaymentPreAccountEntity> {

    List<PaymentPreAccountEntity> getList(PaymentPreAccountPagination paymentPreAccountPagination);

    List<PaymentPreAccountEntity> getTypeList(PaymentPreAccountPagination paymentPreAccountPagination, String dataType);

    PaymentPreAccountEntity getInfo(String id);

    void create(PaymentPreAccountEntity entity);

    /**
     * 预存
     */
    void recharge(String resourceId, String feeItemId, String rechargeAmount);

    /**
     * 扣费
     */
    void charge(String resourceId, String feeItemId, String chargeAmount);

    /**
     * 查询某个商铺下的所有预存款信息
     * 
     * @return
     */
    List<PaymentPreAccountEntity> getAccountsByResourceId(String resourceId);
}
