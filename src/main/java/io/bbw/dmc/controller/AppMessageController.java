package io.bbw.dmc.controller;

import java.security.Principal;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.bbw.dmc.service.ProductService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AppMessageController {

    private final ObjectMapper objectMapper;
    private final ProductService productService;

    // Can be present inside any controller
    // @RequestMapping at class level doesn't matter
    @MessageMapping("/message")
    @SendTo("/topic/products")
    public String handleMessage(Principal principal) throws JsonProcessingException {
        return objectMapper.writeValueAsString(productService.getProducts(principal.getName()));
    }
}
