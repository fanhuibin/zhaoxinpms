package com.zhaoxinms.baseconfig.entity.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;



/**
 * 楼栋管理视图对象 config_building
 *
 * @author fanhuibin
 * @date 2022-06-22
 */
@Data
@ApiModel("楼栋管理视图对象")
public class ConfigBuildingVo {

	private static final long serialVersionUID = 1L;
	
	private String id;

    /**
     * 商铺区域
     */
	@Excel(name = "商铺区域")
	@ApiModelProperty("商铺区域")
	private String block;
	
    /**
     * 商铺区域
     */
    @Excel(name = "商铺区域")
    @ApiModelProperty("商铺区域")
    private String blockName;

    /**
     * 楼栋名
     */
	@Excel(name = "楼栋名")
	@ApiModelProperty("楼栋名")
	private String name;

    /**
     * 门牌号
     */
	@Excel(name = "门牌号")
	@ApiModelProperty("门牌号")
	private String number;


}
