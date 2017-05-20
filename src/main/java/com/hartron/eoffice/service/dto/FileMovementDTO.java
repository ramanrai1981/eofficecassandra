package com.hartron.eoffice.service.dto;


import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the FileMovement entity.
 */
public class FileMovementDTO implements Serializable {

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

    public void setFileId(UUID fileId) {
        this.fileId = fileId;
    }
    public String getMarkFrom() {
        return markFrom;
    }

    public void setMarkFrom(String markFrom) {
        this.markFrom = markFrom;
    }
    public String getMarkTo() {
        return markTo;
    }

    public void setMarkTo(String markTo) {
        this.markTo = markTo;
    }
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public ZonedDateTime getMarkDate() {
        return markDate;
    }

    public void setMarkDate(ZonedDateTime markDate) {
        this.markDate = markDate;
    }
    public ZonedDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(ZonedDateTime updateDate) {
        this.updateDate = updateDate;
    }
    public String getActionStatus() {
        return actionStatus;
    }

    public void setActionStatus(String actionStatus) {
        this.actionStatus = actionStatus;
    }
    public String getComment() {
        return comment;
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

        FileMovementDTO fileMovementDTO = (FileMovementDTO) o;

        if ( ! Objects.equals(id, fileMovementDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "FileMovementDTO{" +
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
