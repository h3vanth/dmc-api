package io.bbw.dmc.event;

import io.formulate.common.event.Event;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class SessionEvent extends Event {
    protected int currentSessions;

    public SessionEvent(String type) {
        super(type);
    }
}
