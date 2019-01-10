package com.abben.mvvmsample.db;

import com.abben.mvvmsample.bean.Movie;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

/**
 *
 * Created by abben on 2017/10/25.
 */
@Dao
public interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovies(List<Movie> movies);

    @Query("SELECT * from movies WHERE type =:type")
    LiveData<List<Movie>> loadMovies(String type);

    @Query("SELECT * from movies")
    LiveData<List<Movie>> loadAllMovies();
}
