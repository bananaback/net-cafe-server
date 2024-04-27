package dev.bananaftmeo.netcafeserver.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.bananaftmeo.netcafeserver.models.Computer;

import dev.bananaftmeo.netcafeserver.models.Room;

public interface ComputerRepository extends JpaRepository<Computer, Long> {
    List<Computer> findByRoom(Room room);
}
