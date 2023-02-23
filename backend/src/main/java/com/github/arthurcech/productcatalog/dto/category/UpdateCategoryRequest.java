package com.github.arthurcech.productcatalog.dto.category;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.github.arthurcech.productcatalog.validation.category.CategoryUpdateValid;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@CategoryUpdateValid
public class UpdateCategoryRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank
    @Size(max = 255)
    private final String name;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public UpdateCategoryRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
