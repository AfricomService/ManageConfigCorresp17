package com.africom.configmanage.service.dto;

import com.africom.configmanage.domain.enumeration.UsualStatus;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.africom.configmanage.domain.Typedocument} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TypedocumentDTO implements Serializable {

    private Long id;

    private String nom;

    private String defautdest;

    private String defaultobject;

    private String defaultencopie;

    private UsualStatus usualstatus;

    private String labelDD;

    private String labelDCC;

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

    public String getDefautdest() {
        return defautdest;
    }

    public void setDefautdest(String defautdest) {
        this.defautdest = defautdest;
    }

    public String getDefaultobject() {
        return defaultobject;
    }

    public void setDefaultobject(String defaultobject) {
        this.defaultobject = defaultobject;
    }

    public String getDefaultencopie() {
        return defaultencopie;
    }

    public void setDefaultencopie(String defaultencopie) {
        this.defaultencopie = defaultencopie;
    }

    public UsualStatus getUsualstatus() {
        return usualstatus;
    }

    public void setUsualstatus(UsualStatus usualstatus) {
        this.usualstatus = usualstatus;
    }

    public String getLabelDD() {
        return labelDD;
    }

    public void setLabelDD(String labelDD) {
        this.labelDD = labelDD;
    }

    public String getLabelDCC() {
        return labelDCC;
    }

    public void setLabelDCC(String labelDCC) {
        this.labelDCC = labelDCC;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TypedocumentDTO)) {
            return false;
        }

        TypedocumentDTO typedocumentDTO = (TypedocumentDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, typedocumentDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TypedocumentDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", defautdest='" + getDefautdest() + "'" +
            ", defaultobject='" + getDefaultobject() + "'" +
            ", defaultencopie='" + getDefaultencopie() + "'" +
            ", usualstatus='" + getUsualstatus() + "'" +
            ", labelDD='" + getLabelDD() + "'" +
            ", labelDCC='" + getLabelDCC() + "'" +
            "}";
    }
}
