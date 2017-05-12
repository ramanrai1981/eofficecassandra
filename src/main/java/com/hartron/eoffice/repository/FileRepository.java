package com.hartron.eoffice.repository;

import com.hartron.eoffice.domain.File;

import com.datastax.driver.core.*;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Cassandra repository for the File entity.
 */
@Repository
public class FileRepository {

    private final Session session;

    private Mapper<File> mapper;

    private PreparedStatement findAllStmt;

    private PreparedStatement truncateStmt;

    public FileRepository(Session session) {
        this.session = session;
        this.mapper = new MappingManager(session).mapper(File.class);
        this.findAllStmt = session.prepare("SELECT * FROM file");
        this.truncateStmt = session.prepare("TRUNCATE file");
    }

    public List<File> findAll() {
        List<File> filesList = new ArrayList<>();
        BoundStatement stmt = findAllStmt.bind();
        session.execute(stmt).all().stream().map(
            row -> {
                File file = new File();
                file.setId(row.getUUID("id"));
                file.setFileNo(row.getString("fileNo"));
                file.setTitle(row.getString("title"));
                file.setTag(row.getString("tag"));
                file.setUploadDate(row.get("uploadDate", ZonedDateTime.class));
                file.setStatus(row.getBool("status"));
                return file;
            }
        ).forEach(filesList::add);
        return filesList;
    }

    public File findOne(UUID id) {
        return mapper.get(id);
    }

    public File save(File file) {
        if (file.getId() == null) {
            file.setId(UUID.randomUUID());
        }
        mapper.save(file);
        return file;
    }

    public void delete(UUID id) {
        mapper.delete(id);
    }

    public void deleteAll() {
        BoundStatement stmt = truncateStmt.bind();
        session.execute(stmt);
    }
}
