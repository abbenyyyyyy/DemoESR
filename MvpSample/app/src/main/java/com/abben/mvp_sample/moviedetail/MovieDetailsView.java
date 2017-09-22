package com.abben.mvp_sample.moviedetail;

import com.abben.mvp_sample.bean.Movie;

/**
 * Created by abben on 2017/5/12.
 */

public interface MovieDetailsView {

    void setPaesenter(MovieDetailsPresenter paesenter);


    void showMovieDetails(Movie movie);

}
