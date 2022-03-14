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
import com.zhaoxinms.payment.entity.PaymentMeterEntity;
import com.zhaoxinms.payment.model.paymentmeter.PaymentMeterImport;
import com.zhaoxinms.payment.model.paymentmeter.PaymentMeterPagination;
import com.zhaoxinms.payment.model.paymentmeter.PaymentMeterUpForm;

import java.util.*;

import javax.validation.Valid;

/**
 *
 * payment_meter 版本： V3.1.0 版权： 作者： CYCBERFORM 日期： 2021-09-24 17:52:05
 */
public interface PaymentMeterService extends IService<PaymentMeterEntity> {

    List<PaymentMeterEntity> getList(PaymentMeterPagination paymentMeterPagination);

    List<PaymentMeterEntity> getTypeList(PaymentMeterPagination paymentMeterPagination, String dataType);

    PaymentMeterEntity getInfo(String id);

    void delete(PaymentMeterEntity entity);

    void create(PaymentMeterEntity entity);

    boolean update(String id, PaymentMeterEntity entity);

    int importMeter(List<PaymentMeterImport> meterList);

    void updateById(String id, @Valid PaymentMeterUpForm paymentMeterUpForm);

    void updateIndex(PaymentMeterEntity entity);

    List<PaymentMeterEntity> getByFeeId(String feeItemId);
}
