package com.zhaoxinms.event;

import org.springframework.context.ApplicationEvent;
import com.zhaoxinms.baseconfig.entity.ConfigHouseBlockEntity;
import lombok.Data;

@Data
public class BlockEvent extends ApplicationEvent {

    private static final long serialVersionUID = -7343153614939095945L;
    public static final String STATE_DELETE = "delete";
    public String state;
    private ConfigHouseBlockEntity block;

    public BlockEvent(Object source, ConfigHouseBlockEntity block, String state) {
        super(source);
        this.state = state;
        this.block = block;
    }

}
