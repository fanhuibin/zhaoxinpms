package com.zhaoxinms.payment.model.paymentpreaccount;

import lombok.Data;
import java.util.List;
import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
public class PaymentPreAccountPayForm {

    private String id;

    /** 资源id */
    @JsonProperty("resourceId")
    private String resourceId;

    /** 商业区 */
    @JsonProperty("block")
    private String block;

    @JsonProperty("feeItemId")
    private String feeItemId;

    @JsonProperty("amt")
    private String amt;

    @JsonProperty("payMoney")
    private String payMoney;
}