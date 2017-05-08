package com.abben.yunziyuanesr.modle;

import com.abben.yunziyuanesr.bean.Movie;

import java.util.ArrayList;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2017/5/8.
 */

public interface IMovieModle {

    Observable<ArrayList<Movie>> getAllMovies();

    Observable<ArrayList<Movie>> getEuramericanMovies();

    Observable<ArrayList<Movie>> getJanpanAndKoreaMovies();

    Observable<ArrayList<Movie>> getChineseMovies();

}
