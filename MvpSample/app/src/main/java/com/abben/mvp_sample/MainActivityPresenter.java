package com.abben.mvp_sample;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

import com.abben.mvp_sample.bean.UpdateBean;
import com.abben.mvp_sample.downloadutil.DownLoadHelper;
import com.abben.mvp_sample.downloadutil.DownLoadManager;
import com.abben.mvp_sample.downloadutil.ProgressListener;
import com.abben.mvp_sample.movies.MainActivityModle;
import com.abben.mvp_sample.util.FolderUtil;

import java.io.File;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by abben on 2017/5/16.
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
                        mMainActivityView.showTip(e.toString());
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
                mMainActivityView.showDialog(updateBean);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void startDownload(String downloadUrl){
        DownLoadHelper helper = DownLoadManager.getInstance().getHelperByTag("xx");
        if(helper != null){
            DownLoadManager.getInstance().cancelHelperByTag("xx");
            return;
        }
        String roodir = FolderUtil.getAppCacheDir();
        if(roodir == null){
            mMainActivityView.showTip("手机存储空间不足!");
        }else {
            final String appFilePath = roodir + File.separator + "yuanziyuanESR.apk";
            new DownLoadHelper.Builder()
                    .setPath(appFilePath)
                    .setTag("yuanziyuanESRApk")
                    .setUrl(downloadUrl)
                    .setDownLoadListener(new ProgressListener() {
                        @Override
                        public void onStart() {
                            mMainActivityView.startNotification();
                        }

                        @Override
                        public void update(long bytesRead, long contentLength, boolean done) {
                            int read = (int) (bytesRead * 100f / contentLength);
                            mMainActivityView.notification(read);
                        }

                        @Override
                        public void onCompleted() {
                            mMainActivityView.cancelNotification();
                            installApk(appFilePath);
                        }

                        @Override
                        public void onError(String err) {
                            mMainActivityView.showTip(err);
                            mMainActivityView.cancelNotification();
                        }
                    })
                    .create()
                    .execute();
        }
    }



    /**发出安装APK的意图给用户*/
    private void installApk(String appFilePath){
        File appFile = new File(appFilePath);
        // 创建URI
        Uri uri = Uri.fromFile(appFile);
        // 创建Intent意图
        Intent intent = new Intent(Intent.ACTION_VIEW);
        // 设置Uri和类型
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // 执行意图进行安装
        mMainActivityView.getContext().startActivity(intent);
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
        DownLoadManager.getInstance().unsubscribe();
    }

}
