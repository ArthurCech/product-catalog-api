package com.github.arthurcech.productcatalog.repository;

import com.github.arthurcech.productcatalog.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

}
