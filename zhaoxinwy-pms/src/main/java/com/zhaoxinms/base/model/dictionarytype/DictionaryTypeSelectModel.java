package com.zhaoxinms.base.model.dictionarytype;

import com.zhaoxinms.base.util.treeutil.SumTree;

import lombok.Data;

@Data
public class DictionaryTypeSelectModel extends SumTree {
    private String id;
    private String parentId;
    private String fullName;
    private String enCode;
}
