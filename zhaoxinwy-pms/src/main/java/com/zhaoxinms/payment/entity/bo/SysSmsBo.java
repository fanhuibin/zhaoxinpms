package com.zhaoxinms.payment.entity.bo;

import com.zhaoxinms.common.core.validate.AddGroup;
import com.zhaoxinms.common.core.validate.EditGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.zhaoxinms.common.core.mybatisplus.BaseEntity;

/**
 * 消息推送记录业务对象 sys_sms
 *
 * @author fanhuibin
 * @date 2022-06-28
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("消息推送记录业务对象")
public class SysSmsBo extends BaseEntity {

    /**
     * ID
     */
    @ApiModelProperty(value = "ID")
    private String id;

    /**
     * 消息标题
     */
    @ApiModelProperty(value = "消息标题")
    private String esTitle;

    /**
     * 发送方式：1短信 2邮件 3微信
     */
    @ApiModelProperty(value = "发送方式：1短信 2邮件 3微信")
    private String esType;

    /**
     * 接收人
     */
    @ApiModelProperty(value = "接收人")
    private String esReceiver;

    /**
     * 发送所需参数Json格式
     */
    @ApiModelProperty(value = "发送所需参数Json格式")
    private String esParam;

    /**
     * 推送内容
     */
    @ApiModelProperty(value = "推送内容")
    private String esContent;

    /**
     * 推送时间
     */
    @ApiModelProperty(value = "推送时间")
    private Date esSendTime;

    /**
     * 推送状态 0未推送 1推送成功 2推送失败 -1失败不再发送
     */
    @ApiModelProperty(value = "推送状态 0未推送 1推送成功 2推送失败 -1失败不再发送")
    private String esSendStatus;

    /**
     * 发送次数 超过5次不再发送
     */
    @ApiModelProperty(value = "发送次数 超过5次不再发送")
    private Long esSendNum;

    /**
     * 推送失败原因
     */
    @ApiModelProperty(value = "推送失败原因")
    private String esResult;


}
