package com.zhaoxinms.baseconfig.model.configfeesetting;


import lombok.Data;
import java.util.List;
import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.zhaoxinms.baseconfig.entity.ConfigFeeSettingEntity;

@Data
public class ConfigFeeSettingCrForm  {

    /** 收费项目名 */
    @JsonProperty("type")
    private String type;

    private List<ConfigFeeSettingEntity> feeList;
}