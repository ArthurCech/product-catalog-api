package com.github.arthurcech.productcatalog.service;

import com.github.arthurcech.productcatalog.domain.Product;
import com.github.arthurcech.productcatalog.dto.product.ProductCreateDTO;
import com.github.arthurcech.productcatalog.dto.product.ProductDTO;
import com.github.arthurcech.productcatalog.dto.product.ProductUpdateDTO;
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
    public Page<ProductDTO> findAll(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(ProductMapper.INSTANCE::toProductDTOBasic);
    }

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        return productRepository.findById(id)
                .map(ProductMapper.INSTANCE::toProductDTOWithCategories)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    @Transactional
    public ProductDTO create(ProductCreateDTO productCreateDTO) {
        try {
            Product product = ProductMapper.INSTANCE.toProduct(productCreateDTO);
            productCreateDTO.getCategories().forEach(category -> {
                product.addCategory(categoryRepository.getOne(category.getId()));
            });
            productRepository.save(product);
            return ProductMapper.INSTANCE.toProductDTOWithCategories(product);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Category not found");
        }
    }

    @Transactional
    public ProductDTO update(
            Long id,
            ProductUpdateDTO productUpdateDTO
    ) {
        try {
            Product product = productRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
            ProductMapper.INSTANCE.updateProductFromDTO(productUpdateDTO, product);
            product.getCategories().clear();
            productUpdateDTO.getCategories().forEach(category -> {
                product.addCategory(categoryRepository.getOne(category.getId()));
            });
            productRepository.save(product);
            return ProductMapper.INSTANCE.toProductDTOWithCategories(product);
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
