package com.nhs.restapi.backend.service;

import com.nhs.restapi.backend.controller.PatientController;
import com.nhs.restapi.backend.exception.business.GlobalAlreadyExistsException;
import com.nhs.restapi.backend.exception.business.GlobalNotFoundException;
import com.nhs.restapi.backend.model.dto.PatientDTO;
import com.nhs.restapi.backend.model.entity.Patient;
import com.nhs.restapi.backend.repository.PatientRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private ModelMapper modelMapper;

    public EntityModel<PatientDTO> add(Patient patient) {
        if (!patientRepository.existsByCnp(patient.getCnp())) {
            patientRepository.save(patient);
            Patient savedPatient = patientRepository.getByCnp(patient.getCnp());
            PatientDTO savedPatientDTO = modelMapper.map(savedPatient, PatientDTO.class);
            return new EntityModel<>(
                    savedPatientDTO,
                    linkTo(methodOn(PatientController.class).findByCnp(savedPatient.getCnp())).withSelfRel());
        } else {
            throw new GlobalAlreadyExistsException("PATIENT");
        }
    }

    public EntityModel<PatientDTO> findByCnp(String cnp) {
        Optional<Patient> optionalPatient = patientRepository.findByCnp(cnp);
        if (optionalPatient.isPresent()) {
            Patient patient = optionalPatient.get();
            PatientDTO patientDTO = modelMapper.map(patient, PatientDTO.class);
            return new EntityModel<>(
                    patientDTO,
                    linkTo(methodOn(PatientController.class).findByCnp(patient.getCnp())).withSelfRel());
        } else {
            throw new GlobalNotFoundException("PATIENT");
        }
    }

    public EntityModel<PatientDTO> update(Patient patient) {
        if (patientRepository.existsById(patient.getId())) {
            Patient updatedPatient = patientRepository.save(patient);
            PatientDTO updatedPatientDTO = modelMapper.map(updatedPatient, PatientDTO.class);
            return new EntityModel<>(
                    updatedPatientDTO,
                    linkTo(methodOn(PatientController.class).findByCnp(updatedPatient.getCnp())).withSelfRel());
        } else {
            throw new GlobalNotFoundException("PATIENT");
        }
    }

    public EntityModel<PatientDTO> deleteByCnp(String cnp) {
        Optional<Patient> optionalPatient = patientRepository.findByCnp(cnp);
        if (optionalPatient.isPresent()) {
            Patient patient = optionalPatient.get();
            PatientDTO patientDTO = modelMapper.map(patient, PatientDTO.class);
            patientRepository.delete(patient);
            return new EntityModel<>(patientDTO);
        } else {
            throw new GlobalNotFoundException("PATIENT");
        }
    }
}
