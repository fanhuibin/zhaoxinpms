package com.zhaoxinms.base.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("base_dictionarytype")
public class DictionaryTypeEntity {
    /**
     * 主键
     */
    @TableId("ID")
    private String id;

    /**
     * 上级
     */
    @TableField("PARENTID")
    private String parentId;

    /**
     * 名称
     */
    @TableField("FULLNAME")
    private String fullName;

    /**
     * 编码
     */
    @TableField("ENCODE")
    private String enCode;

    /**
     * 树形
     */
    @TableField("ISTREE")
    private Integer isTree;

    /**
     * 描述
     */
    @TableField("DESCRIPTION")
    private String description;

    /**
     * 排序码
     */
    @TableField("SORTCODE")
    private Long sortCode;

    /**
     * 有效标志
     */
    @TableField("ENABLEDMARK")
    private Integer enabledMark;


    /**
     * 创建时间
     */
    @TableField(value = "CREATORTIME",fill = FieldFill.INSERT)
    private Date creatorTime;

    /**
     * 创建用户
     */
    @TableField(value = "CREATORUSERID",fill = FieldFill.INSERT)
    @JSONField(name = "CreatorUserId")
    private String creatorUserId;

    /**
     * 修改时间
     */
    @TableField(value = "LASTMODIFYTIME",fill = FieldFill.UPDATE)
    private Date lastModifyTime;

    /**
     * 修改用户
     */
    @TableField(value = "LASTMODIFYUSERID",fill = FieldFill.UPDATE)
    private String lastModifyUserId;

    /**
     * 删除标志
     */
    @TableField("DELETEMARK")
    private Integer deleteMark;

    /**
     * 删除时间
     */
    @TableField("DELETETIME")
    private Date deleteTime;

    /**
     * 删除用户
     */
    @TableField("DELETEUSERID")
    private String deleteUserId;

}
