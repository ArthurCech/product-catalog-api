package com.github.arthurcech.productcatalog.repository;

import com.github.arthurcech.productcatalog.domain.Category;
import com.github.arthurcech.productcatalog.domain.Product;
import com.github.arthurcech.productcatalog.factory.CategoryFactory;
import com.github.arthurcech.productcatalog.factory.ProductFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    private long existingId;
    private long nonExistingId;
    private long totalElements;

    @BeforeEach
    void setUp() {
        existingId = 1L;
        nonExistingId = 100L;
        totalElements = 25L;
    }

    @Test
    void saveShouldPersistWithAutoincrementWhenIdIsNull() {
        Product expectedProduct = ProductFactory.createProduct();
        Category expectedCategory = CategoryFactory.newCategory();
        Long expectedId = 26L;

        Product product = ProductFactory.createProduct();
        product.setId(null);
        productRepository.save(product);

        Assertions.assertNotNull(product);
        Assertions.assertEquals(expectedId, product.getId());
        Assertions.assertEquals(expectedProduct.getName(), product.getName());
        Assertions.assertEquals(expectedProduct.getDescription(), product.getDescription());
        Assertions.assertEquals(expectedProduct.getPrice(), product.getPrice());
        Assertions.assertEquals(expectedProduct.getImgUrl(), product.getImgUrl());
        Assertions.assertEquals(expectedProduct.getDate(), product.getDate());
        Assertions.assertEquals(expectedCategory.getId(), product.getCategories().stream().findFirst().get().getId());
        Assertions.assertEquals(expectedCategory.getName(), product.getCategories().stream().findFirst().get().getName());
    }

    @Test
    void saveShouldUpdateWhenIdExists() {
        Product expectedProduct = ProductFactory.createProduct();
        Category expectedCategory = CategoryFactory.newCategory();

        Product product = ProductFactory.createProduct();
        productRepository.save(product);

        Assertions.assertNotNull(product);
        Assertions.assertEquals(expectedProduct.getId(), product.getId());
        Assertions.assertEquals(expectedProduct.getName(), product.getName());
        Assertions.assertEquals(expectedProduct.getDescription(), product.getDescription());
        Assertions.assertEquals(expectedProduct.getPrice(), product.getPrice());
        Assertions.assertEquals(expectedProduct.getImgUrl(), product.getImgUrl());
        Assertions.assertEquals(expectedProduct.getDate(), product.getDate());
        Assertions.assertEquals(expectedCategory.getId(), product.getCategories().stream().findFirst().get().getId());
        Assertions.assertEquals(expectedCategory.getName(), product.getCategories().stream().findFirst().get().getName());
    }

    @Test
    void deleteShouldDeleteObjectWhenIdExists() {
        productRepository.deleteById(existingId);
        Optional<Product> productOptional = productRepository.findById(existingId);

        Assertions.assertFalse(productOptional.isPresent());
    }

    @Test
    void deleteShouldThrowsEmptyResultDataAccessExceptionWhenIdDoesNotExist() {
        Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
            productRepository.deleteById(nonExistingId);
        });
    }

    @Test
    void findByIdShouldReturnNonEmptyOptionalWhenIdExists() {
        Optional<Product> productOptional = productRepository.findById(existingId);

        Assertions.assertTrue(productOptional.isPresent());
    }

    @Test
    void findByIdShouldReturnEmptyOptionalWhenIdDoesNotExist() {
        Optional<Product> productOptional = productRepository.findById(nonExistingId);

        Assertions.assertTrue(productOptional.isEmpty());
    }

    @Test
    void findAllShouldReturnPage() {
        Page<Product> productsPage = productRepository.findAll(PageRequest.of(0, 10));

        Assertions.assertTrue(productsPage.hasContent());
        Assertions.assertEquals(totalElements, productsPage.getTotalElements());
    }

}