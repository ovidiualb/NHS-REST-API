package com.nhs.restapi.backend.model.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class ClientAppCredentialsDTO {

    // NO ID.

    @NotNull(message = "CLIENT APP NAME CANNOT BE EMPTY")
    private String name;

    @NotNull(message = "PASSWORD CANNOT BE EMPTY")
    @Pattern(regexp = "((?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*]).{8,32})", message = "INVALID PASSWORD")
    private String password;

    // NO STATUS.

    // NO ROLES.

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
