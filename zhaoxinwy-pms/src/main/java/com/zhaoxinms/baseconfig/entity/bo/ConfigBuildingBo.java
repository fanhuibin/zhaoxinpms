package com.zhaoxinms.baseconfig.entity.bo;

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
 * 楼栋管理业务对象 config_building
 *
 * @author fanhuibin
 * @date 2022-06-22
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("楼栋管理业务对象")
public class ConfigBuildingBo extends BaseEntity {

    /**
     * $column.columnComment
     */
    @ApiModelProperty(value = "$column.columnComment")
    private Long id;

    /**
     * 商铺区域
     */
    @ApiModelProperty(value = "商铺区域", required = true)
    @NotNull(message = "商铺区域不能为空", groups = { AddGroup.class, EditGroup.class })
    private String block;

    /**
     * 楼栋名
     */
    @ApiModelProperty(value = "楼栋名", required = true)
    @NotBlank(message = "楼栋名不能为空", groups = { AddGroup.class, EditGroup.class })
    private String name;

    /**
     * 门牌号
     */
    @ApiModelProperty(value = "门牌号", required = true)
    @NotBlank(message = "门牌号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String number;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @ApiModelProperty(value = "删除标志（0代表存在 2代表删除）")
    private String delFlag;


}
