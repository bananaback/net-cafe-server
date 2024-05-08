package dev.bananaftmeo.netcafeserver.services.messageservices;

import dev.bananaftmeo.netcafeserver.models.requests.SendMessageRequest;

public interface IMessageService {
    void sendMessage(SendMessageRequest request);

    void getMessages(Long senderId, Long receiverId, String message);
}
