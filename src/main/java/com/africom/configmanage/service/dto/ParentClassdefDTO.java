package com.africom.configmanage.service.dto;

import com.africom.configmanage.domain.enumeration.CorrespType;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.africom.configmanage.domain.ParentClassdef} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ParentClassdefDTO implements Serializable {

    private Long id;

    private CorrespType type;

    private NumsequentielleDTO sequenceId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CorrespType getType() {
        return type;
    }

    public void setType(CorrespType type) {
        this.type = type;
    }

    public NumsequentielleDTO getSequenceId() {
        return sequenceId;
    }

    public void setSequenceId(NumsequentielleDTO sequenceId) {
        this.sequenceId = sequenceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ParentClassdefDTO)) {
            return false;
        }

        ParentClassdefDTO parentClassdefDTO = (ParentClassdefDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, parentClassdefDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ParentClassdefDTO{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", sequenceId=" + getSequenceId() +
            "}";
    }
}
