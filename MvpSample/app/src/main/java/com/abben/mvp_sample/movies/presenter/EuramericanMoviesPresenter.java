package com.abben.mvp_sample.movies.presenter;

import com.abben.mvp_sample.bean.Movie;
import com.abben.mvp_sample.movies.contract.EuramericanMoviesContract;
import com.abben.mvp_sample.movies.modle.EuramericanMoviesModle;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/5/9.
 */

public class EuramericanMoviesPresenter extends PresenterAdapter implements EuramericanMoviesContract.Presenter{
    private EuramericanMoviesContract.View mEuramericanMoviesView;
    private EuramericanMoviesModle mEuramericanMoviesModle;

    public EuramericanMoviesPresenter(EuramericanMoviesContract.View view){
        mEuramericanMoviesView = view;
        mEuramericanMoviesModle = new EuramericanMoviesModle();
        mEuramericanMoviesView.setPresenter(this);
    }

    @Override
    public void subscribeEuramericanMovies() {
        mEuramericanMoviesModle.getEuramericanMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<Movie>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull ArrayList<Movie> movies) {
                        mEuramericanMoviesView.showEuramericanMovies(movies);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mEuramericanMoviesView.showTip(e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
