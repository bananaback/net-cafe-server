package dev.bananaftmeo.netcafeserver.models.responses;
import java.util.List;

import dev.bananaftmeo.netcafeserver.models.Computer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ComputerResponse {
    private List<Computer> computers;
}
