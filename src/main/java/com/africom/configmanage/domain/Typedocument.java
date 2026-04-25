package com.africom.configmanage.domain;

import com.africom.configmanage.domain.enumeration.UsualStatus;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A Typedocument.
 */
@Entity
@Table(name = "typedocument")
public class Typedocument implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "defautdest")
    private String defautdest;

    @Column(name = "defaultobject")
    private String defaultobject;

    @Column(name = "defaultencopie")
    private String defaultencopie;

    @Enumerated(EnumType.STRING)
    @Column(name = "usualstatus")
    private UsualStatus usualstatus;

    @Column(name = "label_dd")
    private String labelDD;

    @Column(name = "label_dcc")
    private String labelDCC;

    @Column(name = "corresp_type")
    private String correspType;

    @Column(name = "code_unique_societe")
    private String codeUniqueSociete;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Typedocument id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public Typedocument nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDefautdest() {
        return this.defautdest;
    }

    public Typedocument defautdest(String defautdest) {
        this.setDefautdest(defautdest);
        return this;
    }

    public void setDefautdest(String defautdest) {
        this.defautdest = defautdest;
    }

    public String getDefaultobject() {
        return this.defaultobject;
    }

    public Typedocument defaultobject(String defaultobject) {
        this.setDefaultobject(defaultobject);
        return this;
    }

    public void setDefaultobject(String defaultobject) {
        this.defaultobject = defaultobject;
    }

    public String getDefaultencopie() {
        return this.defaultencopie;
    }

    public Typedocument defaultencopie(String defaultencopie) {
        this.setDefaultencopie(defaultencopie);
        return this;
    }

    public void setDefaultencopie(String defaultencopie) {
        this.defaultencopie = defaultencopie;
    }

    public UsualStatus getUsualstatus() {
        return this.usualstatus;
    }

    public Typedocument usualstatus(UsualStatus usualstatus) {
        this.setUsualstatus(usualstatus);
        return this;
    }

    public void setUsualstatus(UsualStatus usualstatus) {
        this.usualstatus = usualstatus;
    }

    public String getLabelDD() {
        return this.labelDD;
    }

    public Typedocument labelDD(String labelDD) {
        this.setLabelDD(labelDD);
        return this;
    }

    public void setLabelDD(String labelDD) {
        this.labelDD = labelDD;
    }

    public String getLabelDCC() {
        return this.labelDCC;
    }

    public Typedocument labelDCC(String labelDCC) {
        this.setLabelDCC(labelDCC);
        return this;
    }

    public void setLabelDCC(String labelDCC) {
        this.labelDCC = labelDCC;
    }

    public String getCorrespType() {
        return this.correspType;
    }

    public Typedocument correspType(String correspType) {
        this.setCorrespType(correspType);
        return this;
    }

    public void setCorrespType(String correspType) {
        this.correspType = correspType;
    }

    public String getCodeUniqueSociete() {
        return this.codeUniqueSociete;
    }

    public Typedocument codeUniqueSociete(String codeUniqueSociete) {
        this.setCodeUniqueSociete(codeUniqueSociete);
        return this;
    }

    public void setCodeUniqueSociete(String codeUniqueSociete) {
        this.codeUniqueSociete = codeUniqueSociete;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Typedocument)) {
            return false;
        }
        return id != null && id.equals(((Typedocument) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Typedocument{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", defautdest='" + getDefautdest() + "'" +
            ", defaultobject='" + getDefaultobject() + "'" +
            ", defaultencopie='" + getDefaultencopie() + "'" +
            ", usualstatus='" + getUsualstatus() + "'" +
            ", labelDD='" + getLabelDD() + "'" +
            ", labelDCC='" + getLabelDCC() + "'" +
            ", correspType='" + getCorrespType() + "'" +
            ", codeUniqueSociete='" + getCodeUniqueSociete() + "'" +
            "}";
    }
}
