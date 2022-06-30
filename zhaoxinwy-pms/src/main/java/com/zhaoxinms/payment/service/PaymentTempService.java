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
import com.zhaoxinms.payment.entity.PaymentTempEntity;
import com.zhaoxinms.payment.model.paymenttemp.PaymentTempPagination;
import com.zhaoxinms.payment.model.paymenttemp.PaymentTempRefundForm;

public interface PaymentTempService extends IService<PaymentTempEntity> {

    List<PaymentTempEntity> getList(PaymentTempPagination paymentTempPagination);

    List<PaymentTempEntity> getTypeList(PaymentTempPagination paymentTempPagination, String dataType);

    PaymentTempEntity getInfo(String id);

    void delete(PaymentTempEntity entity);

    void create(PaymentTempEntity entity);

    boolean refund(String id, PaymentTempRefundForm form);
    
    //通过payNo查询付款记录
    PaymentTempEntity getByPayNo(String payNo);
}
