package com.nhs.restapi.backend.controller;

import com.nhs.restapi.backend.exception.business.GlobalDatabaseException;
import com.nhs.restapi.backend.exception.business.GlobalNotFoundException;
import com.nhs.restapi.backend.model.dto.AdminDTO;
import com.nhs.restapi.backend.model.entity.Admin;
import com.nhs.restapi.backend.service.AdminService;
import com.nhs.restapi.backend.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/admins")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private RoleService roleService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EntityModel<AdminDTO>> add(@RequestBody @Valid Admin admin) {
        try {
            AdminDTO savedAdminDTO = adminService.add(admin);
            EntityModel<AdminDTO> savedAdminDTOEntity = new EntityModel<>(
                    savedAdminDTO,
                    linkTo(methodOn(AdminController.class).findByEmail(savedAdminDTO.getEmail())).withSelfRel());
            return new ResponseEntity<>(savedAdminDTOEntity, HttpStatus.CREATED);
        } catch (Exception ex) {
            throw new GlobalDatabaseException("ADMIN", ex.getCause().getCause().getMessage());
        }
    }

    @GetMapping("/by-email/{email}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EntityModel<AdminDTO>> findByEmail(@PathVariable String email) {
        try {
            AdminDTO adminDTO = adminService.findByEmail(email);
            EntityModel<AdminDTO> adminDTOEntity = new EntityModel<>(
                    adminDTO,
                    linkTo(methodOn(AdminController.class).findByEmail(email)).withSelfRel());
            return new ResponseEntity<>(adminDTOEntity, HttpStatus.OK);
        } catch (GlobalNotFoundException ex) {
            throw new GlobalNotFoundException(ex.getEntityName());
        } catch (Exception ex) {
            throw new GlobalDatabaseException("ADMIN", ex.getCause().getCause().getMessage());
        }
    }

    @GetMapping("/sensitive/by-email/{email}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EntityModel<Admin>> findSensitiveByEmail(@PathVariable String email) {
        try {
            Admin admin = adminService.findSensitiveByEmail(email);
            EntityModel<Admin> adminEntity = new EntityModel<>(
                    admin,
                    linkTo(methodOn(AdminController.class).findSensitiveByEmail(email)).withSelfRel());
            return new ResponseEntity<>(adminEntity, HttpStatus.OK);
        } catch (GlobalNotFoundException ex) {
            throw new GlobalNotFoundException(ex.getEntityName());
        } catch (Exception ex) {
            throw new GlobalDatabaseException("ADMIN", ex.getCause().getCause().getMessage());
        }
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EntityModel<AdminDTO>> update(@RequestBody @Valid Admin admin) {
        try {
            AdminDTO updatedAdminDTO = adminService.update(admin);
            EntityModel<AdminDTO> updatedAdminDTOEntity = new EntityModel<>(
                    updatedAdminDTO,
                    linkTo(methodOn(AdminController.class).findByEmail(admin.getEmail())).withSelfRel());
            return new ResponseEntity<>(updatedAdminDTOEntity, HttpStatus.CREATED);
        } catch (GlobalNotFoundException ex) {
            throw new GlobalNotFoundException(ex.getEntityName());
        } catch (Exception ex) {
            throw new GlobalDatabaseException("ADMIN", ex.getCause().getCause().getMessage());
        }
    }

    @DeleteMapping("/by-email/{email}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EntityModel<AdminDTO>> deleteByEmail(@PathVariable String email) {
        try {
            AdminDTO deletedAdminDTO = adminService.deleteByEmail(email);
            EntityModel<AdminDTO> deletedAdminDTOEntity = new EntityModel<>(deletedAdminDTO);
            return new ResponseEntity<>(deletedAdminDTOEntity, HttpStatus.OK);
        } catch (GlobalNotFoundException ex) {
            throw new GlobalNotFoundException(ex.getEntityName());
        } catch (Exception ex) {
            throw new GlobalDatabaseException("ADMIN", ex.getCause().getCause().getMessage());
        }
    }
}
