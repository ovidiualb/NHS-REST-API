package com.nhs.restapi.backend.controller;

import com.nhs.restapi.backend.model.constant.NurseSpecialty;
import com.nhs.restapi.backend.model.constant.NurseTitle;
import com.nhs.restapi.backend.model.dto.NurseDTO;
import com.nhs.restapi.backend.model.entity.Nurse;
import com.nhs.restapi.backend.service.NurseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Stream;

@RestController
@RequestMapping("/nurses")
public class NurseController {

    @Autowired
    NurseService nurseService;

    @PostMapping
    public EntityModel<NurseDTO> add(@RequestBody @Valid Nurse nurse) {
        return nurseService.add(nurse);
    }

    @GetMapping("/by-cnp/{cnp}")
    public EntityModel<NurseDTO> findByCnp(@PathVariable String cnp) {
        return nurseService.findByCnp(cnp);
    }

    @GetMapping("/specialty")
    public String[] getNurseSpecialty() {
        return Stream.of(NurseSpecialty.values())
                .map(Enum::name)
                .toArray(String[]::new);
    }

    @GetMapping("/title")
    public String[] getNurseTitle() {
        return Stream.of(NurseTitle.values())
                .map(Enum::name)
                .toArray(String[]::new);
    }

    @PutMapping
    public EntityModel<NurseDTO> update(@RequestBody @Valid Nurse nurse) {
        return nurseService.update(nurse);
    }

    @DeleteMapping("/by-cnp/{cnp}")
    public EntityModel<NurseDTO> deleteByCnp(@PathVariable String cnp) {
        return nurseService.deleteByCnp(cnp);
    }
}
