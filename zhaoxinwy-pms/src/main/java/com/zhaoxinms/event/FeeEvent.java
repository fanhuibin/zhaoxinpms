package com.zhaoxinms.event;

import org.springframework.context.ApplicationEvent;

import com.zhaoxinms.baseconfig.entity.ConfigFeeItemEntity;
import com.zhaoxinms.payment.entity.PaymentContractEntity;

import lombok.Data;

@Data
public class FeeEvent extends ApplicationEvent {

    public static final String STATE_DELETE = "delete";
    private String state;
    private ConfigFeeItemEntity item;

    public FeeEvent(Object source, ConfigFeeItemEntity item, String state) {
        super(source);
        this.state = state;
        this.item = item;
    }
}
