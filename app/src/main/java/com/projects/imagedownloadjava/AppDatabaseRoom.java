package com.projects.imagedownloadjava;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = ImagesTb.class, exportSchema = false,version = 1)
public abstract class AppDatabaseRoom extends RoomDatabase {
    private static final String DB_NAME = "intellischool_db";
    private static AppDatabaseRoom instance;

    public static synchronized AppDatabaseRoom getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabaseRoom.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return instance;
    }


    public abstract ImageDao imagesDao();

}
