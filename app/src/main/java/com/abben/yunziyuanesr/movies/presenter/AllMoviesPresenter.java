package com.abben.yunziyuanesr.movies.presenter;

import com.abben.yunziyuanesr.bean.Movie;
import com.abben.yunziyuanesr.movies.contract.AllMoviesContract;
import com.abben.yunziyuanesr.movies.modle.AllMoviesModle;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/5/9.
 */

public class AllMoviesPresenter extends PresenterAdapter implements AllMoviesContract.Presenter{
    private AllMoviesContract.View mAllMoviesView;
    private AllMoviesModle allMoviesModle;

    public AllMoviesPresenter(AllMoviesContract.View view){
        mAllMoviesView = view;
        allMoviesModle = new AllMoviesModle();
        mAllMoviesView.setPresenter(this);
    }

    @Override
    public void subscribeAllMovies() {
        allMoviesModle.getAllMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<Movie>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        addCompositeDisposable(d);
                    }

                    @Override
                    public void onNext(@NonNull ArrayList<Movie> movies) {
                        mAllMoviesView.showAllMovies(movies);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mAllMoviesView.showTip(e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
}
