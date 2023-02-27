package com.github.arthurcech.productcatalog.validation.user;

import com.github.arthurcech.productcatalog.dto.user.UserUpdateDTO;
import com.github.arthurcech.productcatalog.exception.FieldMessage;
import com.github.arthurcech.productcatalog.repository.UserRepository;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
public class UserUpdateValidator implements ConstraintValidator<UserUpdateValid, UserUpdateDTO> {

    private final HttpServletRequest request;
    private final UserRepository userRepository;

    public UserUpdateValidator(HttpServletRequest request,
                               UserRepository userRepository) {
        this.request = request;
        this.userRepository = userRepository;
    }

    @Override
    public void initialize(UserUpdateValid ann) {
    }

    @Override
    public boolean isValid(
            UserUpdateDTO userUpdateDTO,
            ConstraintValidatorContext context
    ) {
        Map<String, String> uriVars = (Map<String, String>) request
                .getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        long userId = Long.parseLong(uriVars.get("id"));

        List<FieldMessage> messages = new ArrayList<>();
        userRepository.findByEmail(userUpdateDTO.getEmail()).ifPresent(user -> {
            if (userId != user.getId()) {
                messages.add(new FieldMessage("email", "User already exists"));
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
