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

import com.zhaoxinms.common.core.domain.BaseEntity;

/**
 * 短信模板业务对象 sys_sms_template
 *
 * @author fanhuibin
 * @date 2022-06-28
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("短信模板业务对象")
public class SysSmsTemplatePagination extends Pagination {

    @ApiModelProperty(value = "模板标题")
    private String templateName;

    @ApiModelProperty(value = "模板CODE", required = true)
    @NotBlank(message = "模板CODE不能为空", groups = { AddGroup.class, EditGroup.class })
    private String templateCode;

    @ApiModelProperty(value = "模板类型：1短信 2邮件 3微信", required = true)
    @NotBlank(message = "模板类型：1短信 2邮件 3微信不能为空", groups = { AddGroup.class, EditGroup.class })
    private String templateType;

    @ApiModelProperty(value = "模板内容", required = true)
    @NotBlank(message = "模板内容不能为空", groups = { AddGroup.class, EditGroup.class })
    private String templateContent;


}
