package com.nhs.restapi.backend.model.dto;

public class PatientDTO {

    private int id;

    private String cnp;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNoRo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNoRo() {
        return phoneNoRo;
    }

    public void setPhoneNoRo(String phoneNoRo) {
        this.phoneNoRo = phoneNoRo;
    }
}
