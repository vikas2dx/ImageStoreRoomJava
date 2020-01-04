package com.projects.imagedownloadjava;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "imagestb")

public class ImagesTb {
    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "filename")
    private String filename;

    @ColumnInfo(name = "filepath")
    private String filepath;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }
}


