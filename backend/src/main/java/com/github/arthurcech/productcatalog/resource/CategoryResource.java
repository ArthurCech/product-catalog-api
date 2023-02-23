package com.github.arthurcech.productcatalog.resource;

import com.github.arthurcech.productcatalog.dto.category.CategoryResponse;
import com.github.arthurcech.productcatalog.dto.category.CreateCategoryRequest;
import com.github.arthurcech.productcatalog.dto.category.UpdateCategoryRequest;
import com.github.arthurcech.productcatalog.service.CategoryService;
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
@RequestMapping(value = "/api/categories")
public class CategoryResource {

    private final CategoryService categoryService;

    public CategoryResource(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public Page<CategoryResponse> findAll(Pageable pageable) {
        return categoryService.findAll(pageable);
    }

    @GetMapping(value = "/{id}")
    public CategoryResponse findById(@PathVariable Long id) {
        return categoryService.findById(id);
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> create(
            @RequestBody @Valid CreateCategoryRequest createCategoryRequest
    ) {
        CategoryResponse categoryResponse = categoryService.create(createCategoryRequest);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(categoryResponse.getId()).toUri();
        return ResponseEntity.created(uri).body(categoryResponse);
    }

    @PutMapping(value = "/{id}")
    public CategoryResponse update(
            @PathVariable Long id,
            @RequestBody @Valid UpdateCategoryRequest updateCategoryRequest
    ) {
        return categoryService.update(id, updateCategoryRequest);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id
    ) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
