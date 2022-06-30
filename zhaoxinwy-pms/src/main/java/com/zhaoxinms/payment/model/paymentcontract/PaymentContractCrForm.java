package com.zhaoxinms.payment.model.paymentcontract;

import lombok.Data;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.zhaoxinms.payment.entity.PaymentContractFeeEntity;

@Data
public class PaymentContractCrForm {
    /** 资源id */
    @JsonProperty("resourceId")
    @Length(min = 0, max = 50)
    private String resourceId;

    /** 资源类型 */
    @JsonProperty("resourceType")
    @Length(min = 0, max = 50)
    private String resourceType;

    /** 合同类型 */
    @JsonProperty("contractType")
    @NotBlank
    @Length(min = 0, max = 50)
    private String contractType;
    
    @JsonProperty("ownerId")
    private String ownerId;

    /** 商业区编码 */
    @JsonProperty("blockCode")
    @Length(min = 0, max = 50)
    private String blockCode;

    /** 开始使用时间 */
    @JsonProperty("beginDate")
    private Long beginDate;

    /** 租的时间 */
    @JsonProperty("period")
    private int period;

    /** 租金 */
    @JsonProperty("rentFee")
    private String rentFee;

    /** 结束使用时间 */
    @JsonProperty("endDate")
    private Long endDate;
    
    /** 公司名 */
    @JsonProperty("company")
    @NotBlank
    @Length(min = 0, max = 50)
    private String company;

    /** 租户姓名/业主姓名 */
    @JsonProperty("userName")
    @NotBlank
    @Length(min = 0, max = 50)
    private String userName;

    /** 租户身份证/业主身份证 */
    @JsonProperty("userIdcard")
    @NotBlank
    @Length(min = 0, max = 18)
    private String userIdcard;

    /** 联系方式 */
    @JsonProperty("userPhone")
    @NotBlank
    @Length(min = 0, max = 13)
    private String userPhone;

    /** 性别 */
    @JsonProperty("userGender")
    @NotBlank
    @Length(min = 0, max = 50)
    private String userGender;

    /** 从事的行业 */
    @JsonProperty("userTrade")
    @NotBlank
    @Length(min = 0, max = 50)
    private String userTrade;

    /** 行业的详细描述 */
    @JsonProperty("userTradeDetail")
    @Length(min = 0, max = 200)
    private String userTradeDetail;

    /** 描述 */
    @JsonProperty("description")
    @Length(min = 0, max = 500)
    private String description;

    /** 收费项 */
    @JsonProperty("contractFees")
    private List<PaymentContractFeeForm> contractFees;

}