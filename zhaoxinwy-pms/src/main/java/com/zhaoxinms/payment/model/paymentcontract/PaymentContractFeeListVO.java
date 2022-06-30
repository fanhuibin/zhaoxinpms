package com.zhaoxinms.payment.model.paymentcontract;

import lombok.Data;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
public class PaymentContractFeeListVO {
    /** 主键 */
    private String id;

    @JsonProperty("resourceCode")
    private String resourceCode;

    @JsonProperty("blockCode")
    private String blockCode;

    /** 资源id */
    @JsonProperty("resourceId")
    private String resourceId;

    /** 资源名 */
    @JsonProperty("resourceName")
    private String resourceName;
    
    @JsonProperty("times")
    private int times;

    @JsonProperty("feeItemId")
    private String feeItemId;

    @JsonProperty("feeItemName")
    private String feeItemName;

    @JsonProperty("userName")
    private String userName;

    @JsonProperty("contractId")
    private String contractId;

    /** 开始使用时间 */
    // 日期输出格式化
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty("beginDate")
    private Date beginDate;

    /** 下次缴费时间 */
    // 日期输出格式化
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty("nextBillDate")
    private Date nextBillDate;

    /** 结束使用时间 */
    // 日期输出格式化
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty("endDate")
    private Date endDate;
}