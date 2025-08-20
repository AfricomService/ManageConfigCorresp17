package com.africom.configmanage.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;

/**
 * A Myreport.
 */
@Entity
@Table(name = "myreport")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Myreport implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "date_created")
    private ZonedDateTime dateCreated;

    @Column(name = "login_name")
    private String loginName;

    @Column(name = "login_id")
    private Long loginId;

    @Column(name = "name_file")
    private String nameFile;

    @Column(name = "name_folder")
    private String nameFolder;

    @Column(name = "config_export_id")
    private Long configExportId;

    @ManyToOne
    private Configexport configexport;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Myreport id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDateCreated() {
        return this.dateCreated;
    }

    public Myreport dateCreated(ZonedDateTime dateCreated) {
        this.setDateCreated(dateCreated);
        return this;
    }

    public void setDateCreated(ZonedDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getLoginName() {
        return this.loginName;
    }

    public Myreport loginName(String loginName) {
        this.setLoginName(loginName);
        return this;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public Long getLoginId() {
        return this.loginId;
    }

    public Myreport loginId(Long loginId) {
        this.setLoginId(loginId);
        return this;
    }

    public void setLoginId(Long loginId) {
        this.loginId = loginId;
    }

    public String getNameFile() {
        return this.nameFile;
    }

    public Myreport nameFile(String nameFile) {
        this.setNameFile(nameFile);
        return this;
    }

    public void setNameFile(String nameFile) {
        this.nameFile = nameFile;
    }

    public String getNameFolder() {
        return this.nameFolder;
    }

    public Myreport nameFolder(String nameFolder) {
        this.setNameFolder(nameFolder);
        return this;
    }

    public void setNameFolder(String nameFolder) {
        this.nameFolder = nameFolder;
    }

    public Long getConfigExportId() {
        return this.configExportId;
    }

    public Myreport configExportId(Long configExportId) {
        this.setConfigExportId(configExportId);
        return this;
    }

    public void setConfigExportId(Long configExportId) {
        this.configExportId = configExportId;
    }

    public Configexport getConfigexport() {
        return this.configexport;
    }

    public void setConfigexport(Configexport configexport) {
        this.configexport = configexport;
    }

    public Myreport configexport(Configexport configexport) {
        this.setConfigexport(configexport);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Myreport)) {
            return false;
        }
        return id != null && id.equals(((Myreport) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Myreport{" +
            "id=" + getId() +
            ", dateCreated='" + getDateCreated() + "'" +
            ", loginName='" + getLoginName() + "'" +
            ", loginId=" + getLoginId() +
            ", nameFile='" + getNameFile() + "'" +
            ", nameFolder='" + getNameFolder() + "'" +
            ", configExportId=" + getConfigExportId() +
            "}";
    }
}
