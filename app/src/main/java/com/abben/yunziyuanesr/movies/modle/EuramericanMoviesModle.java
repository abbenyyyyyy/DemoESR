package com.abben.yunziyuanesr.movies.modle;

import com.abben.yunziyuanesr.bean.Movie;
import com.abben.yunziyuanesr.movies.fragment.MoviesApi;
import com.abben.yunziyuanesr.network.RetrofitHelper;

import java.util.ArrayList;

import io.reactivex.Observable;

/**
 * Created by abben on 2017/5/9.
 */

public class EuramericanMoviesModle {
    private MoviesApi moviesApi;

    public EuramericanMoviesModle(){
        moviesApi = RetrofitHelper.getRetrofit().create(MoviesApi.class);
    }

    public Observable<ArrayList<Movie>> getEuramericanMovies() {
        return moviesApi.fetchMovies("EuramericanMovies");
    }

}
