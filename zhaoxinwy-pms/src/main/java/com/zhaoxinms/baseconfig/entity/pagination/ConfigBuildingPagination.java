package com.zhaoxinms.baseconfig.entity.pagination;

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
 * 楼栋管理业务对象 config_building
 *
 * @author fanhuibin
 * @date 2022-06-22
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("楼栋管理业务对象")
public class ConfigBuildingPagination extends Pagination {

    @ApiModelProperty(value = "商铺区域", required = true)
    @NotNull(message = "商铺区域不能为空", groups = { AddGroup.class })
    private String block;

    @ApiModelProperty(value = "楼栋名", required = true)
    @NotBlank(message = "楼栋名不能为空", groups = { AddGroup.class })
    private String name;

    @ApiModelProperty(value = "门牌号", required = true)
    @NotBlank(message = "门牌号不能为空", groups = { AddGroup.class })
    private String number;


}
