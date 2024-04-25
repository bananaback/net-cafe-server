package dev.bananaftmeo.netcafeserver.services.productservices;

import java.util.List;

import dev.bananaftmeo.netcafeserver.exceptions.ProductCreationException;
import dev.bananaftmeo.netcafeserver.models.Product;
import dev.bananaftmeo.netcafeserver.models.requests.CreateProductRequest;

public interface IProductService {
    void createProduct(CreateProductRequest request)
        throws ProductCreationException;
    List<Product> getAllProducts();

    List<Product> getProductsByCategoryId(Long categoryId);

    Product getProductById(Long id);

    void updateProduct(Long id, CreateProductRequest product );

    void deleteProduct(Long id);

}
