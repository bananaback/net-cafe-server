package dev.bananaftmeo.netcafeserver.controllers;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import dev.bananaftmeo.netcafeserver.exceptions.RoomCreationException;
import dev.bananaftmeo.netcafeserver.models.Room;
import dev.bananaftmeo.netcafeserver.models.requests.CreateRoomRequest;
import dev.bananaftmeo.netcafeserver.models.responses.ErrorResponse;
import dev.bananaftmeo.netcafeserver.models.responses.RoomResponse;
import dev.bananaftmeo.netcafeserver.services.roomservices.IRoomService;

@RestController
@RequestMapping("api/rooms")
public class RoomController {
    @Autowired
    private IRoomService roomService;

    @PostMapping
    public ResponseEntity<?> createRoom(@Validated @RequestBody CreateRoomRequest request,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Handle validation errors
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse("Validation error: " + bindingResult.getAllErrors()));
        }
        try {
            roomService.createRoom(request);
            return ResponseEntity.ok().body("Room created successfully.");
        } catch (RoomCreationException ex) {
            return ResponseEntity.badRequest().body(new ErrorResponse(ex.getErrorMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllRooms() {
        List<Room> rooms = roomService.getAllRooms();
        return ResponseEntity.ok().body(new RoomResponse(rooms));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRoomById(@PathVariable Long id) {
        try {
            Room room = roomService.getRoomById(id);
            return ResponseEntity.ok().body(room);
        } catch (NoSuchElementException ex) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse("Room with id " + id + " does not exist."));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRoom(@PathVariable Long id,
            @Validated @RequestBody CreateRoomRequest createRoomRequest,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Handle validation errors
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse("Validation error: " + bindingResult.getAllErrors()));
        }
        try {
            roomService.updateRoom(id, createRoomRequest);
            return ResponseEntity.ok().body("Update room successfully.");
        } catch (NoSuchElementException ex) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse("Room with id " + id + " does not exist."));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRoom(@PathVariable Long id) {
        try {
            roomService.deleteRoom(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException ex) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse("Room with id " + id + " does not exist."));
        }
    }
}