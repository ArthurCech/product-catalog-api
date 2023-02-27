package com.github.arthurcech.productcatalog.repository;

import com.github.arthurcech.productcatalog.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
