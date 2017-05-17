package com.abben.yunziyuanesr;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.abben.yunziyuanesr.bean.UpdateBean;
import com.abben.yunziyuanesr.movies.MainActivityModle;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Shaolin on 2017/5/16.
 */

public class MainActivityPresenter {
    private MainActivityView mMainActivityView;
    private MainActivityModle mMainActivityModle;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    public MainActivityPresenter(MainActivityView view){
        mMainActivityView = view;
        mMainActivityView.setPaesenter(this);
        mMainActivityModle = new MainActivityModle();
    }

    public void checkUpdate(int maxRetries, int retryDelaySeconds) {

        final String firImUpdateUrl = "http://api.fir.im/apps/latest/591563c5959d69373400003f?api_token=f5c0e4f1f9f642e9a5ab657903825660";
        mMainActivityModle.fetchUpdateBeanFromUrl(firImUpdateUrl)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(maxRetries, retryDelaySeconds))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UpdateBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull UpdateBean updateBean) {
                        compareLocalAndServer(updateBean);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 比较本地应用的版本号和服务器的版本号，是否需要更新
     */
    private boolean compareLocalAndServer(UpdateBean updateBean){
        try {
            int compareResult = getLocalAPPVersion(mMainActivityView.getContext()).compareTo(updateBean.getVersionShort());
            if(compareResult < 0){

            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取当前应用的版本号
     */
    private String getLocalAPPVersion(Context context) throws PackageManager.NameNotFoundException {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        return packageInfo.versionName;
    }

    public void clearCompositeDisposable(){
        mCompositeDisposable.clear();
    }

}
