package com.websocket.game.domain;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by piotr on 02.06.16.
 */
@Entity
public class File {
    private long id;
    private String filename;
    private int fileSize;
    private String deviceName;
    private Set<FileVersion> oneToMany;

    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "filename")
    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    @Basic
    @Column(name = "fileSize")
    public int getFileSize() {
        return fileSize;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }

    @Basic
    @Column(name = "deviceName")
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

    @OneToMany
    public Set<FileVersion> getOneToMany() {
        return oneToMany;
    }

    public void setOneToMany(Set<FileVersion> oneToMany) {
        this.oneToMany = oneToMany;
    }
}