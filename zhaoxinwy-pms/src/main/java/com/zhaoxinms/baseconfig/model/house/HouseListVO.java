package com.zhaoxinms.baseconfig.model.house;


import lombok.Data;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
@Data
public class HouseListVO{
    /** 主键 */
    private String id;

    /** 商铺编号 */
    @JsonProperty("code")
    private String code;
    
    /** 完整编号 */
    @JsonProperty("name")
    private String name;

    /** 商业区 */
    @JsonProperty("block")
    private String block;
    
    /** 商业区 */
    @JsonProperty("blockName")
    private String blockName;
    
    /** 楼栋 */
    @JsonProperty("building")
    private String building;
    
    /** 楼栋 */
    @JsonProperty("buildingName")
    private String buildingName;
    
    /** 楼层 */
    @JsonProperty("floor")
    private String floor;
    
    /** 租金 */
    @JsonProperty("rentFee")
    private String rentFee;

    /** 使用状态 */
    @JsonProperty("state")
    private String state;

    /** 占地面积 */
    @JsonProperty("buildingsquare")
    private String buildingSquare;

    /** 使用面积 */
    @JsonProperty("usesquare")
    private String useSquare;

}