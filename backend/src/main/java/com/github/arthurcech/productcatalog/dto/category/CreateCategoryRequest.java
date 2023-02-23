package com.github.arthurcech.productcatalog.dto.category;

import com.github.arthurcech.productcatalog.validation.category.CategoryInsertValid;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@CategoryInsertValid
public class CreateCategoryRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank
    @Size(max = 255)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
