package dev.bananaftmeo.netcafeserver.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.bananaftmeo.netcafeserver.exceptions.OrderCreationException;
import dev.bananaftmeo.netcafeserver.models.requests.SendMessageRequest;
import dev.bananaftmeo.netcafeserver.models.responses.ErrorResponse;
import dev.bananaftmeo.netcafeserver.services.messageservices.MessageService;

@RestController
@RequestMapping("api/messages")
public class MessageController {
    @Autowired
    private MessageService messageService;
    @PostMapping
    public ResponseEntity<?> sendMessage(SendMessageRequest request) {
        messageService.sendMessage(request);
        return ResponseEntity.ok().body("MESSAGE SENT AT " + request.getSentTime());
    }
}
