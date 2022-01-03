package com.zhaoxinms.workflow.engine.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("act_designer")
public class FlowDesignerEntity {
    /**
     * 流程主键
     */
    @TableId("id")
    private String id;

    /**
     * 流程编码
     */
    @TableField("en_code")
    private String enCode;

    /**
     * 流程名称
     */
    @TableField("full_name")
    private String fullName;

    /**
     * 流程类型
     */
    @TableField("type")
    private int type;

    /**
     * 流程分类
     */
    @TableField("category")
    private String category;


    /**
     * 图标
     */
    @TableField("icon")
    private String icon;
    
    /**
     * 图标背景色
     */
    @TableField("icon_background")
    private String iconBackground;


    /**
     * 设计器保存的json
     */
    @TableField("json")
    private String json;

    /**
     * 描述
     */
    @TableField("description")
    private String description;

    /**
     * 排序码
     */
    @TableField("sort_code")
    private Long sortCode;

    /**
     * 有效标志
     */
    @TableField("enabled_mark")
    private Integer enabledMark;

    /**
     * 创建时间
     */
    @TableField(value = "creator_time",fill = FieldFill.INSERT)
    private Date creatorTime;

    /**
     * 创建用户
     */
    @TableField(value = "creator_user_id",fill = FieldFill.INSERT)
    private String creatorUserId;

    /**
     * 修改时间
     */
    @TableField(value = "last_modify_time",fill = FieldFill.UPDATE)
    private Date lastModifyTime;

    /**
     * 修改用户
     */
    @TableField(value = "last_modify_user_id",fill = FieldFill.UPDATE)
    private String lastModifyUserId;

    /**
     * 删除标志
     */
    @TableField("delete_mark")
    private Integer deleteMark;

    /**
     * 删除时间
     */
    @TableField("delete_time")
    private Date deleteTime;

    /**
     * 删除用户
     */
    @TableField("delete_user_id")
    private String deleteUserId;
}
