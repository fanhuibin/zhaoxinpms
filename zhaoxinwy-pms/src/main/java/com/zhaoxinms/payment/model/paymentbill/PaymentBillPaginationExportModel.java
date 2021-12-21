package com.zhaoxinms.payment.model.paymentbill;

import com.zhaoxinms.base.vo.Pagination;

import lombok.Data;

@Data
public class PaymentBillPaginationExportModel extends Pagination {

    private String selectKey;

    private String json;

    private String dataType;

    /** 资源名 */
    private String resourceName;

    /** 收费项名 */
    private String chargingItemName;

}