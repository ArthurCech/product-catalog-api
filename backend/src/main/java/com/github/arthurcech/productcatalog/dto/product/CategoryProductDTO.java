package com.github.arthurcech.productcatalog.dto.product;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.util.Objects;

public class CategoryProductDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Positive
    private final Long id;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public CategoryProductDTO(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryProductDTO that = (CategoryProductDTO) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
