package com.sns.core.secauth.repository;

import com.sns.core.secauth.model.Clients;
import com.sns.core.secauth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientsRepository extends JpaRepository<Clients, Long> {
    Optional<Clients> findClientsByClientId(String clientId);
}
