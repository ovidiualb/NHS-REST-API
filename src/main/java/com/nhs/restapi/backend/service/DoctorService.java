package com.nhs.restapi.backend.service;

import com.nhs.restapi.backend.controller.DoctorController;
import com.nhs.restapi.backend.exception.business.GlobalAlreadyExistsException;
import com.nhs.restapi.backend.exception.business.GlobalNotFoundException;
import com.nhs.restapi.backend.model.dto.DoctorDTO;
import com.nhs.restapi.backend.model.entity.Doctor;
import com.nhs.restapi.backend.model.entity.Institution;
import com.nhs.restapi.backend.repository.DoctorRepository;
import com.nhs.restapi.backend.repository.InstitutionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    InstitutionRepository institutionRepository;

    public EntityModel<DoctorDTO> add(Doctor doctor) {
        if (!doctorRepository.existsByCnp(doctor.getCnp())) {
            doctorRepository.save(doctor);
            Doctor savedDoctor = doctorRepository.getByCnp(doctor.getCnp());
            DoctorDTO savedDoctorDTO = modelMapper.map(savedDoctor, DoctorDTO.class);
            return new EntityModel<>(
                    savedDoctorDTO,
                    linkTo(methodOn(DoctorController.class).findByCnp(savedDoctorDTO.getCnp())).withSelfRel());
        } else {
            throw new GlobalAlreadyExistsException("DOCTOR");
        }
    }

    public EntityModel<DoctorDTO> findByCnp(String cnp) {
        Optional<Doctor> optionalDoctor = doctorRepository.findByCnp(cnp);
        if (optionalDoctor.isPresent()) {
            Doctor doctor = optionalDoctor.get();
            DoctorDTO doctorDTO = modelMapper.map(doctor, DoctorDTO.class);
            return new EntityModel<>(
                    doctorDTO,
                    linkTo(methodOn(DoctorController.class).findByCnp(cnp)).withSelfRel());
        } else {
            throw new GlobalNotFoundException("DOCTOR");
        }
    }

    public EntityModel<DoctorDTO> update(DoctorDTO doctorDTO) {
        if (doctorRepository.existsByCnp(doctorDTO.getCnp())) {
            Doctor doctor = doctorRepository.getByCnp(doctorDTO.getCnp());
            Set<Institution> newSetInst = new HashSet<>();

            if (doctorDTO.getInstitutionCUIs().isEmpty()) {
                doctor.setInstitutions(newSetInst);
            } else if (!doctorDTO.getInstitutionCUIs().contains(",")) {
                newSetInst.add(institutionRepository.getByCui(doctorDTO.getInstitutionCUIs()));
            } else {
                String[] CUIs = doctorDTO.getInstitutionCUIs().split(",");

                for (String cui : CUIs
                ) {
                    newSetInst.add(institutionRepository.getByCui(cui));
                }
            }
            doctor.setInstitutions(newSetInst);
            doctor.setCnp(doctorDTO.getCnp());
            doctor.setEmail(doctorDTO.getEmail());
            doctor.setFirstName(doctorDTO.getFirstName());
            doctor.setLastName(doctorDTO.getLastName());
            doctor.setLicenseNo(doctorDTO.getLicenseNo());
            doctor.setPhoneNoRo(doctorDTO.getPhoneNoRo());
            doctor.setSpecialties(doctorDTO.getSpecialties());
            doctor.setTitle(doctorDTO.getTitle());

            Doctor updatedDoctor = doctorRepository.save(doctor);
            DoctorDTO updatedDoctorDTO = modelMapper.map(updatedDoctor, DoctorDTO.class);
            return new EntityModel<>(
                    updatedDoctorDTO,
                    linkTo(methodOn(DoctorController.class).findByCnp(updatedDoctorDTO.getCnp())).withSelfRel());
        } else {
            throw new GlobalNotFoundException("DOCTOR");
        }
    }

    public EntityModel<DoctorDTO> deleteByCnp(String cnp) {
        Optional<Doctor> optionalDoctor = doctorRepository.findByCnp(cnp);
        if (optionalDoctor.isPresent()) {
            Doctor doctor = optionalDoctor.get();
            DoctorDTO doctorDTO = modelMapper.map(doctor, DoctorDTO.class);
            doctorRepository.delete(doctor);
            return new EntityModel<>(doctorDTO);
        } else {
            throw new GlobalNotFoundException("DOCTOR");
        }
    }
}
