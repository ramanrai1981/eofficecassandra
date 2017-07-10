package com.hartron.eoffice.repository;

import com.hartron.eoffice.domain.Employee;

import com.datastax.driver.core.*;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Cassandra repository for the Employee entity.
 */
@Repository
public class EmployeeRepository {

    private final Session session;

    private Mapper<Employee> mapper;

    private PreparedStatement findAllStmt;

    private PreparedStatement truncateStmt;

    public EmployeeRepository(Session session) {
        this.session = session;
        this.mapper = new MappingManager(session).mapper(Employee.class);
        this.findAllStmt = session.prepare("SELECT * FROM employee");
        this.truncateStmt = session.prepare("TRUNCATE employee");
    }

    public List<Employee> findAll() {
        List<Employee> employeesList = new ArrayList<>();
        BoundStatement stmt = findAllStmt.bind();
        session.execute(stmt).all().stream().map(
            row -> {
                Employee employee = new Employee();
                employee.setId(row.getUUID("id"));
                employee.setEmpid(row.getString("empid"));
                employee.setEmpname(row.getString("empname"));
                employee.setDepartment(row.getString("department"));
                employee.setDesignation(row.getString("designation"));
                employee.setEmailid(row.getString("emailid"));
                employee.setDateofbirth(row.get("dateofbirth", ZonedDateTime.class));
                employee.setDateofjoining(row.get("dateofjoining", ZonedDateTime.class));
                employee.setRelievingdate(row.get("relievingdate", ZonedDateTime.class));
                employee.setActive(row.getBool("active"));
                employee.setCreatedate(row.get("createdate", ZonedDateTime.class));
                employee.setUpdatedate(row.get("updatedate", ZonedDateTime.class));
                employee.setMobilenumber(row.getString("mobilenumber"));
                return employee;
            }
        ).forEach(employeesList::add);
        return employeesList;
    }

    public Employee findOne(UUID id) {
        return mapper.get(id);
    }

    public Employee save(Employee employee) {
        if (employee.getId() == null) {
            employee.setId(UUID.randomUUID());
        }
        mapper.save(employee);
        return employee;
    }

    public void delete(UUID id) {
        mapper.delete(id);
    }

    public void deleteAll() {
        BoundStatement stmt = truncateStmt.bind();
        session.execute(stmt);
    }
}
