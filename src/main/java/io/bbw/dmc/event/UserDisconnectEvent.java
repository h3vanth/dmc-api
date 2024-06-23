package io.bbw.dmc.event;

import lombok.ToString;

@ToString(callSuper = true)
public class UserDisconnectEvent extends SessionEvent {
    private static final String TYPE = "UserDisconnected";

    public UserDisconnectEvent() {
        super(TYPE);
    }
}
