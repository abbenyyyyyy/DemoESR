package com.abben.mvvmsamplejetpack.bean

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

/**
 *  @author abben
 *  on 2019/1/17
 */
@Entity(tableName = "movies")
data class Movie(
    @PrimaryKey
    var name: String = "",
    var type: String,
    var imageOfMovie: String,
    var summaryOfMovie: String,
    var printscreen: String,
    var baiduyun: String,
    var yunPassword: String,
    var imageOfMovieHeight: String,
    var imageOfMovieWidth: String
) : Serializable