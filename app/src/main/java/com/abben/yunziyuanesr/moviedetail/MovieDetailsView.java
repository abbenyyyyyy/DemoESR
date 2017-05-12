package com.abben.yunziyuanesr.moviedetail;

import com.abben.yunziyuanesr.bean.Movie;

/**
 * Created by abben on 2017/5/12.
 */

public interface MovieDetailsView {

    void setPaesenter(MovieDetailsPresenter paesenter);


    void showMovieDetails(Movie movie);

}
