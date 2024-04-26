package dev.bananaftmeo.netcafeserver.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.google.cloud.Binding;

import dev.bananaftmeo.netcafeserver.exceptions.ProductCategoryCreationException;
import dev.bananaftmeo.netcafeserver.models.ProductCategory;
import dev.bananaftmeo.netcafeserver.models.requests.CreateProductCategoryRequest;
import dev.bananaftmeo.netcafeserver.models.responses.ErrorResponse;
import dev.bananaftmeo.netcafeserver.models.responses.ProductCategoriesResponse;
import dev.bananaftmeo.netcafeserver.services.productcategoryservices.IProductCategoryService;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("api/productcategories")
public class ProductCategoryController {
    
    @Autowired
    private IProductCategoryService productCategoryService;

    @PostMapping
    public ResponseEntity<?> createProductCategory(@Validated @RequestBody CreateProductCategoryRequest request,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Handle validation errors
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse("Validation error: " + bindingResult.getAllErrors()));
        }
        try {
            productCategoryService.createProductCategory(request);
            return ResponseEntity.ok().body("Product category created successfully.");
        } catch (ProductCategoryCreationException ex) {
            return ResponseEntity.badRequest().body(new ErrorResponse(ex.getErrrorMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllProductCategories() {
        List<ProductCategory> productCategories = productCategoryService.getAllCategories();
        return ResponseEntity.ok().body(new ProductCategoriesResponse(productCategories));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductCategoryById(@PathVariable Long id) {
        try {
            ProductCategory productCategory = productCategoryService.getProductCategoryById(id);
            return ResponseEntity.ok().body(productCategory);
        } catch (NoSuchElementException ex) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse("Product category with id " + id + " does not exist."));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProductCategory(@PathVariable Long id,
            @Validated @RequestBody CreateProductCategoryRequest createProductCategoryRequest,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Handle validation errors
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse("Validation error: " + bindingResult.getAllErrors()));
        }
        try {
            productCategoryService.updateProductCategory(id, createProductCategoryRequest);
            return ResponseEntity.ok().body("Update product category successfully.");
        } catch (NoSuchElementException ex) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse("Product category with id " + id + " does not exist."));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProductCategory(@PathVariable Long id) {
        try {
            productCategoryService.deleteProductCategory(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException ex) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse("Product category with id " + id + " does not exist."));
        }
    }
}
