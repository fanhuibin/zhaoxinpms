package com.zhaoxinms.base.model.dictionarydata;

import com.zhaoxinms.base.util.treeutil.SumTree;
import lombok.Data;

@Data
public class DictionaryDataModel extends SumTree {
    private String  id;
    private String parentId;
    private String  fullName;
    private String  enCode;
    private Integer  enabledMark;
    private String icon;
    private long sortCode;
}
