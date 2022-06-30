package com.zhaoxinms.baseconfig.model.house;


import lombok.Data;
import java.util.List;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import cn.afterturn.easypoi.excel.annotation.Excel;

/**
 * House模型
 */
@Data
public class HouseContractListVO  {
	
    @JsonProperty("resourceId")
    private String resourceId;
	
    @JsonProperty("contractId")
    private String contractId;
    
    /** 商铺编号 */
    @JsonProperty("name")
    @Excel(name = "商业区编号-商铺编号")
    private String name;
	
    /** 商铺编号 */
    @JsonProperty("code")
    @Excel(name = "商铺编号")
    private String code;

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
    private String floor;
    
    /** 租金 */
    @JsonProperty("rentFee")
    private String rentFee;

    /** 占地面积 */
    @JsonProperty("buildingsquare")
    @Excel(name = "占地面积")
    private String buildingsquare;

    /** 使用面积 */
    @JsonProperty("usesquare")
    @Excel(name = "使用面积")
    private String usesquare;

    /** 备注 */
    @JsonProperty("remark")
    private String remark;
    
    /** 公司名 */
    @JsonProperty("company")
    private String company;
    
    /** 租户姓名/业主姓名 */
    @JsonProperty("userName")
    private String userName;

    /** 租户身份证/业主身份证 */
    @JsonProperty("userIdcard")
    private String userIdcard;

    /** 联系方式 */
    @JsonProperty("userPhone")
    private String userPhone;

    /** 性别 */
    @JsonProperty("userGender")
    private String userGender;

    /** 从事的行业 */
    @JsonProperty("userTrade")
    private String userTrade;

    /** 开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("beginDate")
    private String beginDate;
    
    /** 备注 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("endDate")
    private String endDate;
    
}