package com.github.arthurcech.productcatalog.dto.user;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@JsonPropertyOrder({"id", "firstName", "lastName", "email", "roles"})
public class UserDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Long id;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final Set<RoleDTO> roles;

    public UserDTO(
            Long id,
            String firstName,
            String lastName,
            String email,
            Set<RoleDTO> roles
    ) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.roles = roles != null ? roles : new HashSet<>();
    }

    public Long getId() {
        return id;
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

    public Set<RoleDTO> getRoles() {
        return roles;
    }

}
