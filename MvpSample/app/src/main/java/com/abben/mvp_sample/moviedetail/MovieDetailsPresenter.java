package com.abben.mvp_sample.moviedetail;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.abben.mvp_sample.bean.Movie;

/**
 * Created by abben on 2017/5/12.
 */

public class MovieDetailsPresenter {

    private MovieDetailsView mMovieDetailsView;
    private MovieDetailsModle mMovieDetailsModle;

    public MovieDetailsPresenter(MovieDetailsView movieDetailsView,Movie movie){
        mMovieDetailsView = movieDetailsView;
        mMovieDetailsView.setPaesenter(this);
        mMovieDetailsModle = new MovieDetailsModle(movie);
    }

    public void startShow(){
        mMovieDetailsView.showMovieDetails(mMovieDetailsModle.fetchmMovie());
    }

    public void linkToBaiduPan(Context context){
        Intent intent= new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(mMovieDetailsModle.fetchmMovie().getBaiduyun());
        intent.setData(content_url);
        context.startActivity(intent);
    }
}
