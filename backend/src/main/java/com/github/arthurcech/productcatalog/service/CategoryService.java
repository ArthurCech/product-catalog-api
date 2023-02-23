package com.github.arthurcech.productcatalog.service;

import com.github.arthurcech.productcatalog.domain.Category;
import com.github.arthurcech.productcatalog.dto.category.CategoryResponse;
import com.github.arthurcech.productcatalog.dto.category.CreateCategoryRequest;
import com.github.arthurcech.productcatalog.dto.category.UpdateCategoryRequest;
import com.github.arthurcech.productcatalog.exception.DatabaseException;
import com.github.arthurcech.productcatalog.exception.ResourceNotFoundException;
import com.github.arthurcech.productcatalog.mapper.CategoryMapper;
import com.github.arthurcech.productcatalog.repository.CategoryRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public Page<CategoryResponse> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable)
                .map(CategoryMapper.INSTANCE::toCategoryResponse);
    }

    @Transactional(readOnly = true)
    public CategoryResponse findById(Long id) {
        return categoryRepository.findById(id)
                .map(CategoryMapper.INSTANCE::toCategoryResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
    }

    @Transactional
    public CategoryResponse create(CreateCategoryRequest createCategoryRequest) {
        Category category = CategoryMapper.INSTANCE.toCategory(createCategoryRequest);
        categoryRepository.save(category);
        return CategoryMapper.INSTANCE.toCategoryResponse(category);
    }

    @Transactional
    public CategoryResponse update(
            Long id,
            UpdateCategoryRequest updateCategoryRequest
    ) {
        try {
            Category category = categoryRepository.getOne(id);
            CategoryMapper.INSTANCE.updateCategoryFromDTO(updateCategoryRequest, category);
            categoryRepository.save(category);
            return CategoryMapper.INSTANCE.toCategoryResponse(category);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Category not found");
        }
    }

    public void delete(Long id) {
        try {
            categoryRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Category not found");
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Database integrity violation");
        }
    }

}
