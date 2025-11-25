package com.africom.configmanage.client;

import javax.validation.constraints.NotNull;

public class DepartementFeignDTO {

    private Long id;

    private String code;

    private String nom;

    private String email;

    public Long getOrganigrammeId() {
        return organigrammeId;
    }

    public void setOrganigrammeId(Long organigrammeId) {
        this.organigrammeId = organigrammeId;
    }

    private Long organigrammeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
