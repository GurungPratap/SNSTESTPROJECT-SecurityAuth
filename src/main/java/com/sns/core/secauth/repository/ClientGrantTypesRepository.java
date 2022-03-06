package com.sns.core.secauth.repository;

import com.sns.core.secauth.model.ClientAuthority;
import com.sns.core.secauth.model.ClientGrantTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientGrantTypesRepository extends JpaRepository<ClientGrantTypes, Integer> {
}
