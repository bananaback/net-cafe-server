package dev.bananaftmeo.netcafeserver.services.messageservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.bananaftmeo.netcafeserver.models.Message;
import dev.bananaftmeo.netcafeserver.models.dtos.MessageDTO;
import dev.bananaftmeo.netcafeserver.repositories.MessageRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public List<MessageDTO> getAllMessagesForUserInTimeRange(Long userId, LocalDateTime beginTime,
            LocalDateTime endTime) {
        return messageRepository.findMessagesByUserIdAndTimeRange(userId, beginTime, endTime);
    }

    public void sendMessageForUser(Long userId, String messageContent) {
        Message message = new Message();
        message.setSenderId(userId);
        message.setReceiverId(null);
        message.setMessage(messageContent);
        message.setSentTime(LocalDateTime.now());
        messageRepository.save(message);
    }

    public void sendMessageForAdmin(Long userId, String messageContent) {
        Message message = new Message();
        message.setSenderId(null);
        message.setReceiverId(userId);
        message.setMessage(messageContent);
        message.setSentTime(LocalDateTime.now());
        messageRepository.save(message);
    }
}
