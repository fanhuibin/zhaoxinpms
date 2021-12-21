package com.zhaoxinms.baseconfig.model.confighouseblock;


import com.zhaoxinms.base.vo.Pagination;

import lombok.Data;

@Data
public class ConfigHouseBlockPagination extends Pagination {
    /** 商业区编号 */
    private String code;

    /** 商业区名 */
    private String name;

}