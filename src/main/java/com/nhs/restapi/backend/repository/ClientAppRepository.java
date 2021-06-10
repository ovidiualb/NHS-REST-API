package com.nhs.restapi.backend.repository;

import com.nhs.restapi.backend.model.entity.ClientApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientAppRepository extends JpaRepository<ClientApp, Integer> {

    Optional<ClientApp> findOneByName(String name);

    Optional<ClientApp> findByName(String name);

    boolean existsByName(String name);
}
