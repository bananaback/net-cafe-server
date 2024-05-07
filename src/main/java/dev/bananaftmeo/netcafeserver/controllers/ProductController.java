package dev.bananaftmeo.netcafeserver.controllers;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.bananaftmeo.netcafeserver.exceptions.ProductCreationException;
import dev.bananaftmeo.netcafeserver.models.Product;
import dev.bananaftmeo.netcafeserver.models.requests.CreateProductRequest;
import dev.bananaftmeo.netcafeserver.models.responses.ErrorResponse;
import dev.bananaftmeo.netcafeserver.models.responses.ProductsResponse;
import dev.bananaftmeo.netcafeserver.services.productservices.IProductService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("api/products")
public class ProductController {
    @Autowired
    private IProductService productService;

    @PostMapping
    public ResponseEntity<?> createProduct(@Validated @RequestBody CreateProductRequest request,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Handle validation errors
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse("Validation error: " + bindingResult.getAllErrors()));
        }
        try {
            productService.createProduct(request);
            return ResponseEntity.ok().body("Product created successfully.");
        } catch (ProductCreationException ex) {
            return ResponseEntity.badRequest().body(new ErrorResponse(ex.getErrorMessage()));
        } catch (NoSuchElementException ex) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse(
                            "Product category with id " + request.getCategoryId() + " does not exist."));
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok().body(new ProductsResponse(products));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<?> getProductsByProductCategory(@PathVariable Long categoryId) {
        try {
            List<Product> products = productService.getProductsByCategoryId(categoryId);
            return ResponseEntity.ok().body(products);
        } catch (NoSuchElementException ex) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse("Product category with id " + categoryId + " does not exist"));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        try {
            Product product = productService.getProductById(id);
            return ResponseEntity.ok().body(product);
        } catch (NoSuchElementException ex) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse("Product with id " + id + " does not exist."));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id,
            @Validated @RequestBody CreateProductRequest createProductRequest,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Handle validation errors
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse("Validation error: " + bindingResult.getAllErrors()));
        }
        try {
            productService.updateProduct(id, createProductRequest);
            return ResponseEntity.ok().body("Update product successfully.");
        } catch (NoSuchElementException ex) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse("Product with id " + id + " or Category with id "
                            + createProductRequest.getCategoryId() + " does not exist."));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException ex) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse("Product with id " + id + " does not exist."));
        }
    }
}
