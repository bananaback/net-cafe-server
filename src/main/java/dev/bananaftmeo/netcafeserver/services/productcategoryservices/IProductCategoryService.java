package dev.bananaftmeo.netcafeserver.services.productcategoryservices;

import java.util.List;

import dev.bananaftmeo.netcafeserver.exceptions.ProductCategoryCreationException;
import dev.bananaftmeo.netcafeserver.models.ProductCategory;
import dev.bananaftmeo.netcafeserver.models.requests.CreateProductCategoryRequest;

public interface IProductCategoryService {
    void createProductCategory(CreateProductCategoryRequest createProductCategoryRequest)
            throws ProductCategoryCreationException;

    List<ProductCategory> getAllCategories();

    ProductCategory getProductCategoryById(Long id);

    void updateProductCategory(Long id, CreateProductCategoryRequest productCategory);

    void deleteProductCategory(Long id);
}
