package com.africom.configmanage.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.africom.configmanage.domain.Orgacare} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OrgacareDTO implements Serializable {

    private Long id;

    private String company;

    private String organigramme;

    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getOrganigramme() {
        return organigramme;
    }

    public void setOrganigramme(String organigramme) {
        this.organigramme = organigramme;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrgacareDTO)) {
            return false;
        }

        OrgacareDTO orgacareDTO = (OrgacareDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, orgacareDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrgacareDTO{" +
            "id=" + getId() +
            ", company='" + getCompany() + "'" +
            ", organigramme='" + getOrganigramme() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
