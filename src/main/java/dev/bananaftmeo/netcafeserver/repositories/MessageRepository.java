package dev.bananaftmeo.netcafeserver.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import dev.bananaftmeo.netcafeserver.models.Message;
import dev.bananaftmeo.netcafeserver.models.dtos.MessageDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("SELECT new dev.bananaftmeo.netcafeserver.models.dtos.MessageDTO(m.id, m.sentTime, m.message, m.senderId, m.receiverId) "
            +
            "FROM Message m " +
            "WHERE (m.senderId = :userId OR m.receiverId = :userId) " +
            "AND m.sentTime BETWEEN :beginTime AND :endTime")
    List<MessageDTO> findMessagesByUserIdAndTimeRange(Long userId, LocalDateTime beginTime, LocalDateTime endTime);
}
