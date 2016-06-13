package com.websocket.game.domain;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by piotr on 02.06.16.
 */
@Entity
@Table(name = "file_version")
public class FileVersion {
    private long fileId;
    private Timestamp timestamp;
    private long receivedDataSize;
    private long versionId;

    @Override
    public String toString() {
        return "FileVersion{" +
                "fileId=" + fileId +
                ", timestamp=" + timestamp +
                ", receivedDataSize=" + receivedDataSize +
                ", versionId=" + versionId +
                '}';
    }

    @Id
    @GeneratedValue
    @Column(name = "version_id")
    public long getVersionId() { return versionId;}
    public void setVersionId(long versionId) {this.versionId =versionId;}

    @Column(name = "file_id")
    public long getFileId() {
        return fileId;
    }

    public void setFileId(long fileId) {
        this.fileId = fileId;
    }

    @Basic
    @Column(name = "timestamp")
    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Basic
    @Column(name = "received_data_size")
    public long getReceivedDataSize() {
        return receivedDataSize;
    }

    public void setReceivedDataSize(long receivedDataSize) {
        this.receivedDataSize = receivedDataSize;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FileVersion that = (FileVersion) o;

        if (fileId != that.fileId) return false;
        if (receivedDataSize != that.receivedDataSize) return false;
        if (timestamp != null ? !timestamp.equals(that.timestamp) : that.timestamp != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (fileId ^ (fileId >>> 32));
        result = 31 * result + (timestamp != null ? timestamp.hashCode() : 0);
        result = 31 * result + (int) (receivedDataSize ^ (receivedDataSize >>> 32));
        return result;
    }
}
