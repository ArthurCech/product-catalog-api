package com.github.arthurcech.productcatalog.repository;

import com.github.arthurcech.productcatalog.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
