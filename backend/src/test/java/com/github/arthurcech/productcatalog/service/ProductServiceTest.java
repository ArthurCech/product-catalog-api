package com.github.arthurcech.productcatalog.service;

import com.github.arthurcech.productcatalog.domain.Category;
import com.github.arthurcech.productcatalog.domain.Product;
import com.github.arthurcech.productcatalog.dto.product.CreateProductRequest;
import com.github.arthurcech.productcatalog.dto.product.ProductResponse;
import com.github.arthurcech.productcatalog.dto.product.UpdateProductRequest;
import com.github.arthurcech.productcatalog.exception.DatabaseException;
import com.github.arthurcech.productcatalog.exception.ResourceNotFoundException;
import com.github.arthurcech.productcatalog.factory.CategoryFactory;
import com.github.arthurcech.productcatalog.factory.ProductFactory;
import com.github.arthurcech.productcatalog.repository.CategoryRepository;
import com.github.arthurcech.productcatalog.repository.ProductRepository;
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
class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    private long existingId;
    private long nonExistingId;
    private long dependentId;
    private Product product;
    private Category category;
    private CreateProductRequest createProductRequest;
    private UpdateProductRequest updateProductRequest;
    private PageImpl<Product> page;

    @BeforeEach
    void setUp() {
        existingId = 1L;
        nonExistingId = 2L;
        dependentId = 3L;
        product = ProductFactory.createProduct();
        category = CategoryFactory.newCategory();
        createProductRequest = ProductFactory.createProductRequest();
        updateProductRequest = ProductFactory.updateProductRequest();
        page = new PageImpl<>(List.of(product));

        Mockito.when(productRepository.findAll(ArgumentMatchers.any(Pageable.class))).thenReturn(page);

        Mockito.when(productRepository.save(ArgumentMatchers.any())).thenReturn(product);

        Mockito.when(productRepository.findById(existingId)).thenReturn(Optional.of(product));
        Mockito.when(productRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        Mockito.when(categoryRepository.getOne(existingId)).thenReturn(category);
        Mockito.when(categoryRepository.getOne(nonExistingId)).thenThrow(EntityNotFoundException.class);

        Mockito.doNothing().when(productRepository).deleteById(existingId);
        Mockito.doThrow(EmptyResultDataAccessException.class).when(productRepository).deleteById(nonExistingId);
        Mockito.doThrow(DataIntegrityViolationException.class).when(productRepository).deleteById(dependentId);
    }

    @Test
    void saveShouldReturnProductResponseWhenCategoryIsCreated() {
        ProductResponse productResponse = productService.create(createProductRequest);

        Assertions.assertNotNull(productResponse);
    }

    @Test
    void updateShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            productService.update(nonExistingId, updateProductRequest);
        });

        Mockito.verify(productRepository, times(1)).findById(nonExistingId);
    }

    @Test
    void updateShouldReturnProductResponseWhenIdExists() {
        ProductResponse productResponse = productService.update(existingId, updateProductRequest);

        Assertions.assertNotNull(productResponse);

        Mockito.verify(productRepository, times(1)).findById(existingId);
    }

    @Test
    void findByIdShouldReturnProductResponseWhenIdExists() {
        ProductResponse productResponse = productService.findById(existingId);

        Assertions.assertNotNull(productResponse);

        Mockito.verify(productRepository, times(1)).findById(existingId);
    }

    @Test
    void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            productService.findById(nonExistingId);
        });

        Mockito.verify(productRepository, times(1)).findById(nonExistingId);
    }

    @Test
    void findAllPagedShouldReturnPage() {
        Pageable pageable = PageRequest.of(0, 12);
        Page<ProductResponse> page = productService.findAll(pageable);

        Assertions.assertNotNull(page);
        Assertions.assertEquals(1L, page.getTotalElements());

        Mockito.verify(productRepository, times(1)).findAll(pageable);
    }

    @Test
    void deleteShouldThrowDatabaseExceptionWhenDependentId() {
        Assertions.assertThrows(DatabaseException.class, () -> {
            productService.delete(dependentId);
        });

        Mockito.verify(productRepository, Mockito.times(1)).deleteById(dependentId);
    }

    @Test
    void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            productService.delete(nonExistingId);
        });

        Mockito.verify(productRepository, times(1)).deleteById(nonExistingId);
    }

    @Test
    void deleteShouldDoNothingWhenIdExists() {
        Assertions.assertDoesNotThrow(() -> {
            productService.delete(existingId);
        });

        Mockito.verify(productRepository, times(1)).deleteById(existingId);
    }

}