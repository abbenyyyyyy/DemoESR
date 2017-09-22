package com.abben.mvp_sample.movies.modle;

import com.abben.mvp_sample.bean.Movie;
import com.abben.mvp_sample.movies.fragment.MoviesApi;
import com.abben.mvp_sample.network.RetrofitHelper;

import java.util.ArrayList;

import io.reactivex.Observable;

/**
 * Created by abben on 2017/5/8.
 */

public class AllMoviesModle {

    private MoviesApi moviesApi;

    public AllMoviesModle(){
        moviesApi = RetrofitHelper.getRetrofit().create(MoviesApi.class);
    }


    public Observable<ArrayList<Movie>> getAllMovies() {
        return moviesApi.fetchMovies("Movies");
    }

}
