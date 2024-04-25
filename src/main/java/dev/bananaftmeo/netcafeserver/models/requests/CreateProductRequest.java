package dev.bananaftmeo.netcafeserver.models.requests;

import com.google.firebase.database.annotations.NotNull;

import dev.bananaftmeo.netcafeserver.models.ProductCategory;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductRequest {
    @NotBlank(message = "Product description could not be blank.")
    private String description;
    @NotBlank(message = "Product name could not be blank.")
    private String name;
    @NotNull
    private float price;
    @NotNull
    private int remainQuantity;
    @NotNull
    private Long categoryId;
}
