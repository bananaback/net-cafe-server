package dev.bananaftmeo.netcafeserver.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.bananaftmeo.netcafeserver.models.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {
    Room findByName(String name);
}
