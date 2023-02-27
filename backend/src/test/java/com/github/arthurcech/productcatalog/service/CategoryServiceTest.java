package com.github.arthurcech.productcatalog.service;

import com.github.arthurcech.productcatalog.domain.Category;
import com.github.arthurcech.productcatalog.dto.category.CategoryDTO;
import com.github.arthurcech.productcatalog.dto.category.CategoryInsertDTO;
import com.github.arthurcech.productcatalog.dto.category.CategoryUpdateDTO;
import com.github.arthurcech.productcatalog.exception.DatabaseException;
import com.github.arthurcech.productcatalog.exception.ResourceNotFoundException;
import com.github.arthurcech.productcatalog.factory.CategoryFactory;
import com.github.arthurcech.productcatalog.repository.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
class CategoryServiceTest {

    @InjectMocks
    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    private long existingId;
    private long nonExistingId;
    private long dependentId;
    private Category category;
    private CategoryInsertDTO categoryInsertDTO;
    private CategoryUpdateDTO categoryUpdateDTO;
    private PageImpl<Category> page;

    @BeforeEach
    void setUp() {
        existingId = 1L;
        nonExistingId = 2L;
        dependentId = 3L;
        category = CategoryFactory.newCategory();
        categoryInsertDTO = CategoryFactory.newCreateCategoryRequest();
        categoryUpdateDTO = CategoryFactory.newUpdateCategoryRequest();
        page = new PageImpl<>(List.of(category));

        Mockito.when(categoryRepository.findAll(ArgumentMatchers.any(Pageable.class))).thenReturn(page);

        Mockito.when(categoryRepository.save(ArgumentMatchers.any())).thenReturn(category);

        Mockito.when(categoryRepository.findById(existingId)).thenReturn(Optional.of(category));
        Mockito.when(categoryRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        Mockito.when(categoryRepository.getOne(existingId)).thenReturn(category);
        Mockito.when(categoryRepository.getOne(nonExistingId)).thenThrow(EntityNotFoundException.class);

        Mockito.doNothing().when(categoryRepository).deleteById(existingId);
        Mockito.doThrow(EmptyResultDataAccessException.class).when(categoryRepository).deleteById(nonExistingId);
        Mockito.doThrow(DataIntegrityViolationException.class).when(categoryRepository).deleteById(dependentId);
    }

    @Test
    void saveShouldReturnCategoryResponseWhenCategoryIsCreated() {
        CategoryDTO categoryDTO = categoryService.create(categoryInsertDTO);

        Assertions.assertNotNull(categoryDTO);
    }

    @Test
    void updateShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            categoryService.update(nonExistingId, categoryUpdateDTO);
        });

        Mockito.verify(categoryRepository, times(1)).getOne(nonExistingId);
    }

    @Test
    void updateShouldReturnCategoryResponseWhenIdExists() {
        CategoryDTO categoryDTO = categoryService.update(existingId, categoryUpdateDTO);

        Assertions.assertNotNull(categoryDTO);

        Mockito.verify(categoryRepository, times(1)).getOne(existingId);
    }

    @Test
    void findByIdShouldReturnCategoryResponseWhenIdExists() {
        CategoryDTO categoryDTO = categoryService.findById(existingId);

        Assertions.assertNotNull(categoryDTO);

        Mockito.verify(categoryRepository, times(1)).findById(existingId);
    }

    @Test
    void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            categoryService.findById(nonExistingId);
        });

        Mockito.verify(categoryRepository, times(1)).findById(nonExistingId);
    }

    @Test
    void findAllPagedShouldReturnPage() {
        Pageable pageable = PageRequest.of(0, 12);
        Page<CategoryDTO> categoriesPage = categoryService.findAll(pageable);

        Assertions.assertTrue(categoriesPage.hasContent());
        Assertions.assertEquals(1L, categoriesPage.getTotalElements());

        Mockito.verify(categoryRepository, times(1)).findAll(pageable);
    }

    @Test
    void deleteShouldThrowDatabaseExceptionWhenDependentId() {
        Assertions.assertThrows(DatabaseException.class, () -> {
            categoryService.delete(dependentId);
        });

        Mockito.verify(categoryRepository, Mockito.times(1)).deleteById(dependentId);
    }

    @Test
    void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            categoryService.delete(nonExistingId);
        });

        Mockito.verify(categoryRepository, times(1)).deleteById(nonExistingId);
    }

    @Test
    void deleteShouldDoNothingWhenIdExists() {
        Assertions.assertDoesNotThrow(() -> {
            categoryService.delete(existingId);
        });

        Mockito.verify(categoryRepository, times(1)).deleteById(existingId);
    }

}