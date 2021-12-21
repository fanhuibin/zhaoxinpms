package com.zhaoxinms.base.model.dictionarydata;

import com.zhaoxinms.base.util.treeutil.SumTree;

import lombok.Data;

@Data
public class DictionaryDataAllModel extends SumTree {
    private String  fullName;
    private String  enCode;
}
