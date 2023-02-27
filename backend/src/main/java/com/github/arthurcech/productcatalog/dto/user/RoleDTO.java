package com.github.arthurcech.productcatalog.dto.user;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;

@JsonPropertyOrder({"id", "authority"})
public class RoleDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Long id;
    private final String authority;

    public RoleDTO(
            Long id,
            String authority
    ) {
        this.id = id;
        this.authority = authority;
    }

    public Long getId() {
        return id;
    }

    public String getAuthority() {
        return authority;
    }

}
