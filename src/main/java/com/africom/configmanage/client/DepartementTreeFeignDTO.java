package com.africom.configmanage.client;

import java.util.ArrayList;
import java.util.List;

public class DepartementTreeFeignDTO {

    private Long id;
    private String code;
    private String nom;
    private List<DepartementTreeFeignDTO> children = new ArrayList<>();

    public DepartementTreeFeignDTO() {}

    public DepartementTreeFeignDTO(Long id, String code, String nom) {
        this.id = id;
        this.code = code;
        this.nom = nom;
    }

    // Getters and setters
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

    public List<DepartementTreeFeignDTO> getChildren() {
        return children;
    }

    public void setChildren(List<DepartementTreeFeignDTO> children) {
        this.children = children;
    }
}
