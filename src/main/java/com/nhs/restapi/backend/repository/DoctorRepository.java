package com.nhs.restapi.backend.repository;

import com.nhs.restapi.backend.model.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {

    Doctor getByCnp(String cnp);

    Optional<Doctor> findByCnp(String cnp);

    boolean existsByCnp(String cnp);
}
