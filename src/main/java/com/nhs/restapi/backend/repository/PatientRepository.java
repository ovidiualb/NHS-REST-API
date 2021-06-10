package com.nhs.restapi.backend.repository;

import com.nhs.restapi.backend.model.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {

    Optional<Patient> findByCnp(String cnp);

    Patient getByCnp(String cnp);

    boolean existsByCnp(String cnp);
}
