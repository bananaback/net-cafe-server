package dev.bananaftmeo.netcafeserver.services.computerservices;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.bananaftmeo.netcafeserver.exceptions.ComputerCreationException;
import dev.bananaftmeo.netcafeserver.models.Computer;
import dev.bananaftmeo.netcafeserver.models.Room;
import dev.bananaftmeo.netcafeserver.models.requests.CreateComputerRequest;
import dev.bananaftmeo.netcafeserver.repositories.ComputerRepository;
import dev.bananaftmeo.netcafeserver.services.roomservices.RoomService;

@Service
public class ComputerService implements IComputerService{

    @Autowired
    private ComputerRepository computerRepository;

    @Autowired
    private RoomService roomService;
    @Override
    public void createComputer(CreateComputerRequest request) throws ComputerCreationException {
        Computer newComputer = new Computer();
        newComputer.setConfiguration(request.getConfiguration());
        newComputer.setPricePerHour(request.getPricePerHour());
        Room room = roomService.getRoomById(request.getRoomId());
        newComputer.setRoom(room);
        computerRepository.save(newComputer);
    }

    @Override
    public List<Computer> getAllComputers() {
        return computerRepository.findAll();
    }

    @Override
    public List<Computer> getComputersByRoomId(Long roomId) {
        Room room = roomService.getRoomById(roomId);
        List<Computer> computers = computerRepository.findByRoom(room);
        return computers;
    }

    @Override
    public Computer getComputerById(Long id) {
        return computerRepository.findById(id).get();
    }

    @Override
    public void updateComputer(Long id, CreateComputerRequest product) {
        Computer existingComputer = computerRepository.findById(id).get();
        existingComputer.setConfiguration(product.getConfiguration());
        existingComputer.setPricePerHour(product.getPricePerHour());
        Room room = roomService.getRoomById(product.getRoomId());
        existingComputer.setRoom(room);
        computerRepository.save(existingComputer);
    }

    @Override
    public void deleteComputer(Long id) {
        Computer existingComputer = computerRepository.findById(id).get();
        computerRepository.delete(existingComputer);
    }
     
}
