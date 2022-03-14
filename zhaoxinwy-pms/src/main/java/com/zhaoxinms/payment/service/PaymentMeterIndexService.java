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
import com.zhaoxinms.payment.entity.PaymentMeterIndexEntity;
import com.zhaoxinms.payment.model.paymentmeterindex.PaymentMeterIndexPagination;

import java.util.*;

/**
 *
 * payment_meter_index 版本： V3.1.0 版权： 作者： CYCBERFORM 日期： 2021-09-26 15:51:42
 */
public interface PaymentMeterIndexService extends IService<PaymentMeterIndexEntity> {

    List<PaymentMeterIndexEntity> getList(PaymentMeterIndexPagination paymentMeterIndexPagination);

    List<PaymentMeterIndexEntity> getTypeList(PaymentMeterIndexPagination paymentMeterIndexPagination, String dataType);

    PaymentMeterIndexEntity getInfo(String id);

    void delete(PaymentMeterIndexEntity entity);

    void createOrUpdateIndex(PaymentMeterIndexEntity entity);

    boolean update(String id, PaymentMeterIndexEntity entity);
}
