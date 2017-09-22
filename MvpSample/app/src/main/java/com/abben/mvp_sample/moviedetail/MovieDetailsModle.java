package com.abben.mvp_sample.moviedetail;

import com.abben.mvp_sample.bean.Movie;

/**
 * Created by abben on 2017/5/12.
 */

public class MovieDetailsModle {

    private Movie mMovie;

    public MovieDetailsModle(Movie movie){
        mMovie = movie;
    }

    public void setmMovie(Movie movie){
        mMovie = movie;
    }

    public Movie fetchmMovie(){
        return mMovie;
    }
}
