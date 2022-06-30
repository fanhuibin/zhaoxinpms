package com.zhaoxinms.payment.entity.vo;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import cn.afterturn.easypoi.excel.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;



/**
 * 消息推送记录视图对象 sys_sms
 *
 * @author fanhuibin
 * @date 2022-06-28
 */
@Data
@ApiModel("消息推送记录视图对象")
public class SysSmsVo {

	private static final long serialVersionUID = 1L;
	
	private String id;

    /**
     * 消息标题
     */
	@Excel(name = "消息标题")
	@ApiModelProperty("消息标题")
	private String esTitle;

    /**
     * 发送方式：1短信 2邮件 3微信
     */
	@Excel(name = "发送方式：1短信 2邮件 3微信")
	@ApiModelProperty("发送方式：1短信 2邮件 3微信")
	private String esType;

    /**
     * 接收人
     */
	@Excel(name = "接收人")
	@ApiModelProperty("接收人")
	private String esReceiver;

    /**
     * 发送所需参数Json格式
     */
	@Excel(name = "发送所需参数Json格式")
	@ApiModelProperty("发送所需参数Json格式")
	private String esParam;

    /**
     * 推送内容
     */
	@Excel(name = "推送内容")
	@ApiModelProperty("推送内容")
	private String esContent;

    /**
     * 推送时间
     */
	@Excel(name = "推送时间")
	@ApiModelProperty("推送时间")
	private Date esSendTime;

    /**
     * 推送状态 0未推送 1推送成功 2推送失败 -1失败不再发送
     */
	@Excel(name = "推送状态 0未推送 1推送成功 2推送失败 -1失败不再发送")
	@ApiModelProperty("推送状态 0未推送 1推送成功 2推送失败 -1失败不再发送")
	private String esSendStatus;

    /**
     * 发送次数 超过5次不再发送
     */
	@Excel(name = "发送次数 超过5次不再发送")
	@ApiModelProperty("发送次数 超过5次不再发送")
	private Long esSendNum;

    /**
     * 推送失败原因
     */
	@Excel(name = "推送失败原因")
	@ApiModelProperty("推送失败原因")
	private String esResult;

    /**
     * 备注
     */
	@Excel(name = "备注")
	@ApiModelProperty("备注")
	private String remark;


}
