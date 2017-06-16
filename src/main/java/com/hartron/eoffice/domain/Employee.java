package com.hartron.eoffice.domain;

import com.datastax.driver.mapping.annotations.*;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * A Employee.
 */

@Table(name = "employee")
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    @PartitionKey
    private UUID id;

    @NotNull
    private String empid;

    private String empname;

    private String department;

    private String designation;

    private String emailid;

    private ZonedDateTime dateofbirth;

    private ZonedDateTime dateofjoining;

    private ZonedDateTime relievingdate;

    private Boolean active;

    private ZonedDateTime createdate;

    private ZonedDateTime updatedate;

    @NotNull
    private String mobilenumber;

    private String createdby;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmpid() {
        return empid;
    }

    public Employee empid(String empid) {
        this.empid = empid;
        return this;
    }

    public void setEmpid(String empid) {
        this.empid = empid;
    }

    public String getEmpname() {
        return empname;
    }

    public Employee empname(String empname) {
        this.empname = empname;
        return this;
    }

    public void setEmpname(String empname) {
        this.empname = empname;
    }

    public String getDepartment() {
        return department;
    }

    public Employee department(String department) {
        this.department = department;
        return this;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDesignation() {
        return designation;
    }

    public Employee designation(String designation) {
        this.designation = designation;
        return this;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getEmailid() {
        return emailid;
    }

    public Employee emailid(String emailid) {
        this.emailid = emailid;
        return this;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public ZonedDateTime getDateofbirth() {
        return dateofbirth;
    }

    public Employee dateofbirth(ZonedDateTime dateofbirth) {
        this.dateofbirth = dateofbirth;
        return this;
    }

    public void setDateofbirth(ZonedDateTime dateofbirth) {
        this.dateofbirth = dateofbirth;
    }

    public ZonedDateTime getDateofjoining() {
        return dateofjoining;
    }

    public Employee dateofjoining(ZonedDateTime dateofjoining) {
        this.dateofjoining = dateofjoining;
        return this;
    }

    public void setDateofjoining(ZonedDateTime dateofjoining) {
        this.dateofjoining = dateofjoining;
    }

    public ZonedDateTime getRelievingdate() {
        return relievingdate;
    }

    public Employee relievingdate(ZonedDateTime relievingdate) {
        this.relievingdate = relievingdate;
        return this;
    }

    public void setRelievingdate(ZonedDateTime relievingdate) {
        this.relievingdate = relievingdate;
    }

    public Boolean isActive() {
        return active;
    }

    public Employee active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public ZonedDateTime getCreatedate() {
        return createdate;
    }

    public Employee createdate(ZonedDateTime createdate) {
        this.createdate = createdate;
        return this;
    }

    public void setCreatedate(ZonedDateTime createdate) {
        this.createdate = createdate;
    }

    public ZonedDateTime getUpdatedate() {
        return updatedate;
    }

    public Employee updatedate(ZonedDateTime updatedate) {
        this.updatedate = updatedate;
        return this;
    }

    public void setUpdatedate(ZonedDateTime updatedate) {
        this.updatedate = updatedate;
    }

    public String getMobilenumber() {
        return mobilenumber;
    }

    public Employee mobilenumber(String mobilenumber) {
        this.mobilenumber = mobilenumber;
        return this;
    }

    public void setMobilenumber(String mobilenumber) {
        this.mobilenumber = mobilenumber;
    }

    public String getCreatedby() {
        return createdby;
    }

    public Employee createdby(String createdby) {
        this.createdby = createdby;
        return this;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Employee employee = (Employee) o;
        if (employee.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, employee.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Employee{" +
            "id=" + id +
            ", empid='" + empid + "'" +
            ", empname='" + empname + "'" +
            ", department='" + department + "'" +
            ", designation='" + designation + "'" +
            ", emailid='" + emailid + "'" +
            ", dateofbirth='" + dateofbirth + "'" +
            ", dateofjoining='" + dateofjoining + "'" +
            ", relievingdate='" + relievingdate + "'" +
            ", active='" + active + "'" +
            ", createdate='" + createdate + "'" +
            ", updatedate='" + updatedate + "'" +
            ", mobilenumber='" + mobilenumber + "'" +
            ", createdby='" + createdby + "'" +
            '}';
    }
}
