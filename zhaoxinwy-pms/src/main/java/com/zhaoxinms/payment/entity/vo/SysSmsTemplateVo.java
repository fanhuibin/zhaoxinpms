package com.zhaoxinms.payment.entity.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;



/**
 * 短信模板视图对象 sys_sms_template
 *
 * @author fanhuibin
 * @date 2022-06-28
 */
@Data
@ApiModel("短信模板视图对象")
public class SysSmsTemplateVo {

	private static final long serialVersionUID = 1L;
	
	private String id;

    /**
     * 模板标题
     */
	@Excel(name = "模板标题")
	@ApiModelProperty("模板标题")
	private String templateName;

    /**
     * 模板CODE
     */
	@Excel(name = "模板CODE")
	@ApiModelProperty("模板CODE")
	private String templateCode;

    /**
     * 模板类型：1短信 2邮件 3微信
     */
	@Excel(name = "模板类型：1短信 2邮件 3微信")
	@ApiModelProperty("模板类型：1短信 2邮件 3微信")
	private String templateType;

    /**
     * 模板内容
     */
	@Excel(name = "模板内容")
	@ApiModelProperty("模板内容")
	private String templateContent;


}
