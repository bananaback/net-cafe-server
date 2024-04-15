package dev.bananaftmeo.netcafeserver.models.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductCategoryRequest {
    @NotBlank(message = "Category name could not be blank.")
    private String categoryName;
    @NotBlank(message = "Image link could not be blank.")
    private String imageLink;
}