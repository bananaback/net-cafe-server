package dev.bananaftmeo.netcafeserver.services.messageservices;

import java.time.LocalDateTime;
import java.util.List;

import dev.bananaftmeo.netcafeserver.models.dtos.MessageDTO;

public interface IMessageService {
    List<MessageDTO> getAllMessagesForUserInTimeRange(Long userId, LocalDateTime beginTime,
            LocalDateTime endTime);

    void sendMessageForUser(Long userId, String messageContent);

    void sendMessageForAdmin(Long userId, String messageContent);
}
