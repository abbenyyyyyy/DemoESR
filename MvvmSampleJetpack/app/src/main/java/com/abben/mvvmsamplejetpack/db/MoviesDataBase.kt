package com.abben.mvvmsamplejetpack.db

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.abben.mvvmsamplejetpack.bean.Movie

/**
 *  @author abben
 *  on 2019/1/18
 */
@Database(entities = [Movie::class], version = 1)
abstract class MoviesDataBase : RoomDatabase() {

    companion object {
        private var sInstance: MoviesDataBase? = null

        fun getsInstance(application: Application): MoviesDataBase {
            if (sInstance == null) {
                sInstance = Room.databaseBuilder(application, MoviesDataBase::class.java, "movies.db").build()
            }
            return sInstance!!
        }
    }

    abstract fun moviesDao(): MoviesDao
}