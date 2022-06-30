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
import com.zhaoxinms.payment.entity.PaymentPreEntity;
import com.zhaoxinms.payment.model.paymentpre.PaymentPrePagination;
import com.zhaoxinms.payment.model.paymentpre.PaymentPreRefundForm;

import java.util.*;

/**
 *
 * payment_pre 版本： V3.1.0 版权： 作者： CYCBERFORM 日期： 2021-10-13 18:27:17
 */
public interface PaymentPreService extends IService<PaymentPreEntity> {

    List<PaymentPreEntity> getList(PaymentPrePagination paymentPrePagination);

    List<PaymentPreEntity> getTypeList(PaymentPrePagination paymentPrePagination, String dataType);

    PaymentPreEntity getInfo(String id);

    void create(PaymentPreEntity entity, String type);

    void refund(PaymentPreRefundForm paymentPreRefundForm);

    void minus(PaymentPreEntity entity, String type);

    PaymentPreEntity getByPayNo(String payNo);
}
