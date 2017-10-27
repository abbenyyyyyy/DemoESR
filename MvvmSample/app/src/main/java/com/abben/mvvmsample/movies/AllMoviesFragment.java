package com.abben.mvvmsample.movies;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abben.mvvmsample.MoviesViewModel;
import com.abben.mvvmsample.R;
import com.abben.mvvmsample.bean.Movie;
import com.abben.mvvmsample.databinding.FragmentMoviesBinding;
import com.abben.mvvmsample.listener.OnItemClickListener;
import com.abben.mvvmsample.moviedetail.MovieDetailsActivity;
import com.abben.mvvmsample.movies.adapter.CustomRecycleViewAdapter;
import com.abben.mvvmsample.ui.common.RetryCallback;
import com.abben.mvvmsample.util.AutoClearedValue;
import com.abben.mvvmsample.vo.Resource;
import com.abben.mvvmsample.vo.TypeMovies;

import java.util.List;

import static com.abben.mvvmsample.MainActivity.INTENT_MOVIE_FALG;

/**
 * Created by abben on 2017/5/3.
 */
public class AllMoviesFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private CustomRecycleViewAdapter customRecycleViewAdapter;

    private AutoClearedValue<FragmentMoviesBinding> fragmentMoviesBinding;
    private MoviesViewModel moviesViewModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return initView(inflater, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MoviesViewModel.Factory factory = new MoviesViewModel.Factory(getActivity().getApplication(), TypeMovies.TYPE_ALL_MOVIES);
        moviesViewModel = ViewModelProviders.of(this, factory).get(MoviesViewModel.class);
        subscribeUi(moviesViewModel);
    }


    private View initView(final LayoutInflater inflater, ViewGroup container) {
        FragmentMoviesBinding dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_movies, container, false);
        dataBinding.setRetryCallback(new RetryCallback() {
            @Override
            public void retry() {
                Log.i("testLog", "retry: ");
                moviesViewModel.refreshMoviesData();
            }
        });
        fragmentMoviesBinding = new AutoClearedValue<>(this, dataBinding);

        fragmentMoviesBinding.getValue().fragmentSwipeRefresh.setOnRefreshListener(this);
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
        fragmentMoviesBinding.getValue().fragmentRecyclerview.setAdapter(customRecycleViewAdapter);

        return dataBinding.getRoot();
    }

    /**
     * 绑定ViewModel与View,
     * 当数据改变时通知View
     *
     * @param moviesViewModel ViewModel
     */
    private void subscribeUi(MoviesViewModel moviesViewModel) {
        moviesViewModel.getmObservableMovies().observe(this, new Observer<Resource<List<Movie>>>() {
            @Override
            public void onChanged(@Nullable Resource<List<Movie>> listResource) {
                fragmentMoviesBinding.getValue().setRepoResource(listResource);
                if (listResource != null && listResource.data != null) {
                    customRecycleViewAdapter.setMovies(listResource.data);
                }
                fragmentMoviesBinding.getValue().executePendingBindings();
            }
        });
        moviesViewModel.getmRefreshing().observe(this, new android.arch.lifecycle.Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean != null) {
                    fragmentMoviesBinding.getValue().fragmentSwipeRefresh.setRefreshing(aBoolean);
                }
            }
        });
    }

    @Override
    public void onRefresh() {
        moviesViewModel.refreshMoviesData();
    }
}
