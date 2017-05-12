package com.abben.yunziyuanesr.movies.presenter;

import com.abben.yunziyuanesr.movies.BasePresenter;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Administrator on 2017/5/9.
 */

public class PresenterAdapter implements BasePresenter{
    CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    @Override
    public void clearCompositeDisposable() {
        mCompositeDisposable.clear();
    }

    void addCompositeDisposable(Disposable disposable){
        mCompositeDisposable.add(disposable);
    }

}
