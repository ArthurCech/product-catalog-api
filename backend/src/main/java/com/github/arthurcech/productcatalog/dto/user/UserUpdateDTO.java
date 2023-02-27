package com.github.arthurcech.productcatalog.dto.user;

import org.hibernate.validator.constraints.UniqueElements;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

public class UserUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank
    @Size(max = 255)
    private final String firstName;
    @NotBlank
    @Size(max = 255)
    private final String lastName;
    @NotBlank
    @Size(max = 255)
    @Email
    private final String email;
    @NotEmpty
    @Valid
    @UniqueElements
    private final List<RoleUserDTO> roles;

    public UserUpdateDTO(
            String firstName,
            String lastName,
            String email,
            List<RoleUserDTO> roles
    ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.roles = roles;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public List<RoleUserDTO> getRoles() {
        return roles;
    }

}
