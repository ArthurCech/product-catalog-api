package com.github.arthurcech.productcatalog.dto.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.UniqueElements;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public class CreateProductRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank
    private final String name;
    @NotBlank
    private final String description;
    @DecimalMin("0.0")
    @NotNull
    private final BigDecimal price;
    @NotBlank
    @JsonProperty("img_url")
    private final String imgUrl;
    @NotNull
    private final Instant date;
    @NotEmpty
    @Valid
    @UniqueElements
    private final List<ProductCategoryRequest> categories;

    public CreateProductRequest(
            String name,
            String description,
            BigDecimal price,
            String imgUrl,
            Instant date,
            List<ProductCategoryRequest> categories
    ) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.imgUrl = imgUrl;
        this.date = date;
        this.categories = categories;
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

    public List<ProductCategoryRequest> getCategories() {
        return List.copyOf(categories);
    }

}
