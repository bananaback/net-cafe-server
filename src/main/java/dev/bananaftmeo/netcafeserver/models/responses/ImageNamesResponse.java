package dev.bananaftmeo.netcafeserver.models.responses;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImageNamesResponse {
    private List<String> imageNames;
    private int size;
}
