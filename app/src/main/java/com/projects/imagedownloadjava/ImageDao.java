package com.projects.imagedownloadjava;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;
@Dao
public interface ImageDao {

    @Query("SELECT * FROM imagestb")
    List<ImagesTb> getAll();

    @Insert
    long insert(ImagesTb imagesTb);

    @Query("SELECT filename  FROM imagestb WHERE filename = :filenameid LIMIT 1")
    String getItembyFilename(String filenameid);
}
