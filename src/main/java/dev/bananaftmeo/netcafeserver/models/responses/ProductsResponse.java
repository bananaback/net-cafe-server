package dev.bananaftmeo.netcafeserver.models.responses;


import java.util.List;

import dev.bananaftmeo.netcafeserver.models.Product;
import dev.bananaftmeo.netcafeserver.models.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductsResponse {
    private List<Product> products;
}
