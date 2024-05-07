package dev.bananaftmeo.netcafeserver.services.productservices;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.bananaftmeo.netcafeserver.exceptions.ProductCreationException;
import dev.bananaftmeo.netcafeserver.models.Product;
import dev.bananaftmeo.netcafeserver.models.ProductCategory;
import dev.bananaftmeo.netcafeserver.models.requests.CreateProductRequest;
import dev.bananaftmeo.netcafeserver.repositories.ProductRepository;
import dev.bananaftmeo.netcafeserver.services.productcategoryservices.ProductCategoryService;

@Service
public class ProductService implements IProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductCategoryService productCategoryService;

    @Override
    public void createProduct(CreateProductRequest request) throws ProductCreationException {
        Product existingProduct = productRepository.findByName(request.getName());
        if (existingProduct != null) {
            throw new ProductCreationException("Product with name " + existingProduct.getName() + " already exists)");
        }
        Product newProduct = new Product();
        newProduct.setName(request.getName());
        newProduct.setDescription(request.getDescription());
        newProduct.setPrice(request.getPrice());
        newProduct.setRemainQuantity(request.getRemainQuantity());
        ProductCategory category = productCategoryService.getProductCategoryById(request.getCategoryId());
        newProduct.setProductCategory(category);
        productRepository.save(newProduct);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategoryId(Long categoryId) {
        ProductCategory category = productCategoryService.getProductCategoryById(categoryId);
        List<Product> products = productRepository.findByProductCategory(category);
        return products;
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).get();
    }

    @Override
    public void updateProduct(Long id, CreateProductRequest request) {
        Product existingProduct = productRepository.findById(id).get();
        existingProduct.setDescription(request.getDescription());
        existingProduct.setName(request.getName());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setRemainQuantity(request.getRemainQuantity());
        ProductCategory category = productCategoryService.getProductCategoryById(request.getCategoryId());
        existingProduct.setProductCategory(category);
        productRepository.save(existingProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        Product existingProduct = productRepository.findById(id).get();
        productRepository.delete(existingProduct);
    }

}
