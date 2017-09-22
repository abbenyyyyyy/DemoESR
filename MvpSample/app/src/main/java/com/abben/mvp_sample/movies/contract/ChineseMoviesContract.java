package com.abben.mvp_sample.movies.contract;

import com.abben.mvp_sample.bean.Movie;
import com.abben.mvp_sample.movies.BasePresenter;
import com.abben.mvp_sample.movies.BaseView;

import java.util.ArrayList;

/**
 * Created by abben on 2017/5/9.
 */

public interface ChineseMoviesContract {
    interface Presenter extends BasePresenter{
        /**获取国产电影*/
        void subscribeChineseMovies();
    }

    interface View extends BaseView<Presenter>{
        /**展示国产电影*/
        void showChineseMovies(ArrayList<Movie> movies);
    }
}
