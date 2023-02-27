package com.github.arthurcech.productcatalog.dto.product;

import org.hibernate.validator.constraints.UniqueElements;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public class ProductCreateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank
    @Size(max = 255)
    private final String name;
    @NotBlank
    @Size(max = 255)
    private final String description;
    @Positive
    @NotNull
    private final BigDecimal price;
    @NotBlank
    @Size(max = 255)
    private final String imgUrl;
    @NotNull
    private final Instant date;
    @NotEmpty
    @Valid
    @UniqueElements
    private final List<CategoryProductDTO> categories;

    public ProductCreateDTO(
            String name,
            String description,
            BigDecimal price,
            String imgUrl,
            Instant date,
            List<CategoryProductDTO> categories
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

    public List<CategoryProductDTO> getCategories() {
        return categories;
    }

}
