package com.zhaoxinms.baseconfig.model.configfeealert;


import com.zhaoxinms.base.vo.Pagination;

import lombok.Data;

@Data
public class ConfigFeeAlertPagination extends Pagination {
    /** 收费项id */
    private String feeId;

    /** 天数 */
    private Integer day;

}