package com.zhaoxinms.payment.model.paymentmeter;

import lombok.Data;
import java.util.Date;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
public class PaymentMeterListVO {
    /** 主键 */
    private String id;

    /** 资源名 */
    @JsonProperty("resourceName")
    private String resourceName;

    /** 费用类型 */
    @JsonProperty("feeItemName")
    private String feeItemName;

    /** 本期读表时间 */
    // 日期输出格式化
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty("currentIndexDate")
    private Date currentIndexDate;

    /** 本期读数 */
    @JsonProperty("currentIndex")
    private String currentIndex;

    /** 上期读数 */
    @JsonProperty("lastIndex")
    private String lastIndex;

    /** 上期读表时间 */
    // 日期输出格式化
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty("lastIndexDate")
    private Date lastIndexDate;

    /** 倍率 */
    @JsonProperty("multiple")
    private String multiple;

    /** 损耗 */
    @JsonProperty("loss")
    private String loss;

    /** 最终数量 */
    @JsonProperty("result")
    private String result;
}