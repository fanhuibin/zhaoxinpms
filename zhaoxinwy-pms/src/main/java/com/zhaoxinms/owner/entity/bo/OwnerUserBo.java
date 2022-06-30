package com.zhaoxinms.owner.entity.bo;

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
 * 业主信息业务对象 owner_user
 *
 * @author cycberform
 * @date 2022-02-23
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("业主信息业务对象")
public class OwnerUserBo extends BaseEntity {

    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID")
    private Long id;
    
    /**
     * 业主姓名
     */
    @ApiModelProperty(value = "公司名", required = true)
    @NotBlank(message = "公司名不能为空", groups = { AddGroup.class, EditGroup.class })
    private String company;

    /**
     * 业主姓名
     */
    @ApiModelProperty(value = "业主姓名", required = true)
    @NotBlank(message = "业主姓名不能为空", groups = { AddGroup.class, EditGroup.class })
    private String userName;

    /**
     * 身份证号
     */
    @ApiModelProperty(value = "身份证号")
    @NotBlank(message = "身份证号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String idcard;

    /**
     * 用户昵称
     */
    @ApiModelProperty(value = "用户昵称")
    private String nickName;

    /**
     * 用户类型（00系统用户）
     */
    @ApiModelProperty(value = "用户类型（00系统用户）")
    private String userType;

    /**
     * 用户邮箱
     */
    @ApiModelProperty(value = "用户邮箱")
    private String email;

    /**
     * 手机号码
     */
    @ApiModelProperty(value = "手机号码")
    @NotBlank(message = "手机号码不能为空", groups = { AddGroup.class, EditGroup.class })
    private String phonenumber;

    /**
     * 用户性别（0男 1女 2未知）
     */
    @ApiModelProperty(value = "用户性别（0男 1女 2未知）")
    @NotBlank(message = "性别不能为空", groups = { AddGroup.class, EditGroup.class })
    private String sex;

    /**
     * 头像地址
     */
    @ApiModelProperty(value = "头像地址")
    private String avatar;

    /**
     * 帐号状态（0正常 1停用）
     */
    @ApiModelProperty(value = "帐号状态（0正常 1停用）")
    private String status;

    /**
     * 在租的数量
     */
    @ApiModelProperty(value = "在租的数量")
    private Long rentedCount;

    /**
     * 拥有的数量
     */
    @ApiModelProperty(value = "拥有的数量")
    private Long ownCount;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @ApiModelProperty(value = "删除标志（0代表存在 2代表删除）")
    private String delFlag;


}
