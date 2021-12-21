package com.zhaoxinms.payment.model.paymentbill;

import com.zhaoxinms.base.vo.Pagination;

import lombok.Data;

@Data
public class PaymentBillPagination extends Pagination {
    /** 资源名 */
    private String resourceName;

    /** 资源id */
    private String resourceId;

    /** 收费项id */
    private String feeItemId;

    /** 收费项名 */
    private String feeItemName;

    /** 支付状态 1已支付 0未支付 */
    private String payState;

    /** 支付的时间范围搜索，支付开始时间 */
    private String payLogNo;

    private String payBeginTime;

    private String payEndTime;

    private String beginTime;

    private String endTime;
}