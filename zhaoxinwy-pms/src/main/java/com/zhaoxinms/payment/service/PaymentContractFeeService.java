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

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhaoxinms.payment.entity.PaymentContractFeeEntity;
import com.zhaoxinms.payment.model.paymentcontract.PaymentContractFeeListVO;

/**
 *
 * payment_contract_fee 版本： V3.1.0 版权： 作者： CYCBERFORM 日期： 2021-09-23 09:19:28
 */
public interface PaymentContractFeeService extends IService<PaymentContractFeeEntity> {

    PaymentContractFeeEntity getInfo(String id);

    void delete(PaymentContractFeeEntity entity);

    void create(PaymentContractFeeEntity entity);

    boolean update(String id, PaymentContractFeeEntity entity);

    void deleteByContractId(String id);

    List<PaymentContractFeeEntity> getbyContractId(String contractId);

    List<PaymentContractFeeListVO> getCanGenerateData(String feeItemId, Date beginDate, Date endDate);

    List<PaymentContractFeeListVO> getByFeeId(String feeItemId);

    List<PaymentContractFeeListVO> getCanGenerateData(String resourceName, String feeItemId, String beginDate,
        String endDate);
}
