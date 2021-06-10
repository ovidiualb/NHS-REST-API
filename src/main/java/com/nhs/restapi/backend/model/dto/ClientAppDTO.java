package com.nhs.restapi.backend.model.dto;

import com.nhs.restapi.backend.model.entity.Role;
import org.springframework.hateoas.RepresentationModel;

import java.util.Set;

public class ClientAppDTO extends RepresentationModel<ClientAppDTO> {

    private int id;

    private String name;

    // NO PASSWORD.

    private int status;

    private Set<Role> roles;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void addRole(Role role) {
        roles.add(role);
    }
}
