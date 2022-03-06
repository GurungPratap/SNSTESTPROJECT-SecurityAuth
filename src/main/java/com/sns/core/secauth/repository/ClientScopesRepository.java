package com.sns.core.secauth.repository;

import com.sns.core.secauth.model.ClientScope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientScopesRepository extends JpaRepository<ClientScope, Integer> {
}
