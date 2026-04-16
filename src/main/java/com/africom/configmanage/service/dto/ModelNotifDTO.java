package com.africom.configmanage.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.africom.configmanage.domain.ModelNotif} entity.
 */
public class ModelNotifDTO implements Serializable {


    private UUID id;

    @NotNull
    private String name;

    private String famille;

    private String code;

    private String content;

    private String subjectFormula;

    @NotNull
    private String senderEmailFormule;

    private String recepientEmailFormule;

    @NotNull
    private ZonedDateTime createdDate;

    private List<Map<String, String>> ccEmailFormule;

    private List<Map<String, String>> cciEmailFormule;

    private Boolean saveCopy;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFamille() {
        return famille;
    }

    public void setFamille(String famille) {
        this.famille = famille;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSubjectFormula() {
        return subjectFormula;
    }

    public void setSubjectFormula(String subjectFormula) {
        this.subjectFormula = subjectFormula;
    }

    public String getSenderEmailFormule() {
        return senderEmailFormule;
    }

    public void setSenderEmailFormule(String senderEmailFormule) {
        this.senderEmailFormule = senderEmailFormule;
    }

    public String getRecepientEmailFormule() {
        return recepientEmailFormule;
    }

    public void setRecepientEmailFormule(String recepientEmailFormule) {
        this.recepientEmailFormule = recepientEmailFormule;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public List<Map<String, String>> getCcEmailFormule() {
        return ccEmailFormule;
    }

    public void setCcEmailFormule(List<Map<String, String>> ccEmailFormule) {
        this.ccEmailFormule = ccEmailFormule;
    }

    public List<Map<String, String>> getCciEmailFormule() {
        return cciEmailFormule;
    }

    public void setCciEmailFormule(List<Map<String, String>> cciEmailFormule) {
        this.cciEmailFormule = cciEmailFormule;
    }

    public Boolean getSaveCopy() {
        return saveCopy;
    }

    public void setSaveCopy(Boolean saveCopy) {
        this.saveCopy = saveCopy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ModelNotifDTO)) {
            return false;
        }

        ModelNotifDTO modelNotifDTO = (ModelNotifDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, modelNotifDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    @Override
    public String toString() {
        return "ModelNotifDTO{" +
            "id='" + getId() + "'" +
            ", name='" + getName() + "'" +
            ", famille='" + getFamille() + "'" +
            ", code='" + getCode() + "'" +
            ", content='" + getContent() + "'" +
            ", subjectFormula='" + getSubjectFormula() + "'" +
            ", senderEmailFormule='" + getSenderEmailFormule() + "'" +
            ", recepientEmailFormule='" + getRecepientEmailFormule() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", ccEmailFormule='" + getCcEmailFormule() + "'" +
            ", cciEmailFormule='" + getCciEmailFormule() + "'" +
            ", saveCopy='" + getSaveCopy() + "'" +
            "}";
    }
}
