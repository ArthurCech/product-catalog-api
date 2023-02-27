package com.github.arthurcech.productcatalog.dto.user;

import com.github.arthurcech.productcatalog.validation.user.UserInsertValid;
import org.hibernate.validator.constraints.UniqueElements;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@UserInsertValid
public class UserInsertDTO implements Serializable {

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
    @NotBlank
    @Size(max = 16)
    private final String password;
    @NotEmpty
    @Valid
    @UniqueElements
    private final List<RoleUserDTO> roles;

    public UserInsertDTO(
            String firstName,
            String lastName,
            String email,
            String password,
            List<RoleUserDTO> roles
    ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public List<RoleUserDTO> getRoles() {
        return roles;
    }

}
