package io.bbw.dmc.event.listener;

import io.bbw.dmc.event.Event;
import io.bbw.dmc.event.ProductCategoryCreatedEvent;
import io.bbw.dmc.service.MessageService;
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
        }
    }
}
