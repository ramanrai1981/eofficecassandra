package com.hartron.eoffice.domain;

import com.datastax.driver.mapping.annotations.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * A Organisation.
 */

@Table(name = "organisation")
public class Organisation implements Serializable {

    private static final long serialVersionUID = 1L;

    @PartitionKey
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

    public Organisation orgname(String orgname) {
        this.orgname = orgname;
        return this;
    }

    public void setOrgname(String orgname) {
        this.orgname = orgname;
    }

    public String getHod() {
        return hod;
    }

    public Organisation hod(String hod) {
        this.hod = hod;
        return this;
    }

    public void setHod(String hod) {
        this.hod = hod;
    }

    public String getAddress() {
        return address;
    }

    public Organisation address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ZonedDateTime getEstablishmentyear() {
        return establishmentyear;
    }

    public Organisation establishmentyear(ZonedDateTime establishmentyear) {
        this.establishmentyear = establishmentyear;
        return this;
    }

    public void setEstablishmentyear(ZonedDateTime establishmentyear) {
        this.establishmentyear = establishmentyear;
    }

    public Boolean isActive() {
        return active;
    }

    public Organisation active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public ZonedDateTime getCreatedate() {
        return createdate;
    }

    public Organisation createdate(ZonedDateTime createdate) {
        this.createdate = createdate;
        return this;
    }

    public void setCreatedate(ZonedDateTime createdate) {
        this.createdate = createdate;
    }

    public ZonedDateTime getUpdatedate() {
        return updatedate;
    }

    public Organisation updatedate(ZonedDateTime updatedate) {
        this.updatedate = updatedate;
        return this;
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
        Organisation organisation = (Organisation) o;
        if (organisation.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, organisation.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Organisation{" +
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
