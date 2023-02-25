package com.github.arthurcech.productcatalog.repository;

import com.github.arthurcech.productcatalog.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
