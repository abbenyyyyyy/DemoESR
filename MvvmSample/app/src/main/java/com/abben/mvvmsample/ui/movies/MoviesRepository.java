package com.abben.mvvmsample.ui.movies;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.abben.mvvmsample.bean.Movie;
import com.abben.mvvmsample.common.NetworkBoundResource;
import com.abben.mvvmsample.db.MoviesDataBase;
import com.abben.mvvmsample.network.ApiManager;
import com.abben.mvvmsample.network.ApiResponse;
import com.abben.mvvmsample.util.RateLimiter;
import com.abben.mvvmsample.vo.Resource;
import com.abben.mvvmsample.vo.TypeMovies;

import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

/**
 * Created by abben on 2017/11/20.
 */
public class MoviesRepository {

    private final MutableLiveData<Boolean> mMoviesRefreshing;

    private RateLimiter<String> moviesListRateLimiter = new RateLimiter<>(5, TimeUnit.MINUTES);
    private MoviesDataBase moviesDataBase;

    public MoviesRepository(MoviesDataBase moviesDataBase) {
        mMoviesRefreshing = new MutableLiveData<>();
        this.moviesDataBase = moviesDataBase;
    }

    public LiveData<Resource<List<Movie>>> getMovies(final String type) {
        mMoviesRefreshing.setValue(true);
        return new NetworkBoundResource<List<Movie>, List<Movie>>() {

            @Override
            protected void saveCallResult(@NonNull List<Movie> item) {
                moviesDataBase.beginTransaction();
                try {
                    moviesDataBase.moviesDao().insertMovies(item);
                    moviesDataBase.setTransactionSuccessful();
                } finally {
                    moviesDataBase.endTransaction();
                }
            }

            @Override
            protected boolean shouldFetch(@Nullable List<Movie> data) {
                return (data == null || data.isEmpty() || moviesListRateLimiter.shouldFetch(type));
            }

            @NonNull
            @Override
            protected LiveData<List<Movie>> loadFromDb() {
                if (type.equals(TypeMovies.TYPE_ALL_MOVIES_ZH)) {
                    return moviesDataBase.moviesDao().loadAllMovies();
                } else {
                    return moviesDataBase.moviesDao().loadMovies(type);
                }
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<List<Movie>>> createCall() {
                String apiType;
                switch (type) {
                    case TypeMovies.TYPE_ALL_MOVIES_ZH:
                        apiType = TypeMovies.API_TYPE_ALL_MOVIES;
                        break;
                    case TypeMovies.TYPE_CHINESE_MOVIES_ZH:
                        apiType = TypeMovies.API_TYPE_CHINESE_MOVIES;
                        break;
                    case TypeMovies.TYPE_EURAMERICAN_MOVIES_ZH:
                        apiType = TypeMovies.API_TYPE_EURAMERICAN_MOVIES;
                        break;
                    case TypeMovies.TYPE_JANPAN_AND_KOREA_MOVIES_ZH:
                        apiType = TypeMovies.API_TYPE_JANPAN_AND_KOREA_MOVIES;
                        break;
                    default:
                        apiType = TypeMovies.API_TYPE_ALL_MOVIES;
                        break;
                }
                return ApiManager.getMoviesApi().fetchMovies(apiType);
            }

            @Override
            protected void onFetchFailed() {
                mMoviesRefreshing.setValue(false);
                moviesListRateLimiter.reset(type);
            }

            @Override
            protected void onFetchSuccess() {
                mMoviesRefreshing.setValue(false);
            }
        }.asLiveData();
    }

    public MutableLiveData<Boolean> getRefreshing() {
        return mMoviesRefreshing;
    }
}
