package com.hartron.eoffice.repository;

import com.hartron.eoffice.domain.FileLog;

import com.datastax.driver.core.*;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Cassandra repository for the FileLog entity.
 */
@Repository
public class FileLogRepository {

    private final Session session;

    private Mapper<FileLog> mapper;

    private PreparedStatement findAllStmt;

    private PreparedStatement truncateStmt;

    public FileLogRepository(Session session) {
        this.session = session;
        this.mapper = new MappingManager(session).mapper(FileLog.class);
        this.findAllStmt = session.prepare("SELECT * FROM fileLog");
        this.truncateStmt = session.prepare("TRUNCATE fileLog");
    }

    public List<FileLog> findAll() {
        List<FileLog> fileLogsList = new ArrayList<>();
        BoundStatement stmt = findAllStmt.bind();
        session.execute(stmt).all().stream().map(
            row -> {
                FileLog fileLog = new FileLog();
                fileLog.setId(row.getUUID("id"));
                fileLog.setFileNo(row.getString("fileNo"));
                fileLog.setTitle(row.getString("title"));
                fileLog.setMarkFrom(row.getString("markFrom"));
                fileLog.setMarkTo(row.getString("markTo"));
                fileLog.setMarkDate(row.get("markDate", ZonedDateTime.class));
                fileLog.setUpdateDate(row.get("updateDate", ZonedDateTime.class));
                fileLog.setComment(row.getString("comment"));
                return fileLog;
            }
        ).forEach(fileLogsList::add);
        return fileLogsList;
    }

    public FileLog findOne(UUID id) {
        return mapper.get(id);
    }

    public FileLog save(FileLog fileLog) {
        if (fileLog.getId() == null) {
            fileLog.setId(UUID.randomUUID());
        }
        mapper.save(fileLog);
        return fileLog;
    }

    public void delete(UUID id) {
        mapper.delete(id);
    }

    public void deleteAll() {
        BoundStatement stmt = truncateStmt.bind();
        session.execute(stmt);
    }
}
