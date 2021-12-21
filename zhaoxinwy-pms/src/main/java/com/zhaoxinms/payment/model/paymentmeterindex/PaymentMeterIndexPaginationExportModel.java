package com.zhaoxinms.payment.model.paymentmeterindex;

import com.zhaoxinms.base.vo.Pagination;

import lombok.Data;

@Data
public class PaymentMeterIndexPaginationExportModel extends Pagination {

    private String selectKey;

    private String json;

    private String dataType;

    /** 收费的资源 */
    private String resourceId;

    /** 资源名 */
    private String resourceName;

    /** 收费项id */
    private String feeItemId;

    /** 读数时间 */
    private Long indexDate;

    /** 当前表读数 */
    private Integer index;

}