package com.zhaoxinms.base.util.treeutil;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SumTree<T> {
    private String id;
    private String parentId;
    private Boolean hasChildren;
    private List<SumTree<T>> children;
}
