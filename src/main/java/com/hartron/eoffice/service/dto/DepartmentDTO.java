package com.hartron.eoffice.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the Department entity.
 */
public class DepartmentDTO implements Serializable {

    private UUID id;

    private String organisationid;

    @NotNull
    private String departmentname;

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
    public String getDepartmentname() {
        return departmentname;
    }

    public void setDepartmentname(String departmentname) {
        this.departmentname = departmentname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DepartmentDTO departmentDTO = (DepartmentDTO) o;

        if ( ! Objects.equals(id, departmentDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "DepartmentDTO{" +
            "id=" + id +
            ", organisationid='" + organisationid + "'" +
            ", departmentname='" + departmentname + "'" +
            '}';
    }
}
