package com.zhaoxinms.baseconfig.model.house;


import com.zhaoxinms.base.vo.Pagination;

import lombok.Data;

@Data
public class HousePaginationExportModel extends Pagination {

    private String selectKey;

    private String json;

    private String dataType;

    /** 商铺编号 */
    private String code;

    /** 商业区 */
    private String area;

    /** 使用状态 */
    private String state;

}