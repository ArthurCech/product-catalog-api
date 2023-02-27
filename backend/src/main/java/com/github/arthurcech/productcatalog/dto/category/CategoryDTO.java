package com.github.arthurcech.productcatalog.dto.category;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;

@JsonPropertyOrder({"id", "name"})
public class CategoryDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Long id;
    private final String name;

    public CategoryDTO(
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
