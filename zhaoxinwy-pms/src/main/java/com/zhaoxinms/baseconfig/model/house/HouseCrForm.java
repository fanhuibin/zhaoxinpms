package com.zhaoxinms.baseconfig.model.house;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import com.fasterxml.jackson.annotation.JsonProperty;
import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class HouseCrForm  {
    /** 商铺编号 */
    @JsonProperty("code")
    @Excel(name = "房间号")
    @Length(min = 0, max = 50)
    @NotBlank
    private String code;

    /** 商业区 */
    @JsonProperty("building")
    @Excel(name = "楼栋编号")
    private String building;
    
    /** 商业区 */
    @JsonProperty("block")
    @Excel(name = "商业区编号")
    private String block;

    /** 使用状态 */
    @JsonProperty("state")
    private String state;
    
    /** 楼层 */
    @JsonProperty("floor")
    @Excel(name = "楼层")
    @Length(min = 1, max = 2)
    @NotBlank
    private String floor;
    
    /** 占地面积 */
    @JsonProperty("buildingsquare")
    @Range(min=0,max=100000000)
    @Excel(name = "占地面积")
    private String buildingSquare;
    
    /** 租金 */
    @JsonProperty("rentFee")
    @Excel(name = "租金")
    @Pattern(regexp = "(([1-9]{1}\\d{0,7})|(0{1}))(\\.\\d{0,2})?",message = "数字格式不正确")
    private String rentFee;

    /** 使用面积 */
    @JsonProperty("usesquare")
    @Excel(name = "使用面积")
    @Range(min=0,max=100000000)
    private String useSquare;

    /** 备注 */
    @JsonProperty("remark")
    @Length(min = 0, max = 200)
    private String remark;
}