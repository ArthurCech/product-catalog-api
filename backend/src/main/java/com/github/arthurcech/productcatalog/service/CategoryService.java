package com.github.arthurcech.productcatalog.service;

import com.github.arthurcech.productcatalog.domain.Category;
import com.github.arthurcech.productcatalog.dto.category.CategoryDTO;
import com.github.arthurcech.productcatalog.dto.category.CategoryInsertDTO;
import com.github.arthurcech.productcatalog.dto.category.CategoryUpdateDTO;
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
    public Page<CategoryDTO> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable)
                .map(CategoryMapper.INSTANCE::toCategoryDTO);
    }

    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id) {
        return categoryRepository.findById(id)
                .map(CategoryMapper.INSTANCE::toCategoryDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
    }

    @Transactional
    public CategoryDTO create(CategoryInsertDTO categoryInsertDTO) {
        Category category = CategoryMapper.INSTANCE.toCategory(categoryInsertDTO);
        categoryRepository.save(category);
        return CategoryMapper.INSTANCE.toCategoryDTO(category);
    }

    @Transactional
    public CategoryDTO update(
            Long id,
            CategoryUpdateDTO categoryUpdateDTO
    ) {
        try {
            Category category = categoryRepository.getOne(id);
            CategoryMapper.INSTANCE.updateCategoryFromDTO(categoryUpdateDTO, category);
            categoryRepository.save(category);
            return CategoryMapper.INSTANCE.toCategoryDTO(category);
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
