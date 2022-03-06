package com.sns.core.secauth.repository;

import com.sns.core.secauth.model.ClientAuthority;
import com.sns.core.secauth.model.UserAuthority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientAuthorityRepository extends JpaRepository<ClientAuthority, Integer> {
}
