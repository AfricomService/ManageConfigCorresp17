package com.africom.configmanage.service.dto;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.africom.configmanage.domain.Numsequentielle} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NumsequentielleDTO implements Serializable {

    @NotNull
    private UUID id;

    private String prefix;

    @NotNull
    private Long nextNumber;

    private String format;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public Long getNextNumber() {
        return nextNumber;
    }

    public void setNextNumber(Long nextNumber) {
        this.nextNumber = nextNumber;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NumsequentielleDTO)) {
            return false;
        }

        NumsequentielleDTO numsequentielleDTO = (NumsequentielleDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, numsequentielleDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NumsequentielleDTO{" +
            "id='" + getId() + "'" +
            ", prefix='" + getPrefix() + "'" +
            ", nextNumber=" + getNextNumber() +
            ", format='" + getFormat() + "'" +
            "}";
    }
}
