package com.TechCart.product;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByIsActiveTrue();

    Optional<Product> findByPIdAndIsActiveTrue(Long pId);

    List<Product> findByCategoryIgnoreCaseAndIsActiveTrue(String category);

    List<Product> findByPNameContainingIgnoreCaseAndIsActiveTrue(String pName);

    List<Product> findByBrandIgnoreCaseAndIsActiveTrue(String brand);
}
