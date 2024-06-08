package io.bbw.dmc.event.listener;

import io.bbw.dmc.event.*;
import io.bbw.dmc.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ProductEventListener implements Listener {
    private final MessageService messageService;

    @Override
    public void handleEvent(Event event) {
        if (!(event instanceof ProductEvent)) return;

        if (event instanceof ProductCreatedEvent || event instanceof ProductUpdatedEvent || event instanceof ProductDeletedEvent || event instanceof ProductCategoryRemovedEvent) {
            messageService.sendMessage("/products", event);
        }
    }
}
