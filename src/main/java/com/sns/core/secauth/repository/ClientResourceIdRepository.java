package com.sns.core.secauth.repository;

import com.sns.core.secauth.model.ClientResourceId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientResourceIdRepository extends JpaRepository<ClientResourceId, Integer> {
}
