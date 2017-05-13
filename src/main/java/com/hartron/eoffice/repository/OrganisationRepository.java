package com.hartron.eoffice.repository;

import com.hartron.eoffice.domain.Organisation;

import com.datastax.driver.core.*;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Cassandra repository for the Organisation entity.
 */
@Repository
public class OrganisationRepository {

    private final Session session;

    private Mapper<Organisation> mapper;

    private PreparedStatement findAllStmt;

    private PreparedStatement truncateStmt;

    public OrganisationRepository(Session session) {
        this.session = session;
        this.mapper = new MappingManager(session).mapper(Organisation.class);
        this.findAllStmt = session.prepare("SELECT * FROM organisation");
        this.truncateStmt = session.prepare("TRUNCATE organisation");
    }

    public List<Organisation> findAll() {
        List<Organisation> organisationsList = new ArrayList<>();
        BoundStatement stmt = findAllStmt.bind();
        session.execute(stmt).all().stream().map(
            row -> {
                Organisation organisation = new Organisation();
                organisation.setId(row.getUUID("id"));
                organisation.setOrgname(row.getString("orgname"));
                organisation.setHod(row.getString("hod"));
                organisation.setAddress(row.getString("address"));
                organisation.setEstablishmentyear(row.get("establishmentyear", ZonedDateTime.class));
                organisation.setActive(row.getBool("active"));
                organisation.setCreatedate(row.get("createdate", ZonedDateTime.class));
                organisation.setUpdatedate(row.get("updatedate", ZonedDateTime.class));
                return organisation;
            }
        ).forEach(organisationsList::add);
        return organisationsList;
    }

    public Organisation findOne(UUID id) {
        return mapper.get(id);
    }

    public Organisation save(Organisation organisation) {
        if (organisation.getId() == null) {
            organisation.setId(UUID.randomUUID());
        }
        mapper.save(organisation);
        return organisation;
    }

    public void delete(UUID id) {
        mapper.delete(id);
    }

    public void deleteAll() {
        BoundStatement stmt = truncateStmt.bind();
        session.execute(stmt);
    }
}
