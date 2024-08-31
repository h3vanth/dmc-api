package io.bbw.dmc.event.listener;

import io.bbw.dmc.event.*;
import io.bbw.dmc.service.MessageService;
import io.formulate.common.event.Event;
import io.formulate.common.event.Listener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class UserEventListener implements Listener {
    private final MessageService messageService;

    @Override
    public void handleEvent(Event event) {
        if (event instanceof ProductCategoryCreatedEvent) {
            messageService.sendMessage("/categories", event);
        } else if (event instanceof UserConnectedEvent || event instanceof UserDisconnectEvent || event instanceof UserSubscribeEvent) {
            messageService.sendMessage("/user-sessions", event);
        }
    }
}
