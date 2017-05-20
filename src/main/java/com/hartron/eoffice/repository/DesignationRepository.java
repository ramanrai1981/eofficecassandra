package com.hartron.eoffice.repository;

import com.hartron.eoffice.domain.Designation;

import com.datastax.driver.core.*;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Cassandra repository for the Designation entity.
 */
@Repository
public class DesignationRepository {

    private final Session session;

    private Mapper<Designation> mapper;

    private PreparedStatement findAllStmt;

    private PreparedStatement truncateStmt;

    public DesignationRepository(Session session) {
        this.session = session;
        this.mapper = new MappingManager(session).mapper(Designation.class);
        this.findAllStmt = session.prepare("SELECT * FROM designation");
        this.truncateStmt = session.prepare("TRUNCATE designation");
    }

    public List<Designation> findAll() {
        List<Designation> designationsList = new ArrayList<>();
        BoundStatement stmt = findAllStmt.bind();
        session.execute(stmt).all().stream().map(
            row -> {
                Designation designation = new Designation();
                designation.setId(row.getUUID("id"));
                designation.setOrganisationid(row.getString("organisationid"));
                designation.setDepartmentid(row.getString("departmentid"));
                designation.setDesignation(row.getString("designation"));
                return designation;
            }
        ).forEach(designationsList::add);
        return designationsList;
    }

    public Designation findOne(UUID id) {
        return mapper.get(id);
    }

    public Designation save(Designation designation) {
        if (designation.getId() == null) {
            designation.setId(UUID.randomUUID());
        }
        mapper.save(designation);
        return designation;
    }

    public void delete(UUID id) {
        mapper.delete(id);
    }

    public void deleteAll() {
        BoundStatement stmt = truncateStmt.bind();
        session.execute(stmt);
    }
}
