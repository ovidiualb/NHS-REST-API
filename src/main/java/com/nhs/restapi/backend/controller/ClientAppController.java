package com.nhs.restapi.backend.controller;

import com.nhs.restapi.backend.model.dto.ClientAppCredentialsDTO;
import com.nhs.restapi.backend.model.dto.ClientAppDTO;
import com.nhs.restapi.backend.model.dto.RoleDTO;
import com.nhs.restapi.backend.model.entity.ClientApp;
import com.nhs.restapi.backend.service.ClientAppService;
import com.nhs.restapi.backend.service.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/client-apps")
public class ClientAppController {

    @Autowired
    private ClientAppService clientAppService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/with-role-name/{roleName}")
    @PreAuthorize("hasRole('ADMIN')")
    public EntityModel<ClientAppDTO> addWithRoleName(@RequestBody @Valid ClientAppCredentialsDTO clientAppCredentialsDTO, @PathVariable String roleName) throws Exception {
        RoleDTO role = roleService.findByName(roleName);
        return clientAppService.add(clientAppCredentialsDTO, role);
    }

    @GetMapping("/by-name/{name}")
    @PreAuthorize("hasRole('ADMIN')")
    public EntityModel<ClientAppDTO> findByName(@PathVariable String name) {
        return clientAppService.findByName(name);
    }

    @PutMapping("/with-role-name/{roleName}")
    @PreAuthorize("hasRole('ADMIN')")
    public EntityModel<ClientAppDTO> update(@RequestBody @Valid ClientApp clientApp, @PathVariable String roleName) {
        return clientAppService.update(clientApp, roleName);
    }

    @DeleteMapping("/by-name/{name}")
    @PreAuthorize("hasRole('ADMIN')")
    public EntityModel<ClientAppDTO> deleteByName(@PathVariable String name) {
        return clientAppService.deleteByName(name);
    }
}
