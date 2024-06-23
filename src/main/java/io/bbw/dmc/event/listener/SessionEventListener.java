package io.bbw.dmc.event.listener;

import io.bbw.dmc.event.SessionEvent;
import io.bbw.dmc.event.UserConnectedEvent;
import io.bbw.dmc.event.UserDisconnectEvent;
import io.bbw.dmc.event.UserSubscribeEvent;
import io.bbw.dmc.event.handler.EventHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.AbstractSubProtocolEvent;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class SessionEventListener {
    private final EventHandler eventHandler;
    private final SimpUserRegistry simpUserRegistry;

    private void emitEvent(String userId, SessionEvent event) {
        event.setUserId(userId);
        Optional<SimpUser> simpUser = simpUserRegistry.getUsers().stream().filter(user -> user.getName().equals(userId)).findFirst();
        if (simpUser.isPresent()) {
            event.setCurrentSessions(simpUser.get().getSessions().size());
        }

        eventHandler.emitEvent(event);
    }

    @EventListener({AbstractSubProtocolEvent.class})
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof AbstractSubProtocolEvent aspe) {
            String userId = aspe.getUser().getName();
            if (event instanceof SessionConnectedEvent) {
                emitEvent(userId, new UserConnectedEvent());
            } else if (event instanceof SessionDisconnectEvent) {
                // Question:
                // SessionDisconnectEvent is emitted when server shutdown is initiated
                // But, client is not getting the event below
                // Not a problem but check why
                emitEvent(userId, new UserDisconnectEvent());
            } else if (event instanceof SessionSubscribeEvent) {
                // Only to send the currentSessions
                emitEvent(userId, new UserSubscribeEvent());
            }
        }
    }
}
