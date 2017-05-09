package com.abben.yunziyuanesr.presenter;

import com.abben.yunziyuanesr.bean.Movie;
import com.abben.yunziyuanesr.contract.JanpanAndKoreaMoviesContract;
import com.abben.yunziyuanesr.modle.JanpanAndKoreaMoviesModle;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/5/9.
 */

public class JanpanAndKoreaMoviesPresenter extends PresenterAdapter implements JanpanAndKoreaMoviesContract.Presenter{
    private JanpanAndKoreaMoviesContract.View mJanpanAndKoreaMoviesView;
    private JanpanAndKoreaMoviesModle mJanpanAndKoreaMoviesModle;

    public JanpanAndKoreaMoviesPresenter(JanpanAndKoreaMoviesContract.View view){
        mJanpanAndKoreaMoviesView = view;
        mJanpanAndKoreaMoviesModle = new JanpanAndKoreaMoviesModle();
        mJanpanAndKoreaMoviesView.setPresenter(this);
    }

    @Override
    public void subscribeJanpanAndKoreaMovies() {
        mJanpanAndKoreaMoviesModle.getJanpanAndKoreaMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<Movie>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull ArrayList<Movie> movies) {
                        mJanpanAndKoreaMoviesView.showJanpanAndKoreaMovies(movies);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mJanpanAndKoreaMoviesView.showTip(e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
