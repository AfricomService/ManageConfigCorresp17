package com.africom.configmanage.domain;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonNodeBinaryType;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

/**
 * A ModelNotif.
 */
@Entity
@Table(name = "model_notif")
@TypeDefs({
    @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class),
    @TypeDef(name = "jsonb-node", typeClass = JsonNodeBinaryType.class)
})
public class ModelNotif implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private UUID id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "famille")
    private String famille;

    @Column(name = "code", unique = true)
    private String code;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "content")
    private String content;

    @Column(name = "subject_formula")
    private String subjectFormula;

    @NotNull
    @Column(name = "sender_email_formule", nullable = false)
    private String senderEmailFormule;

    @Column(name = "recepient_email_formule")
    private String recepientEmailFormule;

    @NotNull
    @Column(name = "created_date", nullable = false)
    private ZonedDateTime createdDate;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb", name = "cc_email_formule")
    private List<Map<String, String>> ccEmailFormule;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb", name = "cci_email_formule")
    private List<Map<String, String>> cciEmailFormule;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public ModelNotif id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public ModelNotif name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFamille() {
        return this.famille;
    }

    public ModelNotif famille(String famille) {
        this.setFamille(famille);
        return this;
    }

    public void setFamille(String famille) {
        this.famille = famille;
    }

    public String getCode() {
        return this.code;
    }

    public ModelNotif code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getContent() {
        return this.content;
    }

    public ModelNotif content(String content) {
        this.setContent(content);
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSubjectFormula() {
        return this.subjectFormula;
    }

    public ModelNotif subjectFormula(String subjectFormula) {
        this.setSubjectFormula(subjectFormula);
        return this;
    }

    public void setSubjectFormula(String subjectFormula) {
        this.subjectFormula = subjectFormula;
    }

    public String getSenderEmailFormule() {
        return this.senderEmailFormule;
    }

    public ModelNotif senderEmailFormule(String senderEmailFormule) {
        this.setSenderEmailFormule(senderEmailFormule);
        return this;
    }

    public void setSenderEmailFormule(String senderEmailFormule) {
        this.senderEmailFormule = senderEmailFormule;
    }

    public String getRecepientEmailFormule() {
        return this.recepientEmailFormule;
    }

    public ModelNotif recepientEmailFormule(String recepientEmailFormule) {
        this.setRecepientEmailFormule(recepientEmailFormule);
        return this;
    }

    public void setRecepientEmailFormule(String recepientEmailFormule) {
        this.recepientEmailFormule = recepientEmailFormule;
    }

    public ZonedDateTime getCreatedDate() {
        return this.createdDate;
    }

    public ModelNotif createdDate(ZonedDateTime createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public List<Map<String, String>> getCcEmailFormule() {
        return this.ccEmailFormule;
    }

    public ModelNotif ccEmailFormule(List<Map<String, String>> ccEmailFormule) {
        this.setCcEmailFormule(ccEmailFormule);
        return this;
    }

    public void setCcEmailFormule(List<Map<String, String>> ccEmailFormule) {
        this.ccEmailFormule = ccEmailFormule;
    }

    public List<Map<String, String>> getCciEmailFormule() {
        return this.cciEmailFormule;
    }

    public ModelNotif cciEmailFormule(List<Map<String, String>> cciEmailFormule) {
        this.setCciEmailFormule(cciEmailFormule);
        return this;
    }

    public void setCciEmailFormule(List<Map<String, String>> cciEmailFormule) {
        this.cciEmailFormule = cciEmailFormule;
    }


    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ModelNotif)) {
            return false;
        }
        return id != null && id.equals(((ModelNotif) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ModelNotif{" +
            "id=" + getId() +
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
            "}";
    }
}
