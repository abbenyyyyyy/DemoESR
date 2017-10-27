package com.abben.mvvmsample.network;

import android.arch.lifecycle.LiveData;

import com.abben.mvvmsample.bean.Movie;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by abben on 2017/5/8.
 */

public interface MoviesApi {

    @GET("{type}.json")
    LiveData<ApiResponse<List<Movie>>> fetchMovies(@Path("type") String type);

}
