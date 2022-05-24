package com.zhaoxinms.event;

import org.springframework.context.ApplicationEvent;

import com.zhaoxinms.owner.entity.OwnerUser;

import lombok.Data;

@Data
public class OwnerUserEvent extends ApplicationEvent {

    public static final String STATE_CHANGE_PHONE = "changePhone";
    private String state;
    private OwnerUser user;

    public OwnerUserEvent(Object source, OwnerUser user, String state) {
        super(source);
        this.state = state;
        this.user = user;
    }
}
