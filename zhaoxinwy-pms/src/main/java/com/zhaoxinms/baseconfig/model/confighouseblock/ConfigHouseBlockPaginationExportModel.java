package com.zhaoxinms.baseconfig.model.confighouseblock;


import com.zhaoxinms.base.vo.Pagination;

import lombok.Data;

@Data
public class ConfigHouseBlockPaginationExportModel extends Pagination {

    private String selectKey;

    private String json;

    private String dataType;

    /** 商业区编号 */
    private String code;

    /** 商业区名 */
    private String name;

}