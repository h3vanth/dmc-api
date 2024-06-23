package io.bbw.dmc.event;

import lombok.ToString;

@ToString(callSuper = true)
public class UserConnectedEvent extends SessionEvent {
    private static final String TYPE = "UserConnected";

    public UserConnectedEvent() {
        super(TYPE);
    }
}
