package com.zhaoxinms.baseconfig.model.configfeesetting;


import com.zhaoxinms.base.vo.Pagination;

import lombok.Data;

@Data
public class ConfigFeeSettingPaginationExportModel extends Pagination {

    private String selectKey;

    private String json;

    private String dataType;

}