package com.africom.configmanage.domain;

import java.io.Serializable;
import java.util.UUID;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Numsequentielle.
 */
@Entity
@Table(name = "numsequentielle")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Numsequentielle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "prefix")
    private String prefix;

    @NotNull
    @Column(name = "next_number", nullable = false)
    private Long nextNumber;

    @Column(name = "format")
    private String format;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public Numsequentielle id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public Numsequentielle prefix(String prefix) {
        this.setPrefix(prefix);
        return this;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public Long getNextNumber() {
        return this.nextNumber;
    }

    public Numsequentielle nextNumber(Long nextNumber) {
        this.setNextNumber(nextNumber);
        return this;
    }

    public void setNextNumber(Long nextNumber) {
        this.nextNumber = nextNumber;
    }

    public String getFormat() {
        return this.format;
    }

    public Numsequentielle format(String format) {
        this.setFormat(format);
        return this;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Numsequentielle)) {
            return false;
        }
        return id != null && id.equals(((Numsequentielle) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Numsequentielle{" +
            "id=" + getId() +
            ", prefix='" + getPrefix() + "'" +
            ", nextNumber=" + getNextNumber() +
            ", format='" + getFormat() + "'" +
            "}";
    }
}
