package com.zhaoxinms.payment.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhaoxinms.payment.entity.PaymentContractFeeEntity;
import com.zhaoxinms.payment.model.paymentcontract.PaymentContractFeeListVO;

/**
 *
 * payment_contract_fee 版本： V3.1.0 版权： 作者： CYCBERFORM 日期： 2021-09-23 09:19:28
 */
public interface PaymentContractFeeMapper extends BaseMapper<PaymentContractFeeEntity> {

    List<PaymentContractFeeListVO> getByFeeAndBillDate(@Param("resourceName") String resourceName,
        @Param("feeItemId") String feeItemId, @Param("beginDate") String beginDate, @Param("endDate") String endDate);

    List<PaymentContractFeeListVO> getByFee(@Param("feeItemId") String feeItemId);
}
