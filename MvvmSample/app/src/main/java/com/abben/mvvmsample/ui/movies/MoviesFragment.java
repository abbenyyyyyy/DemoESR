package com.abben.mvvmsample.ui.movies;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abben.mvvmsample.R;
import com.abben.mvvmsample.bean.Movie;
import com.abben.mvvmsample.databinding.FragmentMoviesBinding;
import com.abben.mvvmsample.common.OnItemClickListener;
import com.abben.mvvmsample.ui.moviedetail.MovieDetailsActivity;
import com.abben.mvvmsample.ui.movies.adapter.MoviesRecycleViewAdapter;
import com.abben.mvvmsample.common.RetryCallback;
import com.abben.mvvmsample.util.AutoClearedValue;
import com.abben.mvvmsample.vo.Resource;
import com.abben.mvvmsample.vo.TypeMovies;

import java.util.List;

import static com.abben.mvvmsample.MainActivity.INTENT_MOVIE_FALG;

/**
 * Created by abben on 2017/11/20.
 */
public class MoviesFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private static String MOVE_TYPE_KEY = "moveType";

    private MoviesRecycleViewAdapter moviesRecycleViewAdapter;
    private AutoClearedValue<FragmentMoviesBinding> fragmentMoviesBinding;
    private MoviesViewModel moviesViewModel;

    public static MoviesFragment create(@TypeMovies.TypeMoviesAnnotation String moveType){
        MoviesFragment moviesFragment = new MoviesFragment();
        Bundle bundle = new Bundle();
        bundle.putString(MOVE_TYPE_KEY,moveType);
        moviesFragment.setArguments(bundle);
        return moviesFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return initView(inflater, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        moviesViewModel = ViewModelProviders.of(this).get(MoviesViewModel.class);
        fragmentMoviesBinding.getValue().setRetryCallback(new RetryCallback() {
            @Override
            public void retry() {
                moviesViewModel.retry();
            }
        });
        moviesViewModel.setMoveType(getArguments().getString(MOVE_TYPE_KEY));
        subscribeUi(moviesViewModel);
    }

    private View initView(final LayoutInflater inflater, ViewGroup container) {
        FragmentMoviesBinding dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_movies, container, false);

        fragmentMoviesBinding = new AutoClearedValue<>(this, dataBinding);

        fragmentMoviesBinding.getValue().fragmentSwipeRefresh.setOnRefreshListener(this);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        moviesRecycleViewAdapter = new MoviesRecycleViewAdapter(getContext(), dm.widthPixels);
        moviesRecycleViewAdapter.setOnItemClikeListen(new OnItemClickListener<Movie>() {
            @Override
            public void onItemClick(Movie movie, View view) {
                Intent intent = new Intent(getActivity(), MovieDetailsActivity.class);
                intent.putExtra(INTENT_MOVIE_FALG, movie);

                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        getActivity(), view, getString(R.string.transitions_name));
                startActivity(intent, optionsCompat.toBundle());
            }
        });
        fragmentMoviesBinding.getValue().fragmentRecyclerview.setAdapter(moviesRecycleViewAdapter);

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
                    moviesRecycleViewAdapter.setMovies(listResource.data);
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
        moviesViewModel.retry();
    }
}
