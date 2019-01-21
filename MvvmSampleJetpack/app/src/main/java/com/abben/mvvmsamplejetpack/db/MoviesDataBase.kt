package com.abben.mvvmsamplejetpack.db

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.abben.mvvmsamplejetpack.bean.Movie

/**
 *  @author abben
 *  on 2019/1/18
 */
@Database(entities = [Movie::class], version = 1)
abstract class MoviesDataBase : RoomDatabase() {

    companion object {
        @Volatile
        private var sInstance: MoviesDataBase? = null

        fun getsInstance(application: Application): MoviesDataBase {
            return sInstance ?: synchronized(this) {
                sInstance ?: buildDatabase(application).also { sInstance = it }
            }
        }

        private fun buildDatabase(context: Context): MoviesDataBase {
            return Room.databaseBuilder(context, MoviesDataBase::class.java, "222movies.db")
                .build()
        }
    }

    abstract fun moviesDao(): MoviesDao
}