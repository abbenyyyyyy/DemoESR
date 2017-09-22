package com.abben.mvp_sample.movies.contract;

import com.abben.mvp_sample.bean.Movie;
import com.abben.mvp_sample.movies.BasePresenter;
import com.abben.mvp_sample.movies.BaseView;

import java.util.ArrayList;

/**
 * Created by abben on 2017/5/9.
 */

public interface EuramericanMoviesContract {
    interface Presenter extends BasePresenter{
        /**获取欧美电影*/
        void subscribeEuramericanMovies();
    }

    interface View extends BaseView<Presenter> {
        /**展示欧美电影*/
        void showEuramericanMovies(ArrayList<Movie> movies);
    }
}
