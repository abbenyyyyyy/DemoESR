package com.abben.mvvmsamplejetpack.network

import androidx.lifecycle.LiveData
import com.abben.mvvmsamplejetpack.bean.Movie
import retrofit2.http.GET
import retrofit2.http.Path

/**
 *  @author abben
 *  on 2019/1/18
 */
interface MoviesApi {
    @GET("{type}.json")
    fun fetchMovies(@Path("type") type: String): LiveData<ApiResponse<List<Movie>>>
}