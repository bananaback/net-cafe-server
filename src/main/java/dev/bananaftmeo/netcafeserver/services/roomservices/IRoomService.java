package dev.bananaftmeo.netcafeserver.services.roomservices;

import java.util.List;

import dev.bananaftmeo.netcafeserver.exceptions.RoomCreationException;
import dev.bananaftmeo.netcafeserver.models.Room;
import dev.bananaftmeo.netcafeserver.models.requests.CreateRoomRequest;

public interface IRoomService {
    void createRoom(CreateRoomRequest createroomRequest)
            throws RoomCreationException;

    List<Room> getAllRooms();

    Room getRoomById(Long id);

    void updateRoom(Long id, CreateRoomRequest room);

    void deleteRoom(Long id);
}
