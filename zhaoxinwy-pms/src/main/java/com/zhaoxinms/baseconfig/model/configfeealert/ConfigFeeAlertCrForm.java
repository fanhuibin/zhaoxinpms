package com.zhaoxinms.baseconfig.model.configfeealert;


import lombok.Data;
import java.util.List;
import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
public class ConfigFeeAlertCrForm  {
    /** 收费项id */
    @JsonProperty("feeId")
    private String feeId;

    /** 天数 */
    @JsonProperty("day")
    private Integer day;


}