package com.zhaoxinms.payment.entity.bo;

import com.zhaoxinms.common.core.validate.AddGroup;
import com.zhaoxinms.common.core.validate.EditGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import com.zhaoxinms.common.core.mybatisplus.BaseEntity;

/**
 * 短信模板业务对象 sys_sms_template
 *
 * @author fanhuibin
 * @date 2022-06-28
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("短信模板业务对象")
public class SysSmsTemplateBo extends BaseEntity {

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private String id;

    /**
     * 模板标题
     */
    @ApiModelProperty(value = "模板标题")
    private String templateName;

    /**
     * 模板CODE
     */
    @ApiModelProperty(value = "模板CODE", required = true)
    @NotBlank(message = "模板CODE不能为空", groups = { AddGroup.class, EditGroup.class })
    private String templateCode;

    /**
     * 模板类型：1短信 2邮件 3微信
     */
    @ApiModelProperty(value = "模板类型：1短信 2邮件 3微信", required = true)
    @NotBlank(message = "模板类型：1短信 2邮件 3微信不能为空", groups = { AddGroup.class, EditGroup.class })
    private String templateType;

    /**
     * 模板内容
     */
    @ApiModelProperty(value = "模板内容", required = true)
    @NotBlank(message = "模板内容不能为空", groups = { AddGroup.class, EditGroup.class })
    private String templateContent;


}
