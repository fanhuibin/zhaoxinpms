package com.zhaoxinms.event;

import org.springframework.context.ApplicationEvent;
import com.zhaoxinms.baseconfig.entity.ConfigHouseBlockEntity;
import com.zhaoxinms.payment.entity.PaymentOrder;

import lombok.Data;

@Data
public class OrderEvent extends ApplicationEvent {

    private static final long serialVersionUID = -7343153614939095945L;
    public static final String ACTION_CREATE = "create";
    public static final String ACTION_OFFLINE_PAY = "offline_pay";
    public static final String ACTION_ONLINE_PAY = "online_pay";
    public static final String ACTION_CLOSE = "close";
    public static final String ACTION_REFUND = "refund";
    public String action;
    private PaymentOrder order;

    public OrderEvent(Object source, PaymentOrder order, String action) {
        super(source);
        this.action = action;
        this.order = order;
    }

}
