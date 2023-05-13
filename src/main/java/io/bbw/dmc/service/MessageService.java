package io.bbw.dmc.service;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final ProductService productService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    public void sendMessage(String userId) {
        // NOTE: StringBuilder - fast but not thread safe
        simpMessagingTemplate.convertAndSend(new StringBuilder().append("/topic/").append(userId).append("/products").toString(), productService.getProducts(userId));
    }
}
