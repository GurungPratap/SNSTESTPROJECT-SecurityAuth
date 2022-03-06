package com.sns.core.secauth.repository;

import com.sns.core.secauth.model.UserAuthority;
import com.sns.core.secauth.util.EAuthority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAuthorityRepository extends JpaRepository<UserAuthority, Integer> {
    Optional<UserAuthority> findByDescription(EAuthority authority);
}
