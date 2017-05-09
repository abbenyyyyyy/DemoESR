package com.abben.yunziyuanesr.presenter;

import com.abben.yunziyuanesr.BasePresenter;

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
