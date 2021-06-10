package com.nhs.restapi.backend.model.dto;

import org.springframework.hateoas.RepresentationModel;

public class RoleDTO extends RepresentationModel<RoleDTO> {

    private int id;

    private String name;

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

    @Override
    public String toString() {
        return name;
    }
}
