package dev.bananaftmeo.netcafeserver.services.messageservices;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.bananaftmeo.netcafeserver.models.Message;
import dev.bananaftmeo.netcafeserver.models.requests.SendMessageRequest;
import dev.bananaftmeo.netcafeserver.repositories.MessageRepository;

@Service
public class MessageService implements IMessageService {

    @Autowired
    private MessageRepository messageRepository;
    @Override
    public void sendMessage(SendMessageRequest request) {
        Message sentMessage = new Message();
        sentMessage.setSenderId(request.getSenderId());
        sentMessage.setReceiverId(request.getReceiverId());
        sentMessage.setMessage(request.getMessage());
        sentMessage.setSentTime(LocalDateTime.now());
        messageRepository.save(sentMessage);

        
    }

    @Override
    public void getMessages(Long senderId, Long receiverId, String message) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getMessages'");
    }
    
}
