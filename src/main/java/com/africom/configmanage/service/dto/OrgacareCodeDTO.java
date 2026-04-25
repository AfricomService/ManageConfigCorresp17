package com.africom.configmanage.service.dto;

public class OrgacareCodeDTO {

    private Long id;
    private String orgaCode;

    public OrgacareCodeDTO() {}

    public OrgacareCodeDTO(Long id, String orgaCode) {
        this.id = id;
        this.orgaCode = orgaCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrgaCode() {
        return orgaCode;
    }

    public void setOrgaCode(String orgaCode) {
        this.orgaCode = orgaCode;
    }
}
