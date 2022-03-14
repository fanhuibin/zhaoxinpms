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
import com.zhaoxinms.payment.entity.PaymentDepositEntity;
import com.zhaoxinms.payment.model.paymentdeposit.PaymentDepositCrForm;
import com.zhaoxinms.payment.model.paymentdeposit.PaymentDepositPagination;
import com.zhaoxinms.payment.model.paymentdeposit.PaymentDepositRefundForm;

import java.util.*;

/**
 *
 * payment_deposit 版本： V3.1.0 版权： 作者： CYCBERFORM 日期： 2021-09-29 17:54:49
 */
public interface PaymentDepositService extends IService<PaymentDepositEntity> {

    List<PaymentDepositEntity> getList(PaymentDepositPagination paymentDepositPagination);

    List<PaymentDepositEntity> getTypeList(PaymentDepositPagination paymentDepositPagination, String dataType);

    PaymentDepositEntity getInfo(String id);

    void delete(PaymentDepositEntity entity);

    void create(PaymentDepositEntity entity);

    boolean refund(String id, PaymentDepositRefundForm form);
}
