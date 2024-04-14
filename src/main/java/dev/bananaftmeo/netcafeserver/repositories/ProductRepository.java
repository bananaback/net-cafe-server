package dev.bananaftmeo.netcafeserver.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.bananaftmeo.netcafeserver.models.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
