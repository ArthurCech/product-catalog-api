package com.github.arthurcech.productcatalog.resource;

import com.github.arthurcech.productcatalog.dto.product.CreateProductRequest;
import com.github.arthurcech.productcatalog.dto.product.ProductResponse;
import com.github.arthurcech.productcatalog.dto.product.UpdateProductRequest;
import com.github.arthurcech.productcatalog.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value = "/api/products")
public class ProductResource {

    private final ProductService productService;

    public ProductResource(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public Page<ProductResponse> findAll(Pageable pageable) {
        return productService.findAll(pageable);
    }

    @GetMapping(value = "/{id}")
    public ProductResponse findById(@PathVariable Long id) {
        return productService.findById(id);
    }

    @PostMapping
    public ResponseEntity<ProductResponse> create(
            @RequestBody @Valid CreateProductRequest createProductRequest
    ) {
        ProductResponse productResponse = productService.create(createProductRequest);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(productResponse.getId()).toUri();
        return ResponseEntity.created(uri).body(productResponse);
    }

    @PutMapping(value = "/{id}")
    public ProductResponse update(
            @PathVariable Long id,
            @RequestBody @Valid UpdateProductRequest updateProductRequest
    ) {
        return productService.update(id, updateProductRequest);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id
    ) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
