package com.hartron.eoffice.domain;

import com.datastax.driver.mapping.annotations.*;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A Department.
 */

@Table(name = "department")
@Document(indexName = "department")
public class Department implements Serializable {

    private static final long serialVersionUID = 1L;

    @PartitionKey
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

    public Department organisationid(String organisationid) {
        this.organisationid = organisationid;
        return this;
    }

    public void setOrganisationid(String organisationid) {
        this.organisationid = organisationid;
    }

    public String getDepartmentname() {
        return departmentname;
    }

    public Department departmentname(String departmentname) {
        this.departmentname = departmentname;
        return this;
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
        Department department = (Department) o;
        if (department.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, department.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Department{" +
            "id=" + id +
            ", organisationid='" + organisationid + "'" +
            ", departmentname='" + departmentname + "'" +
            '}';
    }
}
