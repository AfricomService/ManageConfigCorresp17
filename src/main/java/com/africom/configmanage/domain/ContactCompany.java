package com.africom.configmanage.domain;

import com.africom.configmanage.domain.enumeration.UsualStatus;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A ContactCompany.
 */
@Entity
@Table(name = "contact_company")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ContactCompany implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "nomabrege")
    private String nomabrege;

    @Column(name = "matrne")
    private String matrne;

    @Column(name = "pays")
    private String pays;

    @Column(name = "adresse")
    private String adresse;

    @Column(name = "tel")
    private String tel;

    @Column(name = "fax")
    private String fax;

    @Column(name = "email")
    private String email;

    @Column(name = "webpage")
    private String webpage;

    @Enumerated(EnumType.STRING)
    @Column(name = "etat")
    private UsualStatus etat;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ContactCompany id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public ContactCompany nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNomabrege() {
        return this.nomabrege;
    }

    public ContactCompany nomabrege(String nomabrege) {
        this.setNomabrege(nomabrege);
        return this;
    }

    public void setNomabrege(String nomabrege) {
        this.nomabrege = nomabrege;
    }

    public String getMatrne() {
        return this.matrne;
    }

    public ContactCompany matrne(String matrne) {
        this.setMatrne(matrne);
        return this;
    }

    public void setMatrne(String matrne) {
        this.matrne = matrne;
    }

    public String getPays() {
        return this.pays;
    }

    public ContactCompany pays(String pays) {
        this.setPays(pays);
        return this;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getAdresse() {
        return this.adresse;
    }

    public ContactCompany adresse(String adresse) {
        this.setAdresse(adresse);
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTel() {
        return this.tel;
    }

    public ContactCompany tel(String tel) {
        this.setTel(tel);
        return this;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getFax() {
        return this.fax;
    }

    public ContactCompany fax(String fax) {
        this.setFax(fax);
        return this;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return this.email;
    }

    public ContactCompany email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebpage() {
        return this.webpage;
    }

    public ContactCompany webpage(String webpage) {
        this.setWebpage(webpage);
        return this;
    }

    public void setWebpage(String webpage) {
        this.webpage = webpage;
    }

    public UsualStatus getEtat() {
        return this.etat;
    }

    public ContactCompany etat(UsualStatus etat) {
        this.setEtat(etat);
        return this;
    }

    public void setEtat(UsualStatus etat) {
        this.etat = etat;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ContactCompany)) {
            return false;
        }
        return id != null && id.equals(((ContactCompany) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ContactCompany{" +
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
