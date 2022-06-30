package com.zhaoxinms.payment.model.paymentcontract;

import lombok.Data;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
public class PaymentContractListVO {
    /** 主键 */
    private String id;

    /** 资源id */
    @JsonProperty("resourceId")
    private String resourceId;

    /** 资源名 */
    @JsonProperty("resourceName")
    private String resourceName;

    /** 合同类型 */
    @JsonProperty("contractType")
    private String contractType;

    /** 商铺状态 */
    @JsonProperty("resourceType")
    private String resourceType;

    /** 开始使用时间 */
    // 日期输出格式化
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("beginDate")
    private Date beginDate;

    /** 结束使用时间 */
    // 日期输出格式化
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("endDate")
    private Date endDate;
    
    /** 公司名 */
    @JsonProperty("company")
    private String company;

    /** 租户姓名/业主姓名 */
    @JsonProperty("userName")
    private String userName;

    /** 租户身份证/业主身份证 */
    @JsonProperty("userIdcard")
    private String userIdcard;

    /** 联系方式 */
    @JsonProperty("userPhone")
    private String userPhone;

    /** 性别 */
    @JsonProperty("userGender")
    private String userGender;

    /** 从事的行业 */
    @JsonProperty("userTrade")
    private String userTrade;

    /** 删除时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("deleteTime")
    private Date deleteTime;
}