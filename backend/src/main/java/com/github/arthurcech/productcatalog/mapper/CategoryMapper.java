package com.github.arthurcech.productcatalog.mapper;

import com.github.arthurcech.productcatalog.domain.Category;
import com.github.arthurcech.productcatalog.dto.category.CategoryDTO;
import com.github.arthurcech.productcatalog.dto.category.CategoryInsertDTO;
import com.github.arthurcech.productcatalog.dto.category.CategoryUpdateDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    CategoryDTO toCategoryDTO(Category category);

    Category toCategory(CategoryInsertDTO categoryInsertDTO);

    void updateCategoryFromDTO(
            CategoryUpdateDTO categoryUpdateDTO,
            @MappingTarget Category category
    );

}
