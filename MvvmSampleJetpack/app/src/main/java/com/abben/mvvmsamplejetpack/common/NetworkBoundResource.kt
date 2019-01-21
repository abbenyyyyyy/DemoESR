package com.abben.mvvmsamplejetpack.common

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.abben.mvvmsamplejetpack.network.ApiEmptyResponse
import com.abben.mvvmsamplejetpack.network.ApiErrorResponse
import com.abben.mvvmsamplejetpack.network.ApiResponse
import com.abben.mvvmsamplejetpack.network.ApiSuccessResponse
import com.abben.mvvmsamplejetpack.vo.Resource
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 *  @author abben
 *  on 2019/1/17
 */
abstract class NetworkBoundResource<ResultType, RequestType>
@MainThread constructor() {

    private val result: MediatorLiveData<Resource<ResultType>> = MediatorLiveData()

    init {
        result.value = Resource.loading(null)
        val dbSource: LiveData<ResultType> = loadFromDb()
        result.addSource(dbSource) {
            result.removeSource(dbSource)
            if (shouldFetch(it)) {
                fetchFromNetwork(dbSource)
            } else {
                result.addSource(dbSource) { newData ->
                    setValue(Resource.success(newData))
                }
            }
        }
    }

    @MainThread
    private fun setValue(newValue: Resource<ResultType>) {
        if (result.value != newValue) {
            result.value = newValue
        }
    }

    private fun fetchFromNetwork(dbSource: LiveData<ResultType>) {
        val apiResponse = createCall()
        //我们将dbsource作为一种新数据来源，它将很快地发出它的最后一个数据
        result.addSource(dbSource) {
            setValue(Resource.loading(it))
        }
        result.addSource(apiResponse) { response ->
            result.removeSource(apiResponse)
            result.removeSource(dbSource)
            //检查数据加载状态
            when (response) {
                is ApiErrorResponse -> {
                    onFetchFailed()
                    result.addSource(dbSource) {
                        setValue(Resource.error(response.errorMessage, it))
                    }
                }
                is ApiEmptyResponse -> {
                    result.addSource(loadFromDb()) {
                        setValue(Resource.success(it))
                    }
                }
                is ApiSuccessResponse -> {
                    Observable.just(processResponse(response))
                        .flatMap {
                            saveCallResult(it)
                            Observable.just(true)
                        }
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe {
                            //我们特别要求一个新的实时数据，
                            //否则，我们将立即获得上次缓存的值，
                            //可能不会从网络接收到最新结果。
                            result.addSource(loadFromDb()) { newData ->
                                setValue(Resource.success(newData))
                            }
                        }
                }
            }
        }
    }

    @WorkerThread
    protected fun processResponse(response: ApiSuccessResponse<RequestType>): RequestType {
        return response.body
    }

    /**
     * 调用它将API响应的结果保存到数据库中。
     *
     * @param item 从网络请求获得的数据
     */
    @WorkerThread
    protected abstract fun saveCallResult(item: RequestType)

    /**
     * 调用它来决定是否应该从网络获取数据。
     *
     * @param data 旧数据
     * @return 真就从网络获取数据，假就从数据库获取数据
     */
    @MainThread
    protected abstract fun shouldFetch(data: ResultType): Boolean

    /**
     * 调用它从数据库获取缓存的数据。
     *
     * @return 从数据库获得的数据
     */
    @MainThread
    protected abstract fun loadFromDb(): LiveData<ResultType>

    /**
     * 调用它来创建API调用。
     *
     * @return 从网络接口获得的数据
     */
    @MainThread
    protected abstract fun createCall(): LiveData<ApiResponse<RequestType>>

    /**
     * 获取失败时调用。子类可能需要重置组件。
     * 类似rate limiter(速率限制器)。
     */
    protected abstract fun onFetchFailed()

    protected abstract fun onFetchSuccess()

    fun asLiveData(): LiveData<Resource<ResultType>> {
        onFetchSuccess()
        return result
    }
}