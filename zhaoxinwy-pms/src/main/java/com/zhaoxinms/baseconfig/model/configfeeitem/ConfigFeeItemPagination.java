package com.zhaoxinms.baseconfig.model.configfeeitem;


import com.zhaoxinms.base.vo.Pagination;

import lombok.Data;

@Data
public class ConfigFeeItemPagination extends Pagination {
    /** 收费项分类 */
    private String type;

    /** 收费项目名 */
    private String name;

    /** 费用计算公式 */
    private String formula;
    
    /** 数量类型 */
    private String numType;
    
    /** 收费数据生成类型 */
    private String generateType;

    /** 是否产生滞纳金 */
    private Integer lateFeeEnable;

    /** 滞纳金延迟多久收 */
    private Integer lateFeeDays;

    /** 滞纳金比例 */
    private String lateFeeRate;

}