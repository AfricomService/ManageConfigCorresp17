package com.africom.configmanage.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.africom.configmanage.domain.TypeDocDetailInfo} entity.
 */
public class TypeDocDetailInfoDTO implements Serializable {

    private Long id;

    private Long typeDocId;

    private Long detailInfoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTypeDocId() {
        return typeDocId;
    }

    public void setTypeDocId(Long typeDocId) {
        this.typeDocId = typeDocId;
    }

    public Long getDetailInfoId() {
        return detailInfoId;
    }

    public void setDetailInfoId(Long detailInfoId) {
        this.detailInfoId = detailInfoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TypeDocDetailInfoDTO)) {
            return false;
        }

        TypeDocDetailInfoDTO typeDocDetailInfoDTO = (TypeDocDetailInfoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, typeDocDetailInfoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TypeDocDetailInfoDTO{" +
            "id=" + getId() +
            ", typeDocId=" + getTypeDocId() +
            ", detailInfoId=" + getDetailInfoId() +
            "}";
    }
}
