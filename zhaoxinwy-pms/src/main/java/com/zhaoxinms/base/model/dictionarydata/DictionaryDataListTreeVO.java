package com.zhaoxinms.base.model.dictionarydata;

import java.util.List;

import lombok.Data;

@Data
public class DictionaryDataListTreeVO {
    private String id;
    private String parentId;
    private Boolean hasChildren;
    private List<DictionaryDataListTreeVO> children;
    private String fullName;
    private String enCode;
    private Integer enabledMark;
    private long sortCode;
}
