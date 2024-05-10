package dev.bananaftmeo.netcafeserver.controllers;

import dev.bananaftmeo.netcafeserver.models.dtos.MessageDTO;
import dev.bananaftmeo.netcafeserver.services.messageservices.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<MessageDTO>> getAllMessagesForUserInTimeRange(
            @PathVariable Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime beginTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        List<MessageDTO> messages = messageService.getAllMessagesForUserInTimeRange(userId, beginTime, endTime);
        return ResponseEntity.ok(messages);
    }

    @PostMapping("/user/{userId}/send")
    public ResponseEntity<Void> sendMessageForUser(
            @PathVariable Long userId,
            @RequestBody String messageContent) {
        messageService.sendMessageForUser(userId, messageContent);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/admin/{userId}/send")
    public ResponseEntity<Void> sendMessageForAdmin(
            @PathVariable Long userId,
            @RequestBody String messageContent) {
        messageService.sendMessageForAdmin(userId, messageContent);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
