package com.zhaoxinms.payment.model.paymentmeterindex;

import lombok.Data;
import java.util.List;
import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
public class PaymentMeterIndexCrForm {
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
    @JsonProperty("indexDate")
    private Long indexDate;

    /** 当前表读数 */
    @JsonProperty("index")
    private Integer index;

}