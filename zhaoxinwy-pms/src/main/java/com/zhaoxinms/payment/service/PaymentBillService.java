package com.zhaoxinms.payment.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhaoxinms.baseconfig.entity.ConfigFeeItemEntity;
import com.zhaoxinms.payment.entity.PaymentBillEntity;
import com.zhaoxinms.payment.entity.PaymentContractEntity;
import com.zhaoxinms.payment.entity.PaymentPayLogEntity;
import com.zhaoxinms.payment.model.paymentbill.PaymentBillBatchForm;
import com.zhaoxinms.payment.model.paymentbill.PaymentBillGenerateForm;
import com.zhaoxinms.payment.model.paymentbill.PaymentBillPagination;
import com.zhaoxinms.payment.model.paymentbill.PaymentBillPayForm;

/**
 *
 * payment_bill 版本： V3.1.0 版权： 作者： CYCBERFORM 日期： 2021-08-28 17:46:03
 */
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

    // 保存付款
    PaymentPayLogEntity paySave(PaymentBillPayForm payForm);
}
