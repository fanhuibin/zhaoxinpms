package com.zhaoxinms.payment.model.paymentpre;

import com.zhaoxinms.base.vo.Pagination;

import lombok.Data;

@Data
public class PaymentPrePagination extends Pagination {
    /** 资源名 */
    private String resourceName;

    /** 资源id */
    private String resourceId;

    /** 商业区 */
    private String block;

    /**  */
    private String feeItemId;

    /** 付款方式 */
    private String payType;

    private String type;
}