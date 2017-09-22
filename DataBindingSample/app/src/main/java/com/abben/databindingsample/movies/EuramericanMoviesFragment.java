package com.abben.databindingsample.movies;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.abben.databindingsample.BaseView;
import com.abben.databindingsample.R;
import com.abben.databindingsample.bean.Movie;
import com.abben.databindingsample.databinding.FragmentMoviesBinding;
import com.abben.databindingsample.listener.OnItemClickListener;
import com.abben.databindingsample.moviedetail.MovieDetailsActivity;
import com.abben.databindingsample.movies.adapter.CustomRecycleViewAdapter;
import com.abben.databindingsample.network.RetrofitHelper;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.abben.databindingsample.MainActivity.INTENT_MOVIE_FALG;

/**
 * Created by abben on 2017/5/3.
 */

public class EuramericanMoviesFragment extends Fragment implements BaseView, SwipeRefreshLayout.OnRefreshListener {

    private CustomRecycleViewAdapter customRecycleViewAdapter;
    private FragmentMoviesBinding fragmentMoviesBinding;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return initView(inflater, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showLoading();
        onRefresh();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.clear();
    }

    private View initView(LayoutInflater inflater, ViewGroup container) {
        fragmentMoviesBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_movies, container, false);

        fragmentMoviesBinding.fragmentSwipeRefresh.setOnRefreshListener(this);
        fragmentMoviesBinding.fragmentRecyclerview.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        DisplayMetrics dm = getResources().getDisplayMetrics();
        customRecycleViewAdapter = new CustomRecycleViewAdapter(getContext(),
                dm.widthPixels);
        customRecycleViewAdapter.setOnItemClikeListen(new OnItemClickListener<Movie>() {
            @Override
            public void onItemClick(Movie movie, View view) {
                Intent intent = new Intent(getActivity(), MovieDetailsActivity.class);
                intent.putExtra(INTENT_MOVIE_FALG, movie);

                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        getActivity(), view, getString(R.string.transitions_name));
                startActivity(intent, optionsCompat.toBundle());
            }
        });
        fragmentMoviesBinding.fragmentRecyclerview.setAdapter(customRecycleViewAdapter);

        return fragmentMoviesBinding.getRoot();
    }


    @Override
    public void showLoading() {
        fragmentMoviesBinding.fragmentSwipeRefresh.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        fragmentMoviesBinding.fragmentSwipeRefresh.setRefreshing(false);
    }

    @Override
    public void showTip(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }


    @Override
    public void onRefresh() {
        RetrofitHelper.getRetrofit().create(MoviesApi.class).fetchMovies("EuramericanMovies")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<Movie>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull ArrayList<Movie> movies) {
                        customRecycleViewAdapter.setMovies(movies);
                        hideLoading();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        showTip(e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
