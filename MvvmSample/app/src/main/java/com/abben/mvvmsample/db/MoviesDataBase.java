package com.abben.mvvmsample.db;

import android.app.Application;

import com.abben.mvvmsample.bean.Movie;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * Created by abben on 2017/10/25.
 */
@Database(entities = {Movie.class}, version = 1)
public abstract class MoviesDataBase extends RoomDatabase {
    private static MoviesDataBase sInstance;

    public static MoviesDataBase getsInstance(Application application) {
        if (sInstance == null) {
            synchronized (MoviesDataBase.class) {
                sInstance = Room.databaseBuilder(application, MoviesDataBase.class, "movies.db").build();
            }
        }
        return sInstance;
    }

    public abstract MoviesDao moviesDao();
}
