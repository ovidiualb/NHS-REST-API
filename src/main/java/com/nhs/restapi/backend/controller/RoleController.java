package com.nhs.restapi.backend.controller;

import com.nhs.restapi.backend.exception.business.GlobalDatabaseException;
import com.nhs.restapi.backend.exception.business.GlobalNotFoundException;
import com.nhs.restapi.backend.model.dto.RoleDTO;
import com.nhs.restapi.backend.model.entity.Role;
import com.nhs.restapi.backend.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Role> findAll() {
        try {
            return roleService.findAll();
        } catch (Exception ex) {
            throw new GlobalDatabaseException("ROLE", ex.getCause().getCause().getMessage());
        }
    }

    @GetMapping("/by-name/{name}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EntityModel<RoleDTO>> findByName(@PathVariable String name) {
        try {
            RoleDTO roleDTO = roleService.findByName(name);
            EntityModel<RoleDTO> roleDTOEntity = new EntityModel<>(
                    roleDTO,
                    linkTo(methodOn(RoleController.class).findByName(name)).withSelfRel());
            return new ResponseEntity<>(roleDTOEntity, HttpStatus.OK);
        } catch (GlobalNotFoundException ex) {
            throw new GlobalNotFoundException(ex.getEntityName());
        } catch (Exception ex) {
            throw new GlobalDatabaseException("ROLE", ex.getCause().getCause().getMessage());
        }
    }
}
