package com.abben.yunziyuanesr.contract;

import com.abben.yunziyuanesr.BasePresenter;
import com.abben.yunziyuanesr.BaseView;
import com.abben.yunziyuanesr.bean.Movie;

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
