package com.abben.yunziyuanesr.movies.presenter;

import com.abben.yunziyuanesr.bean.Movie;
import com.abben.yunziyuanesr.movies.contract.ChineseMoviesContract;
import com.abben.yunziyuanesr.movies.modle.ChineseMoviesModle;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/5/9.
 */

public class ChineseMoviesPresenter extends PresenterAdapter implements ChineseMoviesContract.Presenter{
    private ChineseMoviesContract.View mChineseMoviesView;
    private ChineseMoviesModle mChineseMoviesModle;

    public ChineseMoviesPresenter(ChineseMoviesContract.View view){
        mChineseMoviesView = view;
        mChineseMoviesModle = new ChineseMoviesModle();
        mChineseMoviesView.setPresenter(this);
    }

    @Override
    public void subscribeChineseMovies() {
        mChineseMoviesModle.getChineseMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<Movie>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull ArrayList<Movie> movies) {
                        mChineseMoviesView.showChineseMovies(movies);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mChineseMoviesView.showTip(e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
