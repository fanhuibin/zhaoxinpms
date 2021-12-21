package com.zhaoxinms.payment.mapper;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhaoxinms.payment.entity.PaymentPreAccountEntity;

/**
 *
 * payment_pre_account 版本： V3.1.0 版权： 作者： CYCBERFORM 日期： 2021-10-13 16:51:26
 */
public interface PaymentPreAccountMapper extends BaseMapper<PaymentPreAccountEntity> {

    // 账户扣费
    int charge(@Param("resourceId") String resourceId, @Param("feeItemId") String feeItemId,
        @Param("changeVal") String changeVal);

    // 账户充值
    int recharge(@Param("resourceId") String resourceId, @Param("feeItemId") String feeItemId,
        @Param("changeVal") String changeVal);

}
