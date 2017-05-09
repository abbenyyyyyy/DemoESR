package com.abben.yunziyuanesr.modle;

import com.abben.yunziyuanesr.bean.Movie;
import com.abben.yunziyuanesr.fragment.MoviesApi;
import com.abben.yunziyuanesr.network.RetrofitHelper;

import java.util.ArrayList;

import io.reactivex.Observable;

/**
 * Created by abben on 2017/5/9.
 */

public class JanpanAndKoreaMoviesModle {
    private MoviesApi moviesApi;

    public JanpanAndKoreaMoviesModle(){
        moviesApi = RetrofitHelper.getRetrofit().create(MoviesApi.class);
    }

    public Observable<ArrayList<Movie>> getJanpanAndKoreaMovies() {
        return moviesApi.fetchMovies("JanpanAndKoreaMovies");
    }
}
