package io.bbw.dmc.event;

import lombok.ToString;

@ToString(callSuper = true)
public class UserSubscribeEvent extends SessionEvent {
    private static final String TYPE = "UserSubscribe";

    public UserSubscribeEvent() {
        super(TYPE);
    }
}
