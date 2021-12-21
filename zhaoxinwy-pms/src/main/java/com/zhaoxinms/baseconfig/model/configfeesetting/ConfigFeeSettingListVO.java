package com.zhaoxinms.baseconfig.model.configfeesetting;


import lombok.Data;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
public class ConfigFeeSettingListVO{
    /** 主键 */
    private String id;

    /** 收费项分类 */
    @JsonProperty("feeItemId")
    private String feeItemId;

    /** 收费项目名 */
    @JsonProperty("type")
    private String type;
    
    /** 收费项名 */
    @JsonProperty("feeItemName")
    private String feeItemName;
    
    /** 单价 */
    @JsonProperty("price")
    private String price;

    /** 数量 */
    @JsonProperty("numType")
    private String numType;
}