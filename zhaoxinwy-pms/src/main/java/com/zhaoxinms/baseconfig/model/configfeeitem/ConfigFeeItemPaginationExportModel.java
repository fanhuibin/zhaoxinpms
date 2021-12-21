package com.zhaoxinms.baseconfig.model.configfeeitem;


import com.zhaoxinms.base.vo.Pagination;

import lombok.Data;

@Data
public class ConfigFeeItemPaginationExportModel extends Pagination {

    private String selectKey;

    private String json;

    private String dataType;

    /** 收费项分类 */
    private String type;

    /** 收费项目名 */
    private String name;

    /** 费用计算公式 */
    private String formula;

    /** 是否产生滞纳金 */
    private Integer lateFeeEnable;

    /** 滞纳金延迟多久收 */
    private Integer lateFeeDays;

    /** 滞纳金比例 */
    private String lateFeeRate;

}