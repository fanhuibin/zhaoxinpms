package com.zhaoxinms.payment.model.paymentpreaccount;

import lombok.Data;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
public class PaymentPreAccountListVO {
    /** 主键 */
    private String id;

    /** 资源id */
    @JsonProperty("resourceId")
    private String resourceId;

    /** 商业区 */
    @JsonProperty("block")
    private String block;

    /** 费用 */
    @JsonProperty("feeItemId")
    private String feeItemId;

    /** 费用名 */
    @JsonProperty("feeItemName")
    private String feeItemName;

    /** 金额 */
    @JsonProperty("amt")
    private String amt;

    /** 资源名 */
    @JsonProperty("resourceName")
    private String resourceName;

    /** 收费项目 */
    @JsonProperty("USE_FEE_ITEM")
    private String useFeeItem;
}