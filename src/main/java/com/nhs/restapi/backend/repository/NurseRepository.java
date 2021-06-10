package com.nhs.restapi.backend.repository;

import com.nhs.restapi.backend.model.entity.Nurse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NurseRepository extends JpaRepository<Nurse, Integer> {

    Optional<Nurse> findByEmail(String email);

    Optional<Nurse> findByCnp(String cnp);

    Nurse getByCnp(String cnp);

    boolean existsByCnp(String cnp);
}
