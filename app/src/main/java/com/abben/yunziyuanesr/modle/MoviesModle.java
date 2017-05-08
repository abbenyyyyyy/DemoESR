package com.abben.yunziyuanesr.modle;

import com.abben.yunziyuanesr.bean.Movie;
import com.abben.yunziyuanesr.fragment.MoviesApi;
import com.abben.yunziyuanesr.network.RetrofitHelper;

import java.util.ArrayList;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2017/5/8.
 */

public class MoviesModle implements IMovieModle{

    private MoviesApi moviesApi;

    public MoviesModle(){
        moviesApi = RetrofitHelper.getRetrofit().create(MoviesApi.class);
    }

    @Override
    public Observable<ArrayList<Movie>> getAllMovies() {
        return moviesApi.fetchAllMovies("Movies");
    }

    @Override
    public Observable<ArrayList<Movie>> getEuramericanMovies() {
        return null;
    }

    @Override
    public Observable<ArrayList<Movie>> getJanpanAndKoreaMovies() {
        return null;
    }

    @Override
    public Observable<ArrayList<Movie>> getChineseMovies() {
        return null;
    }
}
