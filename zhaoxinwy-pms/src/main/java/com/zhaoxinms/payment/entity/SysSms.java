package com.zhaoxinms.payment.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.zhaoxinms.common.core.mybatisplus.BaseEntity;

/**
 * 消息推送记录对象 sys_sms
 *
 * @author fanhuibin
 * @date 2022-06-28
 */
@Data
@Accessors(chain = true)
@TableName("sys_sms")
public class SysSms extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * ID
     */
    @TableId(value = "id")
    private String id;
    /**
     * 消息标题
     */
    private String esTitle;
    /**
     * 发送方式：1短信 2邮件 3微信
     */
    private String esType;
    /**
     * 接收人
     */
    private String esReceiver;
    /**
     * 发送所需参数Json格式
     */
    private String esParam;
    /**
     * 推送内容
     */
    private String esContent;
    /**
     * 推送时间
     */
    private Date esSendTime;
    /**
     * 推送状态 0未推送 1推送成功 2推送失败 -1失败不再发送
     */
    private String esSendStatus;
    /**
     * 发送次数 超过5次不再发送
     */
    private Long esSendNum;
    /**
     * 推送失败原因
     */
    private String esResult;

}
