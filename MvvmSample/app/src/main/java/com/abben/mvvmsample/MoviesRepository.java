package com.abben.mvvmsample;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.abben.mvvmsample.bean.Movie;
import com.abben.mvvmsample.db.MoviesDataBase;
import com.abben.mvvmsample.network.ApiManager;
import com.abben.mvvmsample.network.ApiResponse;
import com.abben.mvvmsample.repository.NetworkBoundResource;
import com.abben.mvvmsample.util.RateLimiter;
import com.abben.mvvmsample.vo.Resource;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Shaolin on 2017/10/20.
 */

public class MoviesRepository {

    private final MutableLiveData<Boolean> mMoviesRefreshing;

    private RateLimiter<String> moviesListRateLimiter = new RateLimiter<>(10, TimeUnit.MINUTES);
    private MoviesDataBase moviesDataBase;

    public MoviesRepository(MoviesDataBase moviesDataBase){
        mMoviesRefreshing = new MutableLiveData<>();
        this.moviesDataBase = moviesDataBase;
    }


    public LiveData<Resource<List<Movie>>> getMovies(final String type) {
        mMoviesRefreshing.setValue(true);
        return new NetworkBoundResource<List<Movie>, List<Movie>>() {

            @Override
            protected void saveCallResult(@NonNull List<Movie> item) {
                Log.i("testLog", "saveCallResult: "+Thread.currentThread().getName());
                moviesDataBase.beginTransaction();
                try{
                    moviesDataBase.moviesDao().insertMovies(item);
                    moviesDataBase.setTransactionSuccessful();
                }finally {
                    moviesDataBase.endTransaction();
                }
            }

            @Override
            protected boolean shouldFetch(@Nullable List<Movie> data) {
                boolean fetchFromNet = data == null || data.isEmpty() || moviesListRateLimiter.shouldFetch(type);
                Log.i("testLog", "shouldFetch: "+fetchFromNet+"  :"+Thread.currentThread().getName());
                return fetchFromNet;
            }

            @NonNull
            @Override
            protected LiveData<List<Movie>> loadFromDb() {
                Log.i("testLog", "loadFromDb: "+Thread.currentThread().getName());
                return moviesDataBase.moviesDao().loadMovies();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<List<Movie>>> createCall() {
                Log.i("testLog", "createCall: "+Thread.currentThread().getName());
                return ApiManager.getMoviesApi().fetchMovies(type);
            }

            @Override
            protected void onFetchFailed() {
                Log.i("testLog", "onFetchFailed: "+Thread.currentThread().getName());
                moviesListRateLimiter.reset(type);
            }

            @Override
            protected void onFetchSuccess() {
                Log.i("testLog", "onFetchSuccess: "+Thread.currentThread().getName());
                mMoviesRefreshing.setValue(false);
            }
        }.asLiveData();
    }

    public LiveData<Boolean> getRefreshing() {
        return mMoviesRefreshing;
    }
}
