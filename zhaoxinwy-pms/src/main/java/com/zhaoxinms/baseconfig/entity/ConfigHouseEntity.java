package com.zhaoxinms.baseconfig.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonProperty;
/**
 *
 * 商铺表
 */
@Data
@TableName("config_house")
public class ConfigHouseEntity  {
    /** 主键 */
    @TableId("ID")
    @JsonProperty("id")
    private String id;

    /** 商铺编号 */
    @TableField("CODE")
    private String code;
    
    /** 完整编号 商业区编号-商铺编号 */
    @TableField("Name")
    private String name;

    /** 商业区 */
    @TableField("BLOCK")
    private String block;
    
    /**楼栋 */
    @TableField("building")
    private String building;
    
    /** 租金 */
    @TableField("RENT_FEE")
    private String rentFee;
    
    /** 楼层 */
    @TableField("FLOOR")
    private String floor;

    /** 使用状态 */
    @TableField("STATE")
    private String state;
    
    /** 使用的公司 */
    @TableField("STATE_COMPANY")
    private String stateCompany;
    
    /** 状态结束时间 */
    @TableField("STATE_END_TIME")
    private Date stateEndTime;

    /** 占地面积 */
    @TableField("BUILDING_SQUARE")
    @JsonProperty("buildingSquare")
    private String buildingSquare;

    /** 使用面积 */
    @TableField("USE_SQUARE")
    @JsonProperty("useSquare")
    private String useSquare;

    /** 备注 */
    @TableField("REMARK")
    private String remark;

    /** 创建时间 */
    @TableField("CREATOR_TIME")
    @JsonProperty("creatortime")
    private Date creatorTime;

    /** 修改用户 */
    @TableField("LAST_MODIFY_USER_ID")
    @JsonProperty("lastmodifyuserid")
    private String lastModifyUserId;

    /** 删除用户 */
    @TableField("DELETE_USER_ID")
    @JsonProperty("deleteuserid")
    private String deleteUserId;

    /** 修改时间 */
    @TableField("LAST_MODIFY_TIME")
    @JsonProperty("lastmodifytime")
    private Date lastModifyTime;

    /** 删除时间 */
    @TableField("DELETE_TIME")
    @JsonProperty("deletetime")
    private Date deleteTime;

    /** 创建用户 */
    @TableField("CREATOR_USER_ID")
    @JsonProperty("creatoruserid")
    private String creatorUserId;
    
    /**
     * 有效标志
     */
    @TableField("ENABLED_MARK")
    private Integer enabledMark=1;

}
