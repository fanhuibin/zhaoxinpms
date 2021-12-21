package com.zhaoxinms.baseconfig.model.configfeealert;


import lombok.Data;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
public class ConfigFeeAlertListVO{
    /** 主键 */
    private String id;

    /** 收费项id */
    @JsonProperty("feeId")
    private String feeId;

    /** 天数 */
    @JsonProperty("day")
    private Long day;

}