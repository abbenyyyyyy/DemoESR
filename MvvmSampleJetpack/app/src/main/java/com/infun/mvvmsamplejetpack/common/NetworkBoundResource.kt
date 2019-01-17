package com.infun.mvvmsamplejetpack.common

import androidx.lifecycle.MediatorLiveData

/**
 *  @author abben
 *  on 2019/1/17
 */
abstract class NetworkBoundResource<ResultType, RequestType> {
    private final val result: MediatorLiveData<Resource<ResultType>> = MediatorLiveData<>()

    @MainThread
    public NetworkBoundResource() {
        result.setValue((Resource<ResultType>) Resource.loading(null));
        final LiveData<ResultType> dbSource = loadFromDb();
        //Note: As of version 1.0, Room uses the list of tables accessed in the query to decide whether to update instances of LiveData.
        result.addSource(dbSource, resultType -> {
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
        });
    }
}