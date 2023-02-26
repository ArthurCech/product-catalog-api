package com.github.arthurcech.productcatalog.repository;

import com.github.arthurcech.productcatalog.domain.Category;
import com.github.arthurcech.productcatalog.factory.CategoryFactory;
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
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    private long existingId;
    private long nonExistingId;
    private long totalElements;

    @BeforeEach
    void setUp() {
        existingId = 1L;
        nonExistingId = 100L;
        totalElements = 3L;
    }

    @Test
    void saveShouldPersistWithAutoIncrementWhenIdIsNull() {
        Category category = CategoryFactory.newCategory();
        category.setId(null);

        Long expectedId = 4L;
        String expectedName = category.getName();

        categoryRepository.save(category);

        Assertions.assertNotNull(category);
        Assertions.assertEquals(expectedId, category.getId());
        Assertions.assertEquals(expectedName, category.getName());
    }

    @Test
    void saveShouldUpdateWhenIdExists() {
        Category category = CategoryFactory.newCategory();

        Long expectedId = category.getId();
        String expectedName = category.getName();

        categoryRepository.save(category);

        Assertions.assertNotNull(category);
        Assertions.assertEquals(expectedId, category.getId());
        Assertions.assertEquals(expectedName, category.getName());
    }

    @Test
    void deleteShouldDeleteObjectWhenIdExists() {
        categoryRepository.deleteById(existingId);
        Optional<Category> optional = categoryRepository.findById(existingId);

        Assertions.assertTrue(optional.isEmpty());
    }

    @Test
    void deleteShouldThrowsEmptyResultDataAccessExceptionWhenIdDoesNotExist() {
        Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
            categoryRepository.deleteById(nonExistingId);
        });
    }

    @Test
    void findByIdShouldReturnNonEmptyOptionalWhenIdExists() {
        Optional<Category> categoryOptional = categoryRepository.findById(existingId);

        Assertions.assertTrue(categoryOptional.isPresent());
    }

    @Test
    void findByIdShouldReturnEmptyOptionalWhenIdDoesNotExist() {
        Optional<Category> categoryOptional = categoryRepository.findById(nonExistingId);

        Assertions.assertTrue(categoryOptional.isEmpty());
    }

    @Test
    void findAllShouldReturnPage() {
        Page<Category> categoriesPage = categoryRepository.findAll(PageRequest.of(0, 10));

        Assertions.assertTrue(categoriesPage.hasContent());
        Assertions.assertEquals(totalElements, categoriesPage.getTotalElements());
    }

}