package com.hartron.eoffice.domain;

import com.datastax.driver.mapping.annotations.*;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * A FileMovement.
 */

@Table(name = "fileMovement")
@Document(indexName = "filemovement")
public class FileMovement implements Serializable {

    private static final long serialVersionUID = 1L;

    @PartitionKey
    private UUID id;

    @NotNull
    private UUID fileId;

    @NotNull
    private String markFrom;

    @NotNull
    private String markTo;

    private String fileName;

    private ZonedDateTime markDate;

    private ZonedDateTime updateDate;

    @NotNull
    private String actionStatus;

    private String comment;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getFileId() {
        return fileId;
    }

    public FileMovement fileId(UUID fileId) {
        this.fileId = fileId;
        return this;
    }

    public void setFileId(UUID fileId) {
        this.fileId = fileId;
    }

    public String getMarkFrom() {
        return markFrom;
    }

    public FileMovement markFrom(String markFrom) {
        this.markFrom = markFrom;
        return this;
    }

    public void setMarkFrom(String markFrom) {
        this.markFrom = markFrom;
    }

    public String getMarkTo() {
        return markTo;
    }

    public FileMovement markTo(String markTo) {
        this.markTo = markTo;
        return this;
    }

    public void setMarkTo(String markTo) {
        this.markTo = markTo;
    }

    public String getFileName() {
        return fileName;
    }

    public FileMovement fileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public ZonedDateTime getMarkDate() {
        return markDate;
    }

    public FileMovement markDate(ZonedDateTime markDate) {
        this.markDate = markDate;
        return this;
    }

    public void setMarkDate(ZonedDateTime markDate) {
        this.markDate = markDate;
    }

    public ZonedDateTime getUpdateDate() {
        return updateDate;
    }

    public FileMovement updateDate(ZonedDateTime updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    public void setUpdateDate(ZonedDateTime updateDate) {
        this.updateDate = updateDate;
    }

    public String getActionStatus() {
        return actionStatus;
    }

    public FileMovement actionStatus(String actionStatus) {
        this.actionStatus = actionStatus;
        return this;
    }

    public void setActionStatus(String actionStatus) {
        this.actionStatus = actionStatus;
    }

    public String getComment() {
        return comment;
    }

    public FileMovement comment(String comment) {
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
        FileMovement fileMovement = (FileMovement) o;
        if (fileMovement.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, fileMovement.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "FileMovement{" +
            "id=" + id +
            ", fileId='" + fileId + "'" +
            ", markFrom='" + markFrom + "'" +
            ", markTo='" + markTo + "'" +
            ", fileName='" + fileName + "'" +
            ", markDate='" + markDate + "'" +
            ", updateDate='" + updateDate + "'" +
            ", actionStatus='" + actionStatus + "'" +
            ", comment='" + comment + "'" +
            '}';
    }
}
