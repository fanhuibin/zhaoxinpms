package com.zhaoxinms.payment.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.zhaoxinms.common.core.mybatisplus.BaseEntity;

/**
 * 短信模板对象 sys_sms_template
 *
 * @author fanhuibin
 * @date 2022-06-28
 */
@Data
@Accessors(chain = true)
@TableName("sys_sms_template")
public class SysSmsTemplate extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    @TableId(value = "id")
    private String id;
    /**
     * 模板标题
     */
    private String templateName;
    /**
     * 模板CODE
     */
    private String templateCode;
    /**
     * 模板类型：1短信 2邮件 3微信
     */
    private String templateType;
    /**
     * 模板内容
     */
    private String templateContent;

}
