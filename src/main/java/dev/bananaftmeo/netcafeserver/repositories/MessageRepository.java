package dev.bananaftmeo.netcafeserver.repositories;
import java.util.List;
import dev.bananaftmeo.netcafeserver.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface MessageRepository extends JpaRepository<Message, Long>  {
    
}
