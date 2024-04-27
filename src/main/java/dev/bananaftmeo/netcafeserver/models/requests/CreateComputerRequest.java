package dev.bananaftmeo.netcafeserver.models.requests;
import com.google.firebase.database.annotations.NotNull;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateComputerRequest {
    @NotBlank(message = "Configuration could not be blank.")
    private String configuration;
    @NotNull
    private float pricePerHour;
    @NotNull
    private Long roomId;
}
