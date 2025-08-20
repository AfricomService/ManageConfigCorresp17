package com.africom.configmanage.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link com.africom.configmanage.domain.Myreport} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MyreportDTO implements Serializable {

    private Long id;

    private ZonedDateTime dateCreated;

    private String loginName;

    private Long loginId;

    private String nameFile;

    private String nameFolder;

    private Long configExportId;

    private ConfigexportDTO configexport;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(ZonedDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public Long getLoginId() {
        return loginId;
    }

    public void setLoginId(Long loginId) {
        this.loginId = loginId;
    }

    public String getNameFile() {
        return nameFile;
    }

    public void setNameFile(String nameFile) {
        this.nameFile = nameFile;
    }

    public String getNameFolder() {
        return nameFolder;
    }

    public void setNameFolder(String nameFolder) {
        this.nameFolder = nameFolder;
    }

    public Long getConfigExportId() {
        return configExportId;
    }

    public void setConfigExportId(Long configExportId) {
        this.configExportId = configExportId;
    }

    public ConfigexportDTO getConfigexport() {
        return configexport;
    }

    public void setConfigexport(ConfigexportDTO configexport) {
        this.configexport = configexport;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MyreportDTO)) {
            return false;
        }

        MyreportDTO myreportDTO = (MyreportDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, myreportDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MyreportDTO{" +
            "id=" + getId() +
            ", dateCreated='" + getDateCreated() + "'" +
            ", loginName='" + getLoginName() + "'" +
            ", loginId=" + getLoginId() +
            ", nameFile='" + getNameFile() + "'" +
            ", nameFolder='" + getNameFolder() + "'" +
            ", configExportId=" + getConfigExportId() +
            ", configexport=" + getConfigexport() +
            "}";
    }
}
