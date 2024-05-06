package dev.bananaftmeo.netcafeserver.controllers;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.bananaftmeo.netcafeserver.exceptions.ComputerCreationException;
import dev.bananaftmeo.netcafeserver.models.Computer;
import dev.bananaftmeo.netcafeserver.models.requests.CreateComputerRequest;
import dev.bananaftmeo.netcafeserver.models.responses.ErrorResponse;
import dev.bananaftmeo.netcafeserver.models.responses.ComputerResponse;
import dev.bananaftmeo.netcafeserver.services.computerservices.IComputerService;

@RestController
@RequestMapping("api/computers")
public class ComputerController {
    @Autowired
    private IComputerService computerService;

    @PostMapping
    public ResponseEntity<?> createComputer(@Validated @RequestBody CreateComputerRequest request,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Handle validation errors
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse("Validation error: " + bindingResult.getAllErrors()));
        }
        try {
            computerService.createComputer(request);
            return ResponseEntity.ok().body("Computer created successfully.");
        } catch (ComputerCreationException ex) {
            return ResponseEntity.badRequest().body(new ErrorResponse(ex.getErrorMessage()));
        } catch (NoSuchElementException ex) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse("Room with id " + request.getRoomId() + " does not exist."));
        }
    }
    @GetMapping
    public ResponseEntity<?> getAllComputers() {
        List<Computer> computers = computerService.getAllComputers();
        return ResponseEntity.ok().body(new ComputerResponse(computers));
    }

    @GetMapping("/room/{roomId}")
    public ResponseEntity<?> getComputersByComputerCategory(@PathVariable Long roomId){
        try {
            List<Computer> computers = computerService.getComputersByRoomId(roomId);
            return ResponseEntity.ok().body(computers);
        } catch (NoSuchElementException ex) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse("Room with id " + roomId + " does not exist"));
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getComputerById(@PathVariable Long id){
        try {
            Computer computer = computerService.getComputerById(id);
            return ResponseEntity.ok().body(computer);
        } catch (NoSuchElementException ex) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse("Computer with id " + id + " does not exist."));
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateComputer(@PathVariable Long id,
            @Validated @RequestBody CreateComputerRequest createComputerRequest,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Handle validation errors
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse("Validation error: " + bindingResult.getAllErrors()));
        }
        try {
            computerService.updateComputer(id, createComputerRequest);
            return ResponseEntity.ok().body("Update Computer successfully.");
        } catch (NoSuchElementException ex) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse("Computer with id " + id + " or Room with id " + createComputerRequest.getRoomId() +" does not exist."));
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteComputer(@PathVariable Long id) {
        try {
            computerService.deleteComputer(id);
            return ResponseEntity.ok().body("Delete Computer with id " + id + " successfully.");
        } catch (NoSuchElementException ex) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse("Computer with id " + id + " does not exist."));
        }
    }
}
