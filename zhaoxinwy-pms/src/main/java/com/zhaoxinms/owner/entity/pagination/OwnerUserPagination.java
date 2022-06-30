package com.zhaoxinms.owner.entity.pagination;

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
 * 业主信息业务对象 owner_user
 *
 * @author cycberform
 * @date 2022-02-23
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("业主信息业务对象")
public class OwnerUserPagination extends Pagination {
    
    @ApiModelProperty(value = "公司名", required = true)
    @NotBlank(message = "公司名不能为空", groups = { AddGroup.class })
    private String company;

    @ApiModelProperty(value = "业主姓名", required = true)
    @NotBlank(message = "业主姓名不能为空", groups = { AddGroup.class })
    private String userName;

    @ApiModelProperty(value = "身份证号")
    private String idcard;

    @ApiModelProperty(value = "手机号码")
    private String phonenumber;

    @ApiModelProperty(value = "拥有的数量")
    private Long ownCount;


}
