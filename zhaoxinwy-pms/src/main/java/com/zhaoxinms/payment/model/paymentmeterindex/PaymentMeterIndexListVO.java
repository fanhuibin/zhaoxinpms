package com.zhaoxinms.payment.model.paymentmeterindex;

import lombok.Data;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
public class PaymentMeterIndexListVO {
    /** 主键 */
    private String id;

    /** 收费的资源 */
    @JsonProperty("resourceId")
    private String resourceId;

    /** 资源名 */
    @JsonProperty("resourceName")
    private String resourceName;

    /** 收费项id */
    @JsonProperty("feeItemId")
    private String feeItemId;

    /** 读数时间 */
    // 日期输出格式化
    @JsonProperty("indexDate")
    private Date indexDate;

    /** 当前表读数 */
    @JsonProperty("index")
    private Long index;

}