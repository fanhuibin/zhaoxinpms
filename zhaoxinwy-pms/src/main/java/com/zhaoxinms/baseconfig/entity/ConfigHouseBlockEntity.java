package com.zhaoxinms.baseconfig.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@TableName("config_house_block")
public class ConfigHouseBlockEntity  {
    /** 主键 */
    @TableId("ID")
    @JsonProperty("id")
    private String id;

    /** 商业区编号 */
    @TableField("CODE")
    private String code;

    /** 商业区名 */
    @TableField("NAME")
    private String name;
    
    /** 商业区地址 */
    @TableField("ADDRESS")
    private String address;

    /** 备注 */
    @TableField("REMARK")
    private String remark;

    /** 有效标志 */
    @TableField(value="ENABLED_MARK",fill= FieldFill.INSERT)
    private Integer enabledMark;

    /** 创建用户 */
    @TableField(value="CREATOR_USER_ID",fill= FieldFill.INSERT)
    private String creatorUserId;

    /** 创建时间 */
    @TableField(value="CREATOR_TIME",fill= FieldFill.INSERT)
    private Date creatorTime;

    /** 修改用户 */
    @TableField(value="LAST_MODIFY_USER_ID",fill= FieldFill.UPDATE)
    private String lastModifyUserId;

    /** 修改时间 */
    @TableField(value="LAST_MODIFY_TIME",fill= FieldFill.UPDATE)
    private Date lastModifyTime;

    /** 删除用户 */
    @TableField("DELETE_USER_ID")
    @JsonProperty("delete_user_id")
    private String deleteUserId;

    /** 删除时间 */
    @TableField("DELETE_TIME")
    @JsonProperty("delete_time")
    private Date deleteTime;
}
