package com.abben.mvp_sample.movies.contract;

import com.abben.mvp_sample.bean.Movie;
import com.abben.mvp_sample.movies.BasePresenter;
import com.abben.mvp_sample.movies.BaseView;

import java.util.ArrayList;

/**
 * Created by abben on 2017/5/9.
 */

public interface JanpanAndKoreaMoviesContract {
    interface Presenter extends BasePresenter{
        /**获取日韩电影*/
        void subscribeJanpanAndKoreaMovies();
    }

    interface View extends BaseView<Presenter>{
        /**展示日韩电影*/
        void showJanpanAndKoreaMovies(ArrayList<Movie> movies);
    }
}
