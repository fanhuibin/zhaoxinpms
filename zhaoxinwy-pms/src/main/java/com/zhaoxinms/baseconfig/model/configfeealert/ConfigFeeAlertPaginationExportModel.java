package com.zhaoxinms.baseconfig.model.configfeealert;


import com.zhaoxinms.base.vo.Pagination;

import lombok.Data;

@Data
public class ConfigFeeAlertPaginationExportModel extends Pagination {

    private String selectKey;

    private String json;

    private String dataType;

    /** 收费项id */
    private String feeId;

    /** 天数 */
    private Integer day;

}