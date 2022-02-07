package com.zhaoxinms.workflow.engine.model;

import java.util.List;

import lombok.Data;

@Data
public class TreeSelectModel {
     private String id;
     private String parentId;
     private String name;
     private Boolean hasChildren;
     private String type;
     private String icon;
     private List<TreeSelectModel> children;
}
