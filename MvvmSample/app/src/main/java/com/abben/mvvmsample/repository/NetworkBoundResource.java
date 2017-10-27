package com.abben.mvvmsample.repository;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;

import com.abben.mvvmsample.network.ApiResponse;
import com.abben.mvvmsample.util.Objects;
import com.abben.mvvmsample.vo.Resource;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * 从SQLite数据库和网络获得资源数据
 * Created by abben on 2017/10/24.
 * <p>
 * 更多信息请参考<a href="https://developer.android.com/arch">Architecture
 * Guide</a>.
 *
 * @param <ResultType>  数据库以及APP所使用的资源数据的类型
 * @param <RequestType> 远程返回的资源数据的类型
 */
public abstract class NetworkBoundResource<ResultType, RequestType> {


    private final MediatorLiveData<Resource<ResultType>> result = new MediatorLiveData<>();

    @MainThread
    public NetworkBoundResource() {
        result.setValue((Resource<ResultType>) Resource.loading(null));
        final LiveData<ResultType> dbSource = loadFromDb();
        //Note: As of version 1.0, Room uses the list of tables accessed in the query to decide whether to update instances of LiveData.
        result.addSource(dbSource, new Observer<ResultType>() {
            @Override
            public void onChanged(@Nullable ResultType resultType) {
                result.removeSource(dbSource);
                if (shouldFetch(resultType)) {
                    fetchFromNetwork(dbSource);
                } else {
                    result.addSource(dbSource, new Observer<ResultType>() {
                        @Override
                        public void onChanged(@Nullable ResultType resultType) {
                            setValue(Resource.success(resultType));
                        }
                    });
                }
            }
        });
    }

    @MainThread
    private void setValue(Resource<ResultType> newValue) {
        if (!Objects.equals(result.getValue(), newValue)) {
            result.setValue(newValue);
        }
    }

    private void fetchFromNetwork(final LiveData<ResultType> dbSource) {
        final LiveData<ApiResponse<RequestType>> apiResponse = createCall();
        //我们将dbsource作为一种新数据来源，它将很快地发出它的最后一个数据
        result.addSource(dbSource, new Observer<ResultType>() {
            @Override
            public void onChanged(@Nullable ResultType resultType) {
                setValue(Resource.loading(resultType));
            }
        });
        result.addSource(apiResponse, new Observer<ApiResponse<RequestType>>() {
            @Override
            public void onChanged(@Nullable final ApiResponse<RequestType> requestTypeApiResponse) {
                result.removeSource(apiResponse);
                result.removeSource(dbSource);
                //检查数据加载状态
                if (requestTypeApiResponse.isSuccessful()) {
                    //保存结果然后重新初始化
                    Observable.just(processResponse(requestTypeApiResponse))
                            .flatMap(new Function<RequestType, ObservableSource<Boolean>>() {
                                @Override
                                public ObservableSource<Boolean> apply(@io.reactivex.annotations.NonNull RequestType requestType) throws Exception {
                                    saveCallResult(requestType);
                                    return Observable.just(true);
                                }
                            })
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<Boolean>() {
                                @Override
                                public void accept(Boolean aBoolean) throws Exception {
                                    //我们特别要求一个新的实时数据，
                                    //否则，我们将立即获得上次缓存的值，
                                    //可能不会从网络接收到最新结果。
                                    result.addSource(loadFromDb(), new Observer<ResultType>() {
                                        @Override
                                        public void onChanged(@Nullable ResultType resultType) {
                                            setValue(Resource.success(resultType));
                                        }
                                    });
                                }
                            });

                } else {
                    onFetchFailed();
                    result.addSource(dbSource, new Observer<ResultType>() {
                        @Override
                        public void onChanged(@Nullable ResultType resultType) {
                            setValue(Resource.error(requestTypeApiResponse.errorMessage, resultType));
                        }
                    });
                }
            }
        });
    }


    @WorkerThread
    protected RequestType processResponse(ApiResponse<RequestType> response) {
        return response.body;
    }


    /**
     * 调用它将API响应的结果保存到数据库中。
     *
     * @param item
     */
    @WorkerThread
    protected abstract void saveCallResult(@NonNull RequestType item);

    /**
     * 调用它来决定是否应该从网络获取数据。
     *
     * @param data
     * @return
     */
    @MainThread
    protected abstract boolean shouldFetch(@Nullable ResultType data);

    /**
     * 调用它从数据库获取缓存的数据。
     *
     * @return
     */
    @NonNull
    @MainThread
    protected abstract LiveData<ResultType> loadFromDb();

    /**
     * 调用它来创建API调用。
     *
     * @return
     */
    @NonNull
    @MainThread
    protected abstract LiveData<ApiResponse<RequestType>> createCall();

    /**
     * 获取失败时调用。子类可能需要重置组件。
     * 类似rate limiter(速率限制器)。
     */
    protected abstract void onFetchFailed();

    protected abstract void onFetchSuccess();

    public LiveData<Resource<ResultType>> asLiveData() {
        onFetchSuccess();
        return result;
    }
}
