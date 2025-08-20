package com.africom.configmanage.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.africom.configmanage.domain.Configexport} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ConfigexportDTO implements Serializable {

    private Long id;

    private String label;

    private Boolean active;

    @Lob
    private byte[] excelFile;

    private String excelFileContentType;
    private ZonedDateTime dateCreated;

    private String configMappingJson;

    private String readAccess;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public byte[] getExcelFile() {
        return excelFile;
    }

    public void setExcelFile(byte[] excelFile) {
        this.excelFile = excelFile;
    }

    public String getExcelFileContentType() {
        return excelFileContentType;
    }

    public void setExcelFileContentType(String excelFileContentType) {
        this.excelFileContentType = excelFileContentType;
    }

    public ZonedDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(ZonedDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getConfigMappingJson() {
        return configMappingJson;
    }

    public void setConfigMappingJson(String configMappingJson) {
        this.configMappingJson = configMappingJson;
    }

    public String getReadAccess() {
        return readAccess;
    }

    public void setReadAccess(String readAccess) {
        this.readAccess = readAccess;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConfigexportDTO)) {
            return false;
        }

        ConfigexportDTO configexportDTO = (ConfigexportDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, configexportDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ConfigexportDTO{" +
            "id=" + getId() +
            ", label='" + getLabel() + "'" +
            ", active='" + getActive() + "'" +
            ", excelFile='" + getExcelFile() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", configMappingJson='" + getConfigMappingJson() + "'" +
            ", readAccess='" + getReadAccess() + "'" +
            "}";
    }
}
