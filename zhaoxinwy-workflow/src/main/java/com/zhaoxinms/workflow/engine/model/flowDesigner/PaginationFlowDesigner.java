package com.zhaoxinms.workflow.engine.model.flowDesigner;

import com.zhaoxinms.base.vo.Pagination;

import lombok.Data;

@Data
public class PaginationFlowDesigner extends Pagination {
    private String enabledMark;
    private String fullName;
}
