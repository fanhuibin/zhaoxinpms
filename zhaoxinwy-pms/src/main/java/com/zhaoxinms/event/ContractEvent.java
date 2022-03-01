package com.zhaoxinms.event;

import org.springframework.context.ApplicationEvent;

import com.zhaoxinms.payment.entity.PaymentContractEntity;

import lombok.Data;

@Data
public class ContractEvent extends ApplicationEvent {

    public static final String STATE_CANCEL = "cancel";
    public static final String STATE_ADD = "add";
    public static final String STATE_UPDATE = "update";
    private String state;
    private PaymentContractEntity contract;

    public ContractEvent(Object source, PaymentContractEntity contract, String state) {
        super(source);
        this.state = state;
        this.contract = contract;
    }
}
