package com.abben.mvvmsamplejetpack.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.abben.mvvmsamplejetpack.bean.Movie

/**
 *  @author abben
 *  on 2019/1/18
 */
@Dao
interface MoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movies: List<Movie>)

    @Query("SELECT * from movies WHERE type =:type")
    fun loadMovies(type: String): LiveData<List<Movie>>

    @Query("SELECT * from movies")
    fun loadAllMovies(): LiveData<List<Movie>>
}