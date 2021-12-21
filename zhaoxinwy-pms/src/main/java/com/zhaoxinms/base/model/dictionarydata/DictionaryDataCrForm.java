package com.zhaoxinms.base.model.dictionarydata;

import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DictionaryDataCrForm {
    @NotBlank(message = "必填")
    @ApiModelProperty(value = "项目代码")
    private String enCode;

    private Integer enabledMark;

    @NotBlank(message = "必填")
    @ApiModelProperty(value = "上级项目名称")
    private String fullName;

    private String description;

    @NotBlank(message = "必填")
    @ApiModelProperty(value = "上级id,没有传0")
    private String parentId;
    private String dictionaryTypeId;
    private long sortCode;
}
