package com.abben.mvvmsample.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.abben.mvvmsample.bean.Movie;

import java.util.List;

/**
 * Created by abben on 2017/10/25.
 */
@Dao
public interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovies(List<Movie> movies);

    @Query("SELECT * from movies")
    LiveData<List<Movie>> loadMovies();
}
