package dev.bananaftmeo.netcafeserver.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.bananaftmeo.netcafeserver.models.Product;
import dev.bananaftmeo.netcafeserver.models.ProductCategory;


public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByName(String name);
    List<Product> findByProductCategory(ProductCategory productCategory);
}
