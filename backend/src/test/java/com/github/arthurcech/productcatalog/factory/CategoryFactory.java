package com.github.arthurcech.productcatalog.factory;

import com.github.arthurcech.productcatalog.domain.Category;
import com.github.arthurcech.productcatalog.dto.category.CategoryDTO;
import com.github.arthurcech.productcatalog.dto.category.CategoryInsertDTO;
import com.github.arthurcech.productcatalog.dto.category.CategoryUpdateDTO;

public class CategoryFactory {

    public static Category newCategory() {
        Category category = new Category();
        category.setId(1L);
        category.setName("Jogos");
        return category;
    }

    public static CategoryInsertDTO newCreateCategoryRequest() {
        return new CategoryInsertDTO(newCategory().getName());
    }

    public static CategoryUpdateDTO newUpdateCategoryRequest() {
        return new CategoryUpdateDTO(newCategory().getName());
    }

    public static CategoryDTO newCategoryResponse() {
        Category category = newCategory();
        return new CategoryDTO(category.getId(), category.getName());
    }

}
