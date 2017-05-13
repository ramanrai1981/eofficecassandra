package com.hartron.eoffice.repository;

import com.hartron.eoffice.domain.FileMovement;

import com.datastax.driver.core.*;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Cassandra repository for the FileMovement entity.
 */
@Repository
public class FileMovementRepository {

    private final Session session;

    private Mapper<FileMovement> mapper;

    private PreparedStatement findAllStmt;

    private PreparedStatement truncateStmt;

    public FileMovementRepository(Session session) {
        this.session = session;
        this.mapper = new MappingManager(session).mapper(FileMovement.class);
        this.findAllStmt = session.prepare("SELECT * FROM fileMovement");
        this.truncateStmt = session.prepare("TRUNCATE fileMovement");
    }

    public List<FileMovement> findAll() {
        List<FileMovement> fileMovementsList = new ArrayList<>();
        BoundStatement stmt = findAllStmt.bind();
        session.execute(stmt).all().stream().map(
            row -> {
                FileMovement fileMovement = new FileMovement();
                fileMovement.setId(row.getUUID("id"));
                fileMovement.setFileId(row.getUUID("fileId"));
                fileMovement.setMarkFrom(row.getString("markFrom"));
                fileMovement.setMarkTo(row.getString("markTo"));
                fileMovement.setFileName(row.getString("fileName"));
                fileMovement.setMarkDate(row.get("markDate", ZonedDateTime.class));
                fileMovement.setUpdateDate(row.get("updateDate", ZonedDateTime.class));
                fileMovement.setActionStatus(row.getString("actionStatus"));
                fileMovement.setComment(row.getString("comment"));
                return fileMovement;
            }
        ).forEach(fileMovementsList::add);
        return fileMovementsList;
    }

    public FileMovement findOne(UUID id) {
        return mapper.get(id);
    }

    public FileMovement save(FileMovement fileMovement) {
        if (fileMovement.getId() == null) {
            fileMovement.setId(UUID.randomUUID());
        }
        mapper.save(fileMovement);
        return fileMovement;
    }

    public void delete(UUID id) {
        mapper.delete(id);
    }

    public void deleteAll() {
        BoundStatement stmt = truncateStmt.bind();
        session.execute(stmt);
    }
}
