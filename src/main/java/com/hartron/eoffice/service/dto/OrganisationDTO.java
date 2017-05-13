package com.hartron.eoffice.service.dto;


import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the Organisation entity.
 */
public class OrganisationDTO implements Serializable {

    private UUID id;

    private String orgname;

    private String hod;

    private String address;

    private ZonedDateTime establishmentyear;

    private Boolean active;

    private ZonedDateTime createdate;

    private ZonedDateTime updatedate;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
    public String getOrgname() {
        return orgname;
    }

    public void setOrgname(String orgname) {
        this.orgname = orgname;
    }
    public String getHod() {
        return hod;
    }

    public void setHod(String hod) {
        this.hod = hod;
    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public ZonedDateTime getEstablishmentyear() {
        return establishmentyear;
    }

    public void setEstablishmentyear(ZonedDateTime establishmentyear) {
        this.establishmentyear = establishmentyear;
    }
    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
    public ZonedDateTime getCreatedate() {
        return createdate;
    }

    public void setCreatedate(ZonedDateTime createdate) {
        this.createdate = createdate;
    }
    public ZonedDateTime getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(ZonedDateTime updatedate) {
        this.updatedate = updatedate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OrganisationDTO organisationDTO = (OrganisationDTO) o;

        if ( ! Objects.equals(id, organisationDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "OrganisationDTO{" +
            "id=" + id +
            ", orgname='" + orgname + "'" +
            ", hod='" + hod + "'" +
            ", address='" + address + "'" +
            ", establishmentyear='" + establishmentyear + "'" +
            ", active='" + active + "'" +
            ", createdate='" + createdate + "'" +
            ", updatedate='" + updatedate + "'" +
            '}';
    }
}
