package com.github.arthurcech.productcatalog.mapper;

import com.github.arthurcech.productcatalog.domain.Category;
import com.github.arthurcech.productcatalog.dto.category.CategoryResponse;
import com.github.arthurcech.productcatalog.dto.category.CreateCategoryRequest;
import com.github.arthurcech.productcatalog.dto.category.UpdateCategoryRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    CategoryResponse toCategoryResponse(Category category);

    Category toCategory(CreateCategoryRequest createCategoryRequest);

    void updateCategoryFromDTO(
            UpdateCategoryRequest updateCategoryRequest,
            @MappingTarget Category category
    );

}
