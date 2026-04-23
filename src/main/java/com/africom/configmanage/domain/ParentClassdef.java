package com.africom.configmanage.domain;

import com.africom.configmanage.domain.enumeration.CorrespType;
import java.io.Serializable;
import java.util.UUID;
import javax.persistence.*;

/**
 * A ParentClassdef.
 */
@Entity
@Table(name = "parent_classdef")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ParentClassdef implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private CorrespType type;

    @Column(name = "formule")
    private String formule;

    @ManyToOne
    private Numsequentielle sequenceId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ParentClassdef id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CorrespType getType() {
        return this.type;
    }

    public ParentClassdef type(CorrespType type) {
        this.setType(type);
        return this;
    }

    public void setType(CorrespType type) {
        this.type = type;
    }

    public String getFormule() {
        return this.formule;
    }

    public ParentClassdef formule(String formule) {
        this.setFormule(formule);
        return this;
    }

    public void setFormule(String formule) {
        this.formule = formule;
    }

    public Numsequentielle getSequenceId() {
        return this.sequenceId;
    }

    public void setSequenceId(Numsequentielle numsequentielle) {
        this.sequenceId = numsequentielle;
    }

    public ParentClassdef sequenceId(Numsequentielle numsequentielle) {
        this.setSequenceId(numsequentielle);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ParentClassdef)) {
            return false;
        }
        return id != null && id.equals(((ParentClassdef) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ParentClassdef{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", formule='" + getFormule() + "'" +
            "}";
    }
}
