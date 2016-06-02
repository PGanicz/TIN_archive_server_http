package com.websocket.game.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Date;

/**
 * Created by piotr on 02.06.16.
 */
@Entity
public class FileVersion {
    private long fileId;
    private Date timestamp;
    private long receivedDataSize;

    @Id
    @Column(name = "fileId")
    public long getFileId() {
        return fileId;
    }

    public void setFileId(long fileId) {
        this.fileId = fileId;
    }

    @Basic
    @Column(name = "timestamp")
    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @Basic
    @Column(name = "receivedDataSize")
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