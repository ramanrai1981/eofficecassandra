package com.hartron.eoffice.service.dto;


import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the Organisation entity.
 */
public class OrganisationDTO implements Serializable {

    private UUID id;

    private String orgname;

    private String address;

    private Boolean active;

    private ZonedDateTime createdate;

    private ZonedDateTime updatedate;

    @NotNull
    private String owner;

    private ZonedDateTime establishmentdate;

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
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
    public ZonedDateTime getEstablishmentdate() {
        return establishmentdate;
    }

    public void setEstablishmentdate(ZonedDateTime establishmentdate) {
        this.establishmentdate = establishmentdate;
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
            ", address='" + address + "'" +
            ", active='" + active + "'" +
            ", createdate='" + createdate + "'" +
            ", updatedate='" + updatedate + "'" +
            ", owner='" + owner + "'" +
            ", establishmentdate='" + establishmentdate + "'" +
            '}';
    }
}
