package com.zhaoxinms.base.model.dictionarydata;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DictionaryDataAllVO {
    private String  id;
    private String  fullName;
    private String parentId;
    private List<DictionaryDataAllVO> children;
}
