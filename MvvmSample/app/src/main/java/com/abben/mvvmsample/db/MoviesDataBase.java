package com.abben.mvvmsample.db;

import android.app.Application;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;

import com.abben.mvvmsample.bean.Movie;

/**
 * Created by abben on 2017/10/25.
 */
@Database(entities = {Movie.class}, version = 1)
public abstract class MoviesDataBase extends RoomDatabase {
    private static MoviesDataBase sInstance;

    public static MoviesDataBase getsInstance(Application application, String dbName) {
        if (sInstance == null) {
            synchronized (MoviesDataBase.class) {
                sInstance = Room.databaseBuilder(application, MoviesDataBase.class, dbName).build();
            }
        }
        return sInstance;
    }

    public abstract MoviesDao moviesDao();
}
