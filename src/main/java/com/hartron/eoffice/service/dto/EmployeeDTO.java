package com.hartron.eoffice.service.dto;


import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the Employee entity.
 */
public class EmployeeDTO implements Serializable {

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

    public void setEmpid(String empid) {
        this.empid = empid;
    }
    public String getEmpname() {
        return empname;
    }

    public void setEmpname(String empname) {
        this.empname = empname;
    }
    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }
    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }
    public ZonedDateTime getDateofbirth() {
        return dateofbirth;
    }

    public void setDateofbirth(ZonedDateTime dateofbirth) {
        this.dateofbirth = dateofbirth;
    }
    public ZonedDateTime getDateofjoining() {
        return dateofjoining;
    }

    public void setDateofjoining(ZonedDateTime dateofjoining) {
        this.dateofjoining = dateofjoining;
    }
    public ZonedDateTime getRelievingdate() {
        return relievingdate;
    }

    public void setRelievingdate(ZonedDateTime relievingdate) {
        this.relievingdate = relievingdate;
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
    public String getMobilenumber() {
        return mobilenumber;
    }

    public void setMobilenumber(String mobilenumber) {
        this.mobilenumber = mobilenumber;
    }
    public String getCreatedby() {
        return createdby;
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

        EmployeeDTO employeeDTO = (EmployeeDTO) o;

        if ( ! Objects.equals(id, employeeDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "EmployeeDTO{" +
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
