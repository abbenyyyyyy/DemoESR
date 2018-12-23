package com.abben.mvvmsample.ui.movies;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;

import com.abben.mvvmsample.base.BaseAndroidViewModel;
import com.abben.mvvmsample.bean.Movie;
import com.abben.mvvmsample.db.MoviesDataBase;
import com.abben.mvvmsample.util.AbsentLiveData;
import com.abben.mvvmsample.util.Objects;
import com.abben.mvvmsample.vo.Resource;
import com.abben.mvvmsample.vo.TypeMovies;

import java.util.List;

/**
 * Created by abben on 2017/10/20.
 */
public class MoviesViewModel extends BaseAndroidViewModel {

    private final MutableLiveData<String> moveType = new MutableLiveData<>();

    private LiveData<Resource<List<Movie>>> mObservableMovies;

    private MoviesRepository moviesRepository;

    public MoviesViewModel(@NonNull Application application) {
        super(application);
        MoviesDataBase moviesDataBase = MoviesDataBase.getsInstance(application);
        moviesRepository = new MoviesRepository(moviesDataBase);

        mObservableMovies = Transformations.switchMap(moveType, input -> {
            if (input == null) {
                return AbsentLiveData.create();
            } else {
                return moviesRepository.getMovies(input);
            }
        });
    }

    public void setMoveType(@TypeMovies.TypeMoviesAnnotation String moveType) {
        if (Objects.equals(this.moveType.getValue(), moveType)) {
            return;
        }
        this.moveType.setValue(moveType);
    }

    public void retry() {
        if (this.moveType.getValue() != null) {
            this.moveType.setValue(this.moveType.getValue());
        }
    }

    public LiveData<Resource<List<Movie>>> getmObservableMovies() {
        return mObservableMovies;
    }


    public LiveData<Boolean> getmRefreshing() {
        return moviesRepository.getRefreshing();
    }
}
