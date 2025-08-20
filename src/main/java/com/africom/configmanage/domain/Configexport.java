package com.africom.configmanage.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;

/**
 * A Configexport.
 */
@Entity
@Table(name = "configexport")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Configexport implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "label")
    private String label;

    @Column(name = "active")
    private Boolean active;

    @Lob
    @Column(name = "excel_file")
    private byte[] excelFile;

    @Column(name = "excel_file_content_type")
    private String excelFileContentType;

    @Column(name = "date_created")
    private ZonedDateTime dateCreated;

    @Column(name = "config_mapping_json")
    private String configMappingJson;

    @Column(name = "read_access")
    private String readAccess;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Configexport id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return this.label;
    }

    public Configexport label(String label) {
        this.setLabel(label);
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Boolean getActive() {
        return this.active;
    }

    public Configexport active(Boolean active) {
        this.setActive(active);
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public byte[] getExcelFile() {
        return this.excelFile;
    }

    public Configexport excelFile(byte[] excelFile) {
        this.setExcelFile(excelFile);
        return this;
    }

    public void setExcelFile(byte[] excelFile) {
        this.excelFile = excelFile;
    }

    public String getExcelFileContentType() {
        return this.excelFileContentType;
    }

    public Configexport excelFileContentType(String excelFileContentType) {
        this.excelFileContentType = excelFileContentType;
        return this;
    }

    public void setExcelFileContentType(String excelFileContentType) {
        this.excelFileContentType = excelFileContentType;
    }

    public ZonedDateTime getDateCreated() {
        return this.dateCreated;
    }

    public Configexport dateCreated(ZonedDateTime dateCreated) {
        this.setDateCreated(dateCreated);
        return this;
    }

    public void setDateCreated(ZonedDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getConfigMappingJson() {
        return this.configMappingJson;
    }

    public Configexport configMappingJson(String configMappingJson) {
        this.setConfigMappingJson(configMappingJson);
        return this;
    }

    public void setConfigMappingJson(String configMappingJson) {
        this.configMappingJson = configMappingJson;
    }

    public String getReadAccess() {
        return this.readAccess;
    }

    public Configexport readAccess(String readAccess) {
        this.setReadAccess(readAccess);
        return this;
    }

    public void setReadAccess(String readAccess) {
        this.readAccess = readAccess;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Configexport)) {
            return false;
        }
        return id != null && id.equals(((Configexport) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Configexport{" +
            "id=" + getId() +
            ", label='" + getLabel() + "'" +
            ", active='" + getActive() + "'" +
            ", excelFile='" + getExcelFile() + "'" +
            ", excelFileContentType='" + getExcelFileContentType() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", configMappingJson='" + getConfigMappingJson() + "'" +
            ", readAccess='" + getReadAccess() + "'" +
            "}";
    }
}
