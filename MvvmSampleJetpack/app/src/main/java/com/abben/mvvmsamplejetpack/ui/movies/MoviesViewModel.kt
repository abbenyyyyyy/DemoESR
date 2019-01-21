package com.abben.mvvmsamplejetpack.ui.movies

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.abben.mvvmsamplejetpack.base.BaseAndroidViewModel
import com.abben.mvvmsamplejetpack.bean.Movie
import com.abben.mvvmsamplejetpack.db.MoviesDataBase
import com.abben.mvvmsamplejetpack.vo.Resource

/**
 *  @author abben
 *  on 2019/1/21
 */
class MoviesViewModel(baseApplication: Application) : BaseAndroidViewModel(baseApplication) {

    val moveType: MutableLiveData<String> = MutableLiveData()

    var mObservableMovies: LiveData<Resource<List<Movie>>>? = null

    private var moviesRepository: MoviesRepository

    init {
        val moviesDataBase = MoviesDataBase.getsInstance(baseApplication)
        moviesRepository = MoviesRepository(moviesDataBase)
        mObservableMovies = Transformations.switchMap(moveType) {
            moviesRepository.getMovies(it)
        }
    }

    fun retry() {
        moveType.value?.let {
            moveType.value = moveType.value
        }
    }

    fun getmRefreshing(): LiveData<Boolean> {
        return moviesRepository.mMoviesRefreshing
    }
}