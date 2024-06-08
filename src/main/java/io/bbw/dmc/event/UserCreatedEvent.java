package io.bbw.dmc.event;

import lombok.ToString;

@ToString(callSuper = true)
public class UserCreatedEvent extends UserEvent {
    public static final String TYPE = "UserCreated";

    public UserCreatedEvent() {
        super(TYPE);
    }
}
