package dev.bananaftmeo.netcafeserver.services.computerservices;

import java.util.List;

import dev.bananaftmeo.netcafeserver.exceptions.ComputerCreationException;
import dev.bananaftmeo.netcafeserver.models.Computer;
import dev.bananaftmeo.netcafeserver.models.requests.CreateComputerRequest;

public interface IComputerService {
    void createComputer(CreateComputerRequest request)
        throws ComputerCreationException;
    List<Computer> getAllComputers();

    List<Computer> getComputersByRoomId(Long roomId);

    Computer getComputerById(Long id);

    void updateComputer(Long id, CreateComputerRequest computer );

    void deleteComputer(Long id);
}
