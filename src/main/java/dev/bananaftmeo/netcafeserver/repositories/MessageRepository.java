package dev.bananaftmeo.netcafeserver.repositories;

import dev.bananaftmeo.netcafeserver.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {

}
