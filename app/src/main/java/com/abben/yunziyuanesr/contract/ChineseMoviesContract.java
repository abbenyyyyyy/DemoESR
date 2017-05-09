package com.abben.yunziyuanesr.contract;

import com.abben.yunziyuanesr.BasePresenter;
import com.abben.yunziyuanesr.BaseView;
import com.abben.yunziyuanesr.bean.Movie;

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
