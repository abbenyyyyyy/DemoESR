package com.abben.mvvmsamplejetpack.ui.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.abben.mvvmsamplejetpack.bean.Movie
import com.abben.mvvmsamplejetpack.common.NetworkBoundResource
import com.abben.mvvmsamplejetpack.db.MoviesDataBase
import com.abben.mvvmsamplejetpack.network.ApiManager
import com.abben.mvvmsamplejetpack.network.ApiResponse
import com.abben.mvvmsamplejetpack.util.RateLimiter
import com.abben.mvvmsamplejetpack.vo.*
import java.util.concurrent.TimeUnit

/**
 *  @author abben
 *  on 2019/1/21
 */
class MoviesRepository(var moviesDataBase: MoviesDataBase) {
    val mMoviesRefreshing: MutableLiveData<Boolean> = MutableLiveData()

    private var moviesListRateLimiter: RateLimiter<String> = RateLimiter(5, TimeUnit.MINUTES)

    fun getMovies(type: String): LiveData<Resource<List<Movie>>> {
        mMoviesRefreshing.value = true
        return object : NetworkBoundResource<List<Movie>, List<Movie>>() {
            override fun saveCallResult(item: List<Movie>) {
                moviesDataBase.beginTransaction()
                try {
                    moviesDataBase.moviesDao().insertMovies(item)
                    moviesDataBase.setTransactionSuccessful()
                } finally {
                    moviesDataBase.endTransaction()
                }
            }

            override fun shouldFetch(data: List<Movie>): Boolean {
                return (data.isEmpty() || moviesListRateLimiter.shouldFetch(type))
            }

            override fun loadFromDb(): LiveData<List<Movie>> {
                return if (type === TYPE_ALL_MOVIES_ZH) {
                    moviesDataBase.moviesDao().loadAllMovies()
                } else {
                    moviesDataBase.moviesDao().loadMovies(type)
                }
            }

            override fun createCall(): LiveData<ApiResponse<List<Movie>>> {
                val apiType = when (type) {
                    TYPE_ALL_MOVIES_ZH -> TypeMovies.API_TYPE_ALL_MOVIES
                    TYPE_CHINESE_MOVIES_ZH -> TypeMovies.API_TYPE_CHINESE_MOVIES
                    TYPE_EURAMERICAN_MOVIES_ZH -> TypeMovies.API_TYPE_EURAMERICAN_MOVIES
                    TYPE_JANPAN_AND_KOREA_MOVIES_ZH -> TypeMovies.API_TYPE_JANPAN_AND_KOREA_MOVIES
                    else -> TypeMovies.API_TYPE_ALL_MOVIES
                }
                return ApiManager.getMoviesApi().fetchMovies(apiType)
            }

            override fun onFetchFailed() {
                mMoviesRefreshing.value = false
                moviesListRateLimiter.reset(type)
            }

            override fun onFetchSuccess() {
                mMoviesRefreshing.value = false
            }
        }.asLiveData()
    }

}