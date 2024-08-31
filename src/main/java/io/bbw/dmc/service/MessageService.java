package io.bbw.dmc.service;

import io.formulate.common.event.Event;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageService {
    private final SimpMessagingTemplate simpMessagingTemplate;

    public void sendMessage(String prefix, Object payload) {
        if (payload instanceof Event event) {
            simpMessagingTemplate.convertAndSend("/" + event.getUserId() + prefix, payload);
        }
    }
}
