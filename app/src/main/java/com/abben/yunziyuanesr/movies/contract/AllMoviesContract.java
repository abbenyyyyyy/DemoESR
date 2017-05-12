package com.abben.yunziyuanesr.movies.contract;

import com.abben.yunziyuanesr.movies.BasePresenter;
import com.abben.yunziyuanesr.movies.BaseView;
import com.abben.yunziyuanesr.bean.Movie;

import java.util.ArrayList;

/**
 * Created by abben on 2017/5/8.
 */

public interface AllMoviesContract {

    interface Presenter extends BasePresenter{
        /**获取所有的电影*/
        void subscribeAllMovies();
    }

    interface View extends BaseView<Presenter>{

        /**展示所有电影*/
        void showAllMovies(ArrayList<Movie> allMovies);
    }
}
