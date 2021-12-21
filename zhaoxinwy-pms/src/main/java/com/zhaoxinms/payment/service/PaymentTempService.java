package com.zhaoxinms.payment.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhaoxinms.payment.entity.PaymentTempEntity;
import com.zhaoxinms.payment.model.paymenttemp.PaymentTempPagination;
import com.zhaoxinms.payment.model.paymenttemp.PaymentTempRefundForm;

/**
 *
 * payment_temp 版本： V3.1.0 版权： 作者： CYCBERFORM 日期： 2021-09-29 17:54:49
 */
public interface PaymentTempService extends IService<PaymentTempEntity> {

    List<PaymentTempEntity> getList(PaymentTempPagination paymentTempPagination);

    List<PaymentTempEntity> getTypeList(PaymentTempPagination paymentTempPagination, String dataType);

    PaymentTempEntity getInfo(String id);

    void delete(PaymentTempEntity entity);

    void create(PaymentTempEntity entity);

    boolean refund(String id, PaymentTempRefundForm form);
}
