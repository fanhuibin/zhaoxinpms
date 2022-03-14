package com.zhaoxinms.baseconfig.model.confighouseblock;


import lombok.Data;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
public class ConfigHouseBlockListVO{
    /** 主键 */
    private String id;

    /** 商业区编号 */
    @JsonProperty("code")
    private String code;

    /** 商业区名 */
    @JsonProperty("name")
    private String name;

    /** 商业区地址 */
    @JsonProperty("address")
    private String address;
}