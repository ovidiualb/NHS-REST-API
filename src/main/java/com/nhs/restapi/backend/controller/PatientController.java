package com.nhs.restapi.backend.controller;

import com.nhs.restapi.backend.model.dto.PatientDTO;
import com.nhs.restapi.backend.model.entity.Patient;
import com.nhs.restapi.backend.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/patients")
public class PatientController {

    @Autowired
    PatientService patientService;

    @PostMapping
    public EntityModel<PatientDTO> add(@RequestBody @Valid Patient patient) {
        return patientService.add(patient);
    }

    @GetMapping("/by-cnp/{cnp}")
    public EntityModel<PatientDTO> findByCnp(@PathVariable String cnp) {
        return patientService.findByCnp(cnp);
    }

    @PutMapping
    public EntityModel<PatientDTO> update(@RequestBody @Valid Patient patient) {
        return patientService.update(patient);
    }

    @DeleteMapping("/by-cnp/{cnp}")
    public EntityModel<PatientDTO> deleteByCnp(@PathVariable String cnp) {
        return patientService.deleteByCnp(cnp);
    }
}
