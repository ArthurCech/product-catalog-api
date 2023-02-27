package com.github.arthurcech.productcatalog.validation.user;

import com.github.arthurcech.productcatalog.dto.user.UserInsertDTO;
import com.github.arthurcech.productcatalog.exception.FieldMessage;
import com.github.arthurcech.productcatalog.repository.UserRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class UserInsertValidator implements ConstraintValidator<UserInsertValid, UserInsertDTO> {

    private final UserRepository userRepository;

    public UserInsertValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void initialize(UserInsertValid ann) {
    }

    @Override
    public boolean isValid(
            UserInsertDTO userInsertDTO,
            ConstraintValidatorContext context
    ) {
        List<FieldMessage> messages = new ArrayList<>();
        userRepository.findByEmail(userInsertDTO.getEmail()).ifPresent(user -> {
            messages.add(new FieldMessage("email", "User already exists"));
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
