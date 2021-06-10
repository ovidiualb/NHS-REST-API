package com.nhs.restapi.backend.repository;

import com.nhs.restapi.backend.model.entity.Institution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface InstitutionRepository extends JpaRepository<Institution, Integer> {

    Optional<Institution> findByCui(String cui);

    Institution getByCui(String cui);

    ArrayList<Institution> findAll();
}
