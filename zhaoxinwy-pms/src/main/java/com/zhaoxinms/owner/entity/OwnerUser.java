package com.zhaoxinms.owner.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.zhaoxinms.common.core.mybatisplus.BaseEntity;

/**
 * 业主信息对象 owner_user
 *
 * @author cycberform
 * @date 2022-02-23
 */
@Data
@Accessors(chain = true)
@TableName("owner_user")
public class OwnerUser extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 用户ID
     */
    @TableId(value = "id")
    private Long id;
    private String unionId;
    private String openId;
    /**
     * 单位名
     */
    private String company;
    /**
     * 业主姓名
     */
    private String userName;
    /**
     * 身份证号
     */
    private String idcard;
    /**
     * 用户昵称
     */
    private String nickName;
    /**
     * 用户类型（00系统用户）
     */
    private String userType;
    /**
     * 用户邮箱
     */
    private String email;
    /**
     * 手机号码
     */
    private String phonenumber;
    /**
     * 用户性别（0男 1女 2未知）
     */
    private String sex;
    /**
     * 头像地址
     */
    private String avatar;
    /**
     * 帐号状态（0正常 1停用）
     */
    private String status;
    /**
     * 在租的数量
     */
    private Long rentedCount;
    /**
     * 拥有的数量
     */
    private Long ownCount;
    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @TableLogic
    private String delFlag;
    
    //是否完成了手机绑定
    private int isBind;
}
