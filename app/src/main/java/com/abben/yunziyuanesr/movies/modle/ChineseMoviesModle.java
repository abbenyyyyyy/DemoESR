package com.abben.yunziyuanesr.movies.modle;

import com.abben.yunziyuanesr.bean.Movie;
import com.abben.yunziyuanesr.movies.fragment.MoviesApi;
import com.abben.yunziyuanesr.network.RetrofitHelper;

import java.util.ArrayList;

import io.reactivex.Observable;

/**
 * Created by abben on 2017/5/9.
 */

public class ChineseMoviesModle {
    private MoviesApi moviesApi;

    public ChineseMoviesModle(){
        moviesApi = RetrofitHelper.getRetrofit().create(MoviesApi.class);
    }

    public Observable<ArrayList<Movie>> getChineseMovies() {
        return moviesApi.fetchMovies("ChineseMovies");
    }
}
