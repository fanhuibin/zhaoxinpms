package com.zhaoxinms.base.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("base_billrule")
public class BillRuleEntity {
    /**
     * 单据主键
     */
    @TableId("ID")
    private String id;

    /**
     * 单据名称
     */
    @TableField("FULLNAME")
    private String fullName;

    /**
     * 单据编码
     */
    @TableField("ENCODE")
    private String enCode;

    /**
     * 单据前缀
     */
    @TableField("PREFIX")
    private String prefix;

    /**
     * 日期格式
     */
    @TableField("DATEFORMAT")
    private String dateFormat;

    /**
     * 流水位数
     */
    @TableField("DIGIT")
    private Integer digit;

    /**
     * 流水起始
     */
    @TableField("STARTNUMBER")
    private String startNumber;

    /**
     * 流水范例
     */
    @TableField("EXAMPLE")
    private String example;

    /**
     * 当前流水号
     */
    @TableField("THISNUMBER")
    private Integer thisNumber;

    /**
     * 输出流水号
     */
    @TableField("OUTPUTNUMBER")
    private String outputNumber;

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
     * 删除时间
     */
    @TableField("DELETETIME")
    private Date deleteTime;

    /**
     * 删除用户
     */
    @TableField("DELETEUSERID")
    private String deleteUserId;

    /**
     * 删除标志
     */
    @TableField("DELETEMARK")
    private Integer deleteMark;
}
