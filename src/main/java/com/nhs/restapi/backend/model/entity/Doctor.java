package com.nhs.restapi.backend.model.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Set;

@Entity
@Table(name = "doctors")
public class Doctor {

    @Id
    @NotNull(message = "CNP CANNOT BE EMPTY")
    @Pattern(regexp = "[1-8]\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])(0[1-9]|[1-4]\\d|5[0-2]|99)\\d{4}", message = "INVALID CNP")
    @Column(name = "cnp", nullable = false, unique = true, columnDefinition = "VARCHAR(255)")
    private String cnp;

    @Column(name = "title", nullable = false, columnDefinition = "VARCHAR(255)")
    private String title;

    @NotNull(message = "FIRST NAME CANNOT BE EMPTY")
    @Column(name = "first_name", nullable = false, columnDefinition = "VARCHAR(255)")
    private String firstName;

    @NotNull(message = "LAST NAME CANNOT BE EMPTY")
    @Column(name = "last_name", nullable = false, columnDefinition = "VARCHAR(255)")
    private String lastName;

    @NotNull(message = "EMAIL CANNOT BE EMPTY")
    @Pattern(regexp = ".+@.+\\..+", message = "INVALID EMAIL ADDRESS")
    @Column(name = "email", nullable = false, unique = true, columnDefinition = "VARCHAR(255)")
    private String email;

    @NotNull(message = "PHONE NUMBER CANNOT BE EMPTY")
    @Pattern(regexp = "^0040\\d{9}$", message = "INVALID PHONE NUMBER")
    @Column(name = "phone_ro", nullable = false, unique = true, columnDefinition = "VARCHAR(255)")
    private String phoneNoRo;

    @NotNull(message = "MEDICAL LICENSE NUMBER CANNOT BE EMPTY")
    @Column(name = "medical_license", nullable = false, unique = true, columnDefinition = "VARCHAR(255)")
    private String licenseNo;

    @Column(name = "specialties", nullable = false, columnDefinition = "VARCHAR(255)")
    private String specialties;

    @ManyToMany(cascade = CascadeType.DETACH)
    @JoinTable(
            name = "doctors_institutions",
            joinColumns = @JoinColumn(name = "doctor_cnp", referencedColumnName = "cnp"),
            inverseJoinColumns = @JoinColumn(name = "institution_id", referencedColumnName = "id"))
    private Set<Institution> institutions;

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getLicenseNo() {
        return licenseNo;
    }

    public void setLicenseNo(String licenseNo) {
        this.licenseNo = licenseNo;
    }

    public String getSpecialties() {
        return specialties;
    }

    public void setSpecialties(String specialties) {
        this.specialties = specialties;
    }

    public Set<Institution> getInstitutions() {
        return institutions;
    }

    public void setInstitutions(Set<Institution> institutions) {
        this.institutions = institutions;
    }
}
