package com.zhaoxinms.payment.model.paymentcontract;

import com.zhaoxinms.base.vo.Pagination;

import lombok.Data;

@Data
public class PaymentContractPaginationExportModel extends Pagination {

    private String selectKey;

    private String json;

    private String dataType;

    /** 租户姓名/业主姓名 */
    private String userName;

    /** 联系方式 */
    private String userPhone;

    /** 从事的行业 */
    private String userTrade;

}