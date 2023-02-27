package com.github.arthurcech.productcatalog.mapper;

import com.github.arthurcech.productcatalog.domain.Product;
import com.github.arthurcech.productcatalog.dto.product.ProductCreateDTO;
import com.github.arthurcech.productcatalog.dto.product.ProductDTO;
import com.github.arthurcech.productcatalog.dto.product.ProductUpdateDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    ProductDTO toProductDTOWithCategories(Product product);

    @Mapping(target = "categories", ignore = true)
    ProductDTO toProductDTOBasic(Product product);

    @Mapping(target = "categories", ignore = true)
    Product toProduct(ProductCreateDTO productCreateDTO);

    @Mapping(target = "categories", ignore = true)
    void updateProductFromDTO(
            ProductUpdateDTO productUpdateDTO,
            @MappingTarget Product product
    );

}
