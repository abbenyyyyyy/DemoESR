package com.abben.mvvmsample;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.abben.mvvmsample.bean.Movie;
import com.abben.mvvmsample.db.MoviesDataBase;
import com.abben.mvvmsample.vo.Resource;
import com.abben.mvvmsample.vo.TypeMovies;

import java.util.List;

/**
 * Created by Shaolin on 2017/10/20.
 */

public class MoviesViewModel extends AndroidViewModel{

    private LiveData<Resource<List<Movie>>> mObservableMovies;
    private LiveData<Boolean> mRefreshing;

    private MoviesRepository moviesRepository;
    private final String type_movies;

    public MoviesViewModel(@NonNull Application application,String type_movies) {
        super(application);
        this.type_movies = type_movies;
        MoviesDataBase moviesDataBase = MoviesDataBase.getsInstance(application,type_movies);
        moviesRepository = new MoviesRepository(moviesDataBase);
        setmRefreshing(moviesRepository.getRefreshing());
        refreshMoviesData();
    }

    public void refreshMoviesData(){
        setmObservableMovies(moviesRepository.getMovies(type_movies));
    }

    public LiveData<Resource<List<Movie>>> getmObservableMovies() {
        return mObservableMovies;
    }

    private void setmObservableMovies(LiveData<Resource<List<Movie>>> movies){
        this.mObservableMovies = movies;
    }

    public LiveData<Boolean> getmRefreshing() {
        return mRefreshing;
    }

    private void setmRefreshing(LiveData<Boolean> mRefreshing) {
        this.mRefreshing = mRefreshing;
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory{
        @NonNull
        Application application;
        String type_movies;

        public Factory(@NonNull Application application,@TypeMovies.TypeMoviesAnnotation String type_movies) {
            this.application = application;
            this.type_movies = type_movies;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new MoviesViewModel(application,type_movies);
        }
    }
}
