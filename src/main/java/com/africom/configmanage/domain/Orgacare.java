package com.africom.configmanage.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A Orgacare.
 */
@Entity
@Table(name = "orgacare")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Orgacare implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "company")
    private String company;

    @Column(name = "organigramme")
    private String organigramme;

    @Column(name = "status")
    private String status;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Orgacare id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompany() {
        return this.company;
    }

    public Orgacare company(String company) {
        this.setCompany(company);
        return this;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getOrganigramme() {
        return this.organigramme;
    }

    public Orgacare organigramme(String organigramme) {
        this.setOrganigramme(organigramme);
        return this;
    }

    public void setOrganigramme(String organigramme) {
        this.organigramme = organigramme;
    }

    public String getStatus() {
        return this.status;
    }

    public Orgacare status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Orgacare)) {
            return false;
        }
        return id != null && id.equals(((Orgacare) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Orgacare{" +
            "id=" + getId() +
            ", company='" + getCompany() + "'" +
            ", organigramme='" + getOrganigramme() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
