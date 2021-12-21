package com.zhaoxinms.payment.model.paymentpaylog;

import com.zhaoxinms.base.vo.Pagination;

import lombok.Data;

@Data
public class PaymentPayLogPagination extends Pagination {
    /** 支付方式 */
    private String payMethod;

    /** 类型 */
    private String type;

    /** 支付的项目 */
    private String name;

    private String payBeginDate;

    private String payEndDate;
}