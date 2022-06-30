package com.zhaoxinms.baseconfig.model.house;


import com.zhaoxinms.base.vo.Pagination;
import lombok.Data;

@Data
public class HousePagination extends Pagination {
    /** 商铺编号 */
    private String code;

    /** 商业区 */
    private String block;

    /** 使用状态 */
    private String state;
    
    /** 商业区编号-商铺编号 */
    private String name;
    
    private String building;
}