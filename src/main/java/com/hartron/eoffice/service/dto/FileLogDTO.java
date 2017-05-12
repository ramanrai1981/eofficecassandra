package com.hartron.eoffice.service.dto;


import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the FileLog entity.
 */
public class FileLogDTO implements Serializable {

    private UUID id;

    private String fileNo;

    private String title;

    private String markFrom;

    private String markTo;

    private ZonedDateTime markDate;

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

    public void setFileNo(String fileNo) {
        this.fileNo = fileNo;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
    public ZonedDateTime getMarkDate() {
        return markDate;
    }

    public void setMarkDate(ZonedDateTime markDate) {
        this.markDate = markDate;
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

        FileLogDTO fileLogDTO = (FileLogDTO) o;

        if ( ! Objects.equals(id, fileLogDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "FileLogDTO{" +
            "id=" + id +
            ", fileNo='" + fileNo + "'" +
            ", title='" + title + "'" +
            ", markFrom='" + markFrom + "'" +
            ", markTo='" + markTo + "'" +
            ", markDate='" + markDate + "'" +
            ", comment='" + comment + "'" +
            '}';
    }
}
