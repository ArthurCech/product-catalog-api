package com.github.arthurcech.productcatalog.dto.category;

import java.io.Serializable;

public class CategoryResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Long id;
    private final String name;

    public CategoryResponse(
            Long id,
            String name
    ) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
