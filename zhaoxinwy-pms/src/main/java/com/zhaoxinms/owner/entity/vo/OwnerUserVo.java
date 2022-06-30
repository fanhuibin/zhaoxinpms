package com.zhaoxinms.owner.entity.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;



/**
 * 业主信息视图对象 owner_user
 *
 * @author cycberform
 * @date 2022-02-23
 */
@Data
@ApiModel("业主信息视图对象")
public class OwnerUserVo {

	private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    private String id;
    
    /**
     * 公司名
     */
    @Excel(name = "公司名")
    @ApiModelProperty("公司名")
    private String company;
	
    /**
     * 业主姓名
     */
	@Excel(name = "业主姓名")
	@ApiModelProperty("业主姓名")
	private String userName;

    /**
     * 身份证号
     */
	@Excel(name = "身份证号")
	@ApiModelProperty("身份证号")
	private String idcard;

    /**
     * 手机号码
     */
	@Excel(name = "手机号码")
	@ApiModelProperty("手机号码")
	private String phonenumber;

    /**
     * 用户性别（0男 1女 2未知）
     */
    @Excel(name = "用户性别")
	@ApiModelProperty("用户性别（0男 1女 2未知）")
	private String sex;

    /**
     * 帐号状态（0正常 1停用）
     */
    @Excel(name = "帐号状态")
	@ApiModelProperty("帐号状态（0正常 1停用）")
	private String status;

    /**
     * 在租的数量
     */
	@Excel(name = "在租的数量")
	@ApiModelProperty("在租的数量")
	private Long rentedCount;

    /**
     * 拥有的数量
     */
	@Excel(name = "拥有的数量")
	@ApiModelProperty("拥有的数量")
	private Long ownCount;

    /**
     * 备注
     */
	@Excel(name = "备注")
	@ApiModelProperty("备注")
	private String remark;

	@ApiModelProperty("创建时间")
	private Date createTime;
}
