package com.github.arthurcech.productcatalog.validation.category;

import com.github.arthurcech.productcatalog.dto.category.CategoryInsertDTO;
import com.github.arthurcech.productcatalog.exception.FieldMessage;
import com.github.arthurcech.productcatalog.repository.CategoryRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class CategoryInsertValidator implements ConstraintValidator<CategoryInsertValid, CategoryInsertDTO> {

    private final CategoryRepository categoryRepository;

    public CategoryInsertValidator(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void initialize(CategoryInsertValid ann) {
    }

    @Override
    public boolean isValid(
            CategoryInsertDTO categoryInsertDTO,
            ConstraintValidatorContext context
    ) {
        List<FieldMessage> messages = new ArrayList<>();
        categoryRepository.findByName(categoryInsertDTO.getName())
                .ifPresent(category -> messages
                        .add(
                                new FieldMessage("name", "Category already exists")
                        ));
        for (FieldMessage f : messages) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(f.getMessage())
                    .addPropertyNode(f.getField())
                    .addConstraintViolation();
        }
        return messages.isEmpty();
    }

}
