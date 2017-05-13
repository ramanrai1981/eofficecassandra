package com.hartron.eoffice.domain;

import com.datastax.driver.mapping.annotations.*;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * A File.
 */

@Table(name = "file")
public class File implements Serializable {

    private static final long serialVersionUID = 1L;

    @PartitionKey
    private UUID id;

    @NotNull
    private String fileNo;

    private String title;

    private String tag;

    private ZonedDateTime uploadDate;

    @NotNull
    private Boolean status;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFileNo() {
        return fileNo;
    }

    public File fileNo(String fileNo) {
        this.fileNo = fileNo;
        return this;
    }

    public void setFileNo(String fileNo) {
        this.fileNo = fileNo;
    }

    public String getTitle() {
        return title;
    }

    public File title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTag() {
        return tag;
    }

    public File tag(String tag) {
        this.tag = tag;
        return this;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public ZonedDateTime getUploadDate() {
        return uploadDate;
    }

    public File uploadDate(ZonedDateTime uploadDate) {
        this.uploadDate = uploadDate;
        return this;
    }

    public void setUploadDate(ZonedDateTime uploadDate) {
        this.uploadDate = uploadDate;
    }

    public Boolean isStatus() {
        return status;
    }

    public File status(Boolean status) {
        this.status = status;
        return this;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        File file = (File) o;
        if (file.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, file.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "File{" +
            "id=" + id +
            ", fileNo='" + fileNo + "'" +
            ", title='" + title + "'" +
            ", tag='" + tag + "'" +
            ", uploadDate='" + uploadDate + "'" +
            ", status='" + status + "'" +
            '}';
    }
}
