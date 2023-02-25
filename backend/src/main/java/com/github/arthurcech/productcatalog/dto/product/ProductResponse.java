package com.github.arthurcech.productcatalog.dto.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.arthurcech.productcatalog.dto.category.CategoryResponse;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

public class ProductResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Long id;
    private final String name;
    private final String description;
    private final BigDecimal price;
    @JsonProperty("img_url")
    private final String imgUrl;
    private final Instant date;
    private final Set<CategoryResponse> categories;

    public ProductResponse(
            Long id,
            String name,
            String description,
            BigDecimal price,
            String imgUrl,
            Instant date,
            Set<CategoryResponse> categories
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imgUrl = imgUrl;
        this.date = date;
        this.categories = categories == null ? new HashSet<>() : categories;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public Instant getDate() {
        return date;
    }

    public Set<CategoryResponse> getCategories() {
        return categories;
    }

}
