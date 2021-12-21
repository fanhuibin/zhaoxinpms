package com.zhaoxinms.payment.model.paymentpaylog;

import com.zhaoxinms.base.vo.Pagination;

import lombok.Data;

@Data
public class PaymentPayLogPaginationExportModel extends Pagination {

    private String selectKey;

    private String json;

    private String dataType;

    /** 支付方式 */
    private String payMethod;

    /** 支付和退款 */
    private String type;

    /** 描述 */
    private String describe;

}