package com.github.arthurcech.productcatalog.factory;

import com.github.arthurcech.productcatalog.domain.Product;
import com.github.arthurcech.productcatalog.dto.product.ProductCreateDTO;
import com.github.arthurcech.productcatalog.dto.product.CategoryProductDTO;
import com.github.arthurcech.productcatalog.dto.product.ProductUpdateDTO;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public class ProductFactory {

    public static Product createProduct() {
        Product product = new Product();
        product.setId(1L);
        product.setName("GTA V");
        product.setDescription("Grand Theft Auto V é um jogo eletrônico de ação-aventura desenvolvido pela Rockstar North e publicado pela Rockstar Games.");
        product.setPrice(BigDecimal.valueOf(140.0));
        product.setImgUrl("https://upload.wikimedia.org/wikipedia/pt/8/80/Grand_Theft_Auto_V_capa.png");
        product.setDate(Instant.parse("2023-02-25T17:00:00Z"));
        product.addCategory(CategoryFactory.newCategory());
        return product;
    }

    public static ProductCreateDTO createProductRequest() {
        Product product = createProduct();
        return new ProductCreateDTO(
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getImgUrl(),
                product.getDate(),
                List.of(
                        new CategoryProductDTO(CategoryFactory.newCategory().getId())
                )
        );
    }

    public static ProductUpdateDTO updateProductRequest() {
        Product product = createProduct();
        return new ProductUpdateDTO(
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getImgUrl(),
                product.getDate(),
                List.of(
                        new CategoryProductDTO(CategoryFactory.newCategory().getId())
                )
        );
    }

}
