package com.hartron.eoffice.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the Designation entity.
 */
public class DesignationDTO implements Serializable {

    private UUID id;

    @NotNull
    private String organisationid;

    @NotNull
    private String departmentid;

    @NotNull
    private String designation;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
    public String getOrganisationid() {
        return organisationid;
    }

    public void setOrganisationid(String organisationid) {
        this.organisationid = organisationid;
    }
    public String getDepartmentid() {
        return departmentid;
    }

    public void setDepartmentid(String departmentid) {
        this.departmentid = departmentid;
    }
    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DesignationDTO designationDTO = (DesignationDTO) o;

        if ( ! Objects.equals(id, designationDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "DesignationDTO{" +
            "id=" + id +
            ", organisationid='" + organisationid + "'" +
            ", departmentid='" + departmentid + "'" +
            ", designation='" + designation + "'" +
            '}';
    }
}
