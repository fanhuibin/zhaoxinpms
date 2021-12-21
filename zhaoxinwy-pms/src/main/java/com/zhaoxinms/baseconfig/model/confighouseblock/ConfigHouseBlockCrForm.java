package com.zhaoxinms.baseconfig.model.confighouseblock;


import lombok.Data;
import java.util.List;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
public class ConfigHouseBlockCrForm  {
    /** 商业区编号 */
    @JsonProperty("code")
    @NotBlank
    @Length(min = 0, max = 50)
    private String code;

    /** 商业区名 */
    @JsonProperty("name")
    @NotBlank
    @Length(min = 0, max = 100)
    private String name;

    /** 备注 */
    @JsonProperty("remark")
    private String remark;


}