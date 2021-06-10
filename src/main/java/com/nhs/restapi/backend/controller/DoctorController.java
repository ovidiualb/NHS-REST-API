package com.nhs.restapi.backend.controller;

import com.nhs.restapi.backend.model.constant.DoctorSpecialty;
import com.nhs.restapi.backend.model.constant.DoctorTitle;
import com.nhs.restapi.backend.model.dto.DoctorDTO;
import com.nhs.restapi.backend.model.entity.Doctor;
import com.nhs.restapi.backend.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Stream;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    @Autowired
    DoctorService doctorService;

    @PostMapping
    public EntityModel<DoctorDTO> add(@RequestBody @Valid Doctor doctor) {
        return doctorService.add(doctor);
    }

    @GetMapping("/by-cnp/{cnp}")
    public EntityModel<DoctorDTO> findByCnp(@PathVariable String cnp) {
        return doctorService.findByCnp(cnp);
    }

    @GetMapping("/title")
    public String[] retrieveDoctorTitle() {
        return Stream.of(DoctorTitle.values())
                .map(Enum::name)
                .toArray(String[]::new);
    }

    @GetMapping("/specialty")
    public String[] retrieveDoctorSpecialty() {
        return Stream.of(DoctorSpecialty.values())
                .map(Enum::name)
                .toArray(String[]::new);
    }

    @PutMapping
    public EntityModel<DoctorDTO> update(@RequestBody @Valid DoctorDTO doctorDTO) {
        return doctorService.update(doctorDTO);
    }

    @DeleteMapping("/by-cnp/{cnp}")
    public EntityModel<DoctorDTO> deleteByCnp(@PathVariable String cnp) {
        return doctorService.deleteByCnp(cnp);
    }
}
