package dev.bananaftmeo.netcafeserver.services.roomservices;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import dev.bananaftmeo.netcafeserver.exceptions.RoomCreationException;
import dev.bananaftmeo.netcafeserver.models.Room;
import dev.bananaftmeo.netcafeserver.models.requests.CreateRoomRequest;
import dev.bananaftmeo.netcafeserver.repositories.RoomRepository;


@Service
public class RoomService implements IRoomService{


    @Autowired
    private RoomRepository roomRepository;

    @Override
    public void createRoom(CreateRoomRequest createRoomRequest)
            throws RoomCreationException {
        Room existingRoom = roomRepository
                .findByName(createRoomRequest.getName());
        if (existingRoom != null) {
            throw new RoomCreationException(
                    "Room with name " + createRoomRequest.getName() + " already exist.");
        }
        Room room = new Room();
        room.setName(createRoomRequest.getName());
        roomRepository.save(room);
    }

    @Override
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    @Override
    public Room getRoomById(Long id) {
        return roomRepository.findById(id).get();
    }

    @Override
    public void updateRoom(Long id, CreateRoomRequest room) {
        Room existingRoom = roomRepository.findById(id).get();
        existingRoom.setName(room.getName());
        roomRepository.save(existingRoom);
    }

    @Override
    public void deleteRoom(Long id) {
        Room existingRoom = roomRepository.findById(id).get();
        roomRepository.delete(existingRoom);
    }


    
    
}
