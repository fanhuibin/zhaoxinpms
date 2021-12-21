package com.zhaoxinms.payment.model.paymentpreaccount;

import com.zhaoxinms.base.vo.Pagination;

import lombok.Data;

@Data
public class PaymentPreAccountPagination extends Pagination {
    /** 资源id */
    private String resourceId;
    /** 资源Name */
    private String resourceName;

    /** 商业区 */
    private String block;

    private String feeItemId;

    private String amt;

}