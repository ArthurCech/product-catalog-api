package com.github.arthurcech.productcatalog.service;

import com.github.arthurcech.productcatalog.domain.Product;
import com.github.arthurcech.productcatalog.dto.product.CreateProductRequest;
import com.github.arthurcech.productcatalog.dto.product.ProductResponse;
import com.github.arthurcech.productcatalog.dto.product.UpdateProductRequest;
import com.github.arthurcech.productcatalog.exception.DatabaseException;
import com.github.arthurcech.productcatalog.exception.ResourceNotFoundException;
import com.github.arthurcech.productcatalog.mapper.ProductMapper;
import com.github.arthurcech.productcatalog.repository.CategoryRepository;
import com.github.arthurcech.productcatalog.repository.ProductRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository,
                          CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public Page<ProductResponse> findAll(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(ProductMapper.INSTANCE::toProductResponseBasic);
    }

    @Transactional(readOnly = true)
    public ProductResponse findById(Long id) {
        return productRepository.findById(id)
                .map(ProductMapper.INSTANCE::toProductResponseWithCategories)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    @Transactional
    public ProductResponse create(CreateProductRequest createProductRequest) {
        try {
            Product product = ProductMapper.INSTANCE.toProduct(createProductRequest);
            createProductRequest.getCategories()
                    .forEach(category -> {
                        product.addCategory(categoryRepository.getOne(category.getId()));
                    });
            productRepository.save(product);
            return ProductMapper.INSTANCE.toProductResponseWithCategories(product);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Category not found");
        }
    }

    @Transactional
    public ProductResponse update(
            Long id,
            UpdateProductRequest updateProductRequest
    ) {
        try {
            Product product = productRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
            ProductMapper.INSTANCE.updateProductFromDTO(updateProductRequest, product);
            product.getCategories().clear();
            updateProductRequest.getCategories()
                    .forEach(category -> {
                        product.addCategory(categoryRepository.getOne(category.getId()));
                    });
            productRepository.save(product);
            return ProductMapper.INSTANCE.toProductResponseWithCategories(product);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Category not found");
        }
    }

    public void delete(Long id) {
        try {
            productRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Product not found");
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Database integrity violation");
        }
    }

}
