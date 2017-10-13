package com.abben.mvp_sample.downloadutil;

import com.abben.mvp_sample.MainActivityApi;
import com.abben.mvp_sample.network.RetrofitHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by abben on 2017/5/17.
 */

public class DownLoadHelper {
    public static final int CONNECTION_TIMEOUT = 10;

    public static final int READ_DOWN_TIMEOUT = 20;

    public String mApkDownloadUrl;

    public Set<ProgressListener> mDownloadListeners;

    private DownLoadManager mManager;

    public ProgressListener mListener;

    private String mUrl;

    private String mPath;

    private int mConnectionTimeout;

    private int mReadTimeout;

    public Object mTag;

    private Retrofit mAdapter;

    private MainActivityApi mUploadService;

    private boolean isCancel;

    private DownLoadHelper() {
        mDownloadListeners = new HashSet<>();
        mManager = DownLoadManager.getInstance();
    }

    public void setCancel(boolean cancel) {
        isCancel = cancel;
    }

    public void setmUrl(String url) {
        if (url != null) {
            mApkDownloadUrl = url;
        }
    }

    /**
     * 默认分配tag
     */
    public void setTag(Object tag) {
        if (tag != null) {
            mTag = tag;
        } else {
            mTag = UUID.randomUUID().toString();
        }
    }

    public void registerListener(ProgressListener listener) {
        mDownloadListeners.add(listener);
    }

    public void unRegisterListener(ProgressListener listener) {
        mDownloadListeners.remove(listener);
    }

    private OkHttpClient getDefaultOkHttp() {
        return getBuilder().build();
    }

    private OkHttpClient.Builder getBuilder() {
        SigningInterceptor signingInterceptor = new SigningInterceptor();
        signingInterceptor.setProgressListener(mListener);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(signingInterceptor);
        builder.connectTimeout(mConnectionTimeout, TimeUnit.SECONDS);
        builder.readTimeout(mReadTimeout, TimeUnit.SECONDS);
        return builder;
    }


    public static class Builder {
        private ProgressListener mListener;
        private String mUrl;
        private String mPath;
        private int mConnectionTimeout;
        private int mReadTimeout;
        private Object mTag;

        public Builder() {
            this.mConnectionTimeout = CONNECTION_TIMEOUT;
            this.mReadTimeout = READ_DOWN_TIMEOUT;
        }

        /**
         * 设置任务标记
         */
        public Builder setTag(Object tag) {
            this.mTag = tag;
            return this;
        }

        /**
         * 设置下载文件的URL
         */
        public Builder setUrl(String url) {
            this.mUrl = url;
            return this;
        }

        /**
         * 设置HTTP请求连接超时时间，默认10s
         */
        public Builder setConnectionTimeout(int timeout) {
            this.mConnectionTimeout = timeout;
            return this;
        }

        /**
         * 设置HTTP请求数据读取超时时间，默认20s
         */
        public Builder setReadTimeout(int timeout) {
            this.mReadTimeout = timeout;
            return this;
        }

        /**
         * 设置下载文件保存地址，使用绝对路径
         */
        public Builder setPath(String path) {
            this.mPath = path;
            return this;
        }

        /**
         * 设置下载监听
         */
        public Builder setDownLoadListener(ProgressListener listener) {
            this.mListener = listener;
            return this;
        }

        /**
         * 创建一个本地任务
         */
        public DownLoadHelper create() {
            final DownLoadHelper helper = new DownLoadHelper();
            helper.mConnectionTimeout = mConnectionTimeout;
            helper.mReadTimeout = mReadTimeout;
            helper.mPath = mPath;
            helper.setTag(mTag);
            helper.setmUrl(mUrl);
            if (mListener != null) {
                helper.mDownloadListeners.add(mListener);
            }
            return helper;
        }

    }

    public void execute() {
        mManager.addHelper(this);

        mAdapter = new Retrofit.Builder()
                .baseUrl(RetrofitHelper.getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(getDefaultOkHttp())
                .build();

        mUploadService = mAdapter.create(MainActivityApi.class);

        mUploadService.download(mApkDownloadUrl)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ResponseBody responseBody) {
                        OutputStream output = null;
                        InputStream input = null;
                        File ret;
                        try {
                            input = responseBody.byteStream();
                            ret = new File(mPath);
                            output = new FileOutputStream(ret);
                            byte[] buffer = new byte[8192];
                            int len = -1;
                            while ((len = input.read(buffer)) != -1) {
                                if (isCancel) {
                                    ret.delete();
                                    return;
                                }
                                output.write(buffer, 0, len);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                if (input != null) {
                                    input.close();
                                }
                                if (output != null) {
                                    output.close();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        if (mListener != null) {
                            mListener.onError(e.getMessage());
                        }
                    }

                    @Override
                    public void onComplete() {
                        if (isCancel) {
                            mListener.onError("cancel");
                        } else if (mListener != null) {
                            mListener.onCompleted();
                        }
                    }
                });

        if (mListener != null) {
            mListener.onStart();
        }
    }
}
