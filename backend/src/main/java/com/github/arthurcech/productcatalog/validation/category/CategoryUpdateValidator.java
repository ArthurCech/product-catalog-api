package com.github.arthurcech.productcatalog.validation.category;

import com.github.arthurcech.productcatalog.dto.category.UpdateCategoryRequest;
import com.github.arthurcech.productcatalog.exception.FieldMessage;
import com.github.arthurcech.productcatalog.repository.CategoryRepository;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
public class CategoryUpdateValidator implements ConstraintValidator<CategoryUpdateValid, UpdateCategoryRequest> {

    private final HttpServletRequest request;
    private final CategoryRepository categoryRepository;

    public CategoryUpdateValidator(HttpServletRequest request,
                                   CategoryRepository categoryRepository) {
        this.request = request;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void initialize(CategoryUpdateValid ann) {
    }

    @Override
    public boolean isValid(
            UpdateCategoryRequest updateCategoryRequest,
            ConstraintValidatorContext context
    ) {
        Map<String, String> uriVars = (Map<String, String>) request
                .getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        long categoryId = Long.parseLong(uriVars.get("id"));

        List<FieldMessage> messages = new ArrayList<>();
        categoryRepository.findByName(updateCategoryRequest.getName())
                .ifPresent(category -> {
                    if (categoryId != category.getId()) {
                        messages.add(new FieldMessage("name", "Category already exists"));
                    }
                });
        for (FieldMessage f : messages) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(f.getMessage())
                    .addPropertyNode(f.getField())
                    .addConstraintViolation();
        }
        return messages.isEmpty();
    }

}
