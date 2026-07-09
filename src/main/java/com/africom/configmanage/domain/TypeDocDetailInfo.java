package com.africom.configmanage.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A TypeDocDetailInfo.
 */
@Entity
@Table(name = "type_doc_detail_info")
public class TypeDocDetailInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "type_doc_id")
    private Long typeDocId;

    @Column(name = "detail_info_id")
    private Long detailInfoId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TypeDocDetailInfo id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTypeDocId() {
        return this.typeDocId;
    }

    public TypeDocDetailInfo typeDocId(Long typeDocId) {
        this.setTypeDocId(typeDocId);
        return this;
    }

    public void setTypeDocId(Long typeDocId) {
        this.typeDocId = typeDocId;
    }

    public Long getDetailInfoId() {
        return this.detailInfoId;
    }

    public TypeDocDetailInfo detailInfoId(Long detailInfoId) {
        this.setDetailInfoId(detailInfoId);
        return this;
    }

    public void setDetailInfoId(Long detailInfoId) {
        this.detailInfoId = detailInfoId;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TypeDocDetailInfo)) {
            return false;
        }
        return id != null && id.equals(((TypeDocDetailInfo) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TypeDocDetailInfo{" +
            "id=" + getId() +
            ", typeDocId=" + getTypeDocId() +
            ", detailInfoId=" + getDetailInfoId() +
            "}";
    }
}
