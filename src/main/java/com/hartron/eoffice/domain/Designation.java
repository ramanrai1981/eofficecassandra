package com.hartron.eoffice.domain;

import com.datastax.driver.mapping.annotations.*;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A Designation.
 */

@Table(name = "designation")
@Document(indexName = "designation")
public class Designation implements Serializable {

    private static final long serialVersionUID = 1L;

    @PartitionKey
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

    public Designation organisationid(String organisationid) {
        this.organisationid = organisationid;
        return this;
    }

    public void setOrganisationid(String organisationid) {
        this.organisationid = organisationid;
    }

    public String getDepartmentid() {
        return departmentid;
    }

    public Designation departmentid(String departmentid) {
        this.departmentid = departmentid;
        return this;
    }

    public void setDepartmentid(String departmentid) {
        this.departmentid = departmentid;
    }

    public String getDesignation() {
        return designation;
    }

    public Designation designation(String designation) {
        this.designation = designation;
        return this;
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
        Designation designation = (Designation) o;
        if (designation.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, designation.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Designation{" +
            "id=" + id +
            ", organisationid='" + organisationid + "'" +
            ", departmentid='" + departmentid + "'" +
            ", designation='" + designation + "'" +
            '}';
    }
}
