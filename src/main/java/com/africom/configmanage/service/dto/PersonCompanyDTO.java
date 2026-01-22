package com.africom.configmanage.service.dto;

import com.africom.configmanage.domain.enumeration.UsualStatus;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.africom.configmanage.domain.PersonCompany} entity.
 */
public class PersonCompanyDTO implements Serializable {

    private Long id;

    @NotNull
    private String nom;

    private String pays;

    private String adresse;

    private String tel;

    private String fax;

    private String email;

    private String whatsapp;

    private UsualStatus etat;

    private Long contactCompanyId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWhatsapp() {
        return whatsapp;
    }

    public void setWhatsapp(String whatsapp) {
        this.whatsapp = whatsapp;
    }

    public UsualStatus getEtat() {
        return etat;
    }

    public void setEtat(UsualStatus etat) {
        this.etat = etat;
    }

    public Long getContactCompanyId() {
        return contactCompanyId;
    }

    public void setContactCompanyId(Long contactCompanyId) {
        this.contactCompanyId = contactCompanyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PersonCompanyDTO)) {
            return false;
        }

        PersonCompanyDTO personCompanyDTO = (PersonCompanyDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, personCompanyDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PersonCompanyDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", pays='" + getPays() + "'" +
            ", adresse='" + getAdresse() + "'" +
            ", tel='" + getTel() + "'" +
            ", fax='" + getFax() + "'" +
            ", email='" + getEmail() + "'" +
            ", whatsapp='" + getWhatsapp() + "'" +
            ", etat='" + getEtat() + "'" +
            ", contactCompanyId=" + getContactCompanyId() +
            "}";
    }
}
