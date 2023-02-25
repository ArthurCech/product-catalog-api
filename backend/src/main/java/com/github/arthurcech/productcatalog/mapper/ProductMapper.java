package com.github.arthurcech.productcatalog.mapper;

import com.github.arthurcech.productcatalog.domain.Product;
import com.github.arthurcech.productcatalog.dto.product.CreateProductRequest;
import com.github.arthurcech.productcatalog.dto.product.ProductResponse;
import com.github.arthurcech.productcatalog.dto.product.UpdateProductRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    ProductResponse toProductResponseWithCategories(Product product);

    @Mapping(target = "categories", ignore = true)
    ProductResponse toProductResponseBasic(Product product);

    @Mapping(target = "categories", ignore = true)
    Product toProduct(CreateProductRequest createProductRequest);

    @Mapping(target = "categories", ignore = true)
    void updateProductFromDTO(
            UpdateProductRequest updateProductRequest,
            @MappingTarget Product product
    );

}
