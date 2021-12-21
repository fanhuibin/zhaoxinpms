package com.zhaoxinms.base.vo;

import lombok.Data;

@Data
public class PaginationVO {
    private Long currentPage;
    private Long pageSize;
    private Integer total;
}
