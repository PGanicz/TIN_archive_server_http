package com.websocket.game.domain;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by piotr on 02.06.16.
 */
@Entity
public class File {
    private long id;
    private long userid;
    private String filename;
    private int fileSize;
    private String deviceName;
    private Set<FileVersion> oneToMany;

    @Id
    @Column(name = "id")
    @GeneratedValue
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "user_id")
    public long getUserId() {
        return userid;
    }

    public void setUserId(long id) {
        this.userid = id;
    }
    @Basic
    @Column(name = "file_name")
    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    @Basic
    @Column(name = "file_size")
    public int getFileSize() {
        return fileSize;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }

    @Basic
    @Column(name = "device_name")
    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        File file = (File) o;

        if (id != file.id) return false;
        if (fileSize != file.fileSize) return false;
        if (filename != null ? !filename.equals(file.filename) : file.filename != null) return false;
        if (deviceName != null ? !deviceName.equals(file.deviceName) : file.deviceName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (filename != null ? filename.hashCode() : 0);
        result = 31 * result + fileSize;
        result = 31 * result + (deviceName != null ? deviceName.hashCode() : 0);
        return result;
    }
}
