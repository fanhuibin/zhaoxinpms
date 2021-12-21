package com.zhaoxinms.payment.model.paymentmeterindex;

import com.zhaoxinms.base.vo.Pagination;

import lombok.Data;

@Data
public class PaymentMeterIndexPagination extends Pagination {
    /** 收费的资源 */
    private String resourceId;

    /** 资源名 */
    private String resourceName;

    /** 收费项id */
    private String feeItemId;

    /** 读数时间 */
    private Long currentIndexDate;

    /** 当前表读数 */
    private Integer currentIndex;

}