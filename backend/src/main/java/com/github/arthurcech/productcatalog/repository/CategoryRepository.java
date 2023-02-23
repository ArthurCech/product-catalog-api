package com.github.arthurcech.productcatalog.repository;

import com.github.arthurcech.productcatalog.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
