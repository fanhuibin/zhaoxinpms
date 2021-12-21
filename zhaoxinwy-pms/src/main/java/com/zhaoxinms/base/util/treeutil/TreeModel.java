package com.zhaoxinms.base.util.treeutil;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TreeModel<T> {
    private String id;
    private String fullName;
    private String parentId;
    private Boolean hasChildren = true;
    private String icon;
    private List<TreeModel<T>> children = new ArrayList<>();
}
