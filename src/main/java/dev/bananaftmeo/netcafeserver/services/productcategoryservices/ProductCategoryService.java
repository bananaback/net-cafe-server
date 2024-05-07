package dev.bananaftmeo.netcafeserver.services.productcategoryservices;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.bananaftmeo.netcafeserver.exceptions.ProductCategoryCreationException;
import dev.bananaftmeo.netcafeserver.models.ProductCategory;
import dev.bananaftmeo.netcafeserver.models.requests.CreateProductCategoryRequest;
import dev.bananaftmeo.netcafeserver.repositories.ProductCategoryRepository;

@Service
public class ProductCategoryService implements IProductCategoryService {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Override
    public void createProductCategory(CreateProductCategoryRequest createProductCategoryRequest)
            throws ProductCategoryCreationException {
        ProductCategory existingProductCategory = productCategoryRepository
                .findByName(createProductCategoryRequest.getCategoryName());
        if (existingProductCategory != null) {
            throw new ProductCategoryCreationException(
                    "Product category with name " + createProductCategoryRequest.getCategoryName() + " already exist.");
        }
        ProductCategory productCategory = new ProductCategory();
        productCategory.setName(createProductCategoryRequest.getCategoryName());
        productCategory.setImageLink(createProductCategoryRequest.getImageLink());

        productCategoryRepository.save(productCategory);
    }

    @Override
    public List<ProductCategory> getAllCategories() {
        return productCategoryRepository.findAll();
    }

    @Override
    public ProductCategory getProductCategoryById(Long id) {
        return productCategoryRepository.findById(id).get();
    }

    @Override
    public void updateProductCategory(Long id, CreateProductCategoryRequest productCategory) {
        ProductCategory existingProductCategory = productCategoryRepository.findById(id).get();
        existingProductCategory.setName(productCategory.getCategoryName());
        existingProductCategory.setImageLink(productCategory.getImageLink());
        productCategoryRepository.save(existingProductCategory);
    }

    @Override
    public void deleteProductCategory(Long id) {
        ProductCategory existingProductCategory = productCategoryRepository.findById(id).get();
        productCategoryRepository.delete(existingProductCategory);
    }

}
