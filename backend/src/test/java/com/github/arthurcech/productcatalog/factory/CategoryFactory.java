package com.github.arthurcech.productcatalog.factory;

import com.github.arthurcech.productcatalog.domain.Category;
import com.github.arthurcech.productcatalog.dto.category.CreateCategoryRequest;
import com.github.arthurcech.productcatalog.dto.category.UpdateCategoryRequest;

public class CategoryFactory {

    public static Category newCategory() {
        Category category = new Category();
        category.setId(1L);
        category.setName("Jogos");
        return category;
    }

    public static CreateCategoryRequest newCreateCategoryRequest() {
        return new CreateCategoryRequest(newCategory().getName());
    }

    public static UpdateCategoryRequest newUpdateCategoryRequest() {
        return new UpdateCategoryRequest(newCategory().getName());
    }

}
