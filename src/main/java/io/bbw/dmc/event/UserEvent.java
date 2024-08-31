package io.bbw.dmc.event;

import io.formulate.common.event.Event;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class UserEvent extends Event {
    protected String email;

    public UserEvent(String type) {
        super(type);
    }
}
