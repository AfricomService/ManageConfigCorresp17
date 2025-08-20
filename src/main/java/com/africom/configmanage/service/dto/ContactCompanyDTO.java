package com.africom.configmanage.service.dto;

import com.africom.configmanage.domain.enumeration.UsualStatus;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.africom.configmanage.domain.ContactCompany} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ContactCompanyDTO implements Serializable {

    private Long id;

    @NotNull
    private String nom;

    private String nomabrege;

    private String matrne;

    private String pays;

    private String adresse;

    private String tel;

    private String fax;

    private String email;

    private String webpage;

    private UsualStatus etat;

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

    public String getNomabrege() {
        return nomabrege;
    }

    public void setNomabrege(String nomabrege) {
        this.nomabrege = nomabrege;
    }

    public String getMatrne() {
        return matrne;
    }

    public void setMatrne(String matrne) {
        this.matrne = matrne;
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

    public String getWebpage() {
        return webpage;
    }

    public void setWebpage(String webpage) {
        this.webpage = webpage;
    }

    public UsualStatus getEtat() {
        return etat;
    }

    public void setEtat(UsualStatus etat) {
        this.etat = etat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ContactCompanyDTO)) {
            return false;
        }

        ContactCompanyDTO contactCompanyDTO = (ContactCompanyDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, contactCompanyDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ContactCompanyDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", nomabrege='" + getNomabrege() + "'" +
            ", matrne='" + getMatrne() + "'" +
            ", pays='" + getPays() + "'" +
            ", adresse='" + getAdresse() + "'" +
            ", tel='" + getTel() + "'" +
            ", fax='" + getFax() + "'" +
            ", email='" + getEmail() + "'" +
            ", webpage='" + getWebpage() + "'" +
            ", etat='" + getEtat() + "'" +
            "}";
    }
}
