package com.sns.core.secauth.repository;

import com.sns.core.secauth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByUsername(String u);
    boolean existsByUsername(String username);
}
