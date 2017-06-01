package com.hartron.eoffice.domain;

import com.datastax.driver.mapping.annotations.*;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * A FileLog.
 */

@Table(name = "fileLog")
@Document(indexName = "filelog")
public class FileLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @PartitionKey
    private UUID id;

    private String fileNo;

    private String title;

    private String markFrom;

    private String markTo;

    private ZonedDateTime markDate;

    private ZonedDateTime updateDate;

    private String comment;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFileNo() {
        return fileNo;
    }

    public FileLog fileNo(String fileNo) {
        this.fileNo = fileNo;
        return this;
    }

    public void setFileNo(String fileNo) {
        this.fileNo = fileNo;
    }

    public String getTitle() {
        return title;
    }

    public FileLog title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMarkFrom() {
        return markFrom;
    }

    public FileLog markFrom(String markFrom) {
        this.markFrom = markFrom;
        return this;
    }

    public void setMarkFrom(String markFrom) {
        this.markFrom = markFrom;
    }

    public String getMarkTo() {
        return markTo;
    }

    public FileLog markTo(String markTo) {
        this.markTo = markTo;
        return this;
    }

    public void setMarkTo(String markTo) {
        this.markTo = markTo;
    }

    public ZonedDateTime getMarkDate() {
        return markDate;
    }

    public FileLog markDate(ZonedDateTime markDate) {
        this.markDate = markDate;
        return this;
    }

    public void setMarkDate(ZonedDateTime markDate) {
        this.markDate = markDate;
    }

    public ZonedDateTime getUpdateDate() {
        return updateDate;
    }

    public FileLog updateDate(ZonedDateTime updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    public void setUpdateDate(ZonedDateTime updateDate) {
        this.updateDate = updateDate;
    }

    public String getComment() {
        return comment;
    }

    public FileLog comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FileLog fileLog = (FileLog) o;
        if (fileLog.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, fileLog.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "FileLog{" +
            "id=" + id +
            ", fileNo='" + fileNo + "'" +
            ", title='" + title + "'" +
            ", markFrom='" + markFrom + "'" +
            ", markTo='" + markTo + "'" +
            ", markDate='" + markDate + "'" +
            ", updateDate='" + updateDate + "'" +
            ", comment='" + comment + "'" +
            '}';
    }
}
