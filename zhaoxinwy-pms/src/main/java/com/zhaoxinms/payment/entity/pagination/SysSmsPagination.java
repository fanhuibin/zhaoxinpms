package com.zhaoxinms.payment.entity.pagination;

import com.zhaoxinms.common.core.validate.AddGroup;
import com.zhaoxinms.common.core.validate.EditGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;
import com.zhaoxinms.base.vo.Pagination;
import java.util.Date;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.zhaoxinms.common.core.domain.BaseEntity;

/**
 * 消息推送记录业务对象 sys_sms
 *
 * @author fanhuibin
 * @date 2022-06-28
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("消息推送记录业务对象")
public class SysSmsPagination extends Pagination {

    @ApiModelProperty(value = "消息标题")
    private String esTitle;

    @ApiModelProperty(value = "发送方式：1短信 2邮件 3微信")
    private String esType;

    @ApiModelProperty(value = "接收人")
    private String esReceiver;

    @ApiModelProperty(value = "发送所需参数Json格式")
    private String esParam;

    @ApiModelProperty(value = "推送内容")
    private String esContent;

    @ApiModelProperty(value = "推送时间")
    private Date esSendTime;

    @ApiModelProperty(value = "推送状态 0未推送 1推送成功 2推送失败 -1失败不再发送")
    private String esSendStatus;

    @ApiModelProperty(value = "发送次数 超过5次不再发送")
    private Long esSendNum;

    @ApiModelProperty(value = "推送失败原因")
    private String esResult;


}
