package com.zhaoxinms.payment.model.paymentmeter;

import com.zhaoxinms.base.vo.Pagination;

import lombok.Data;

@Data
public class PaymentMeterPagination extends Pagination {
    /** 资源名 */
    private String resourceName;

    /** 收费项id */
    private String feeItemId;

}