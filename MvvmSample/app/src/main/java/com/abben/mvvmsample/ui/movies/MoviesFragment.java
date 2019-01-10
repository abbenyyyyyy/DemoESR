package com.abben.mvvmsample.ui.movies;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

import com.abben.mvvmsample.R;
import com.abben.mvvmsample.base.BaseDatabindFragment;
import com.abben.mvvmsample.databinding.FragmentMoviesBinding;
import com.abben.mvvmsample.ui.moviedetail.MovieDetailsActivity;
import com.abben.mvvmsample.ui.movies.adapter.MoviesRecycleViewAdapter;
import com.abben.mvvmsample.vo.TypeMovies;

import androidx.core.app.ActivityOptionsCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import static com.abben.mvvmsample.MainActivity.INTENT_MOVIE_FALG;

/**
 * Created by abben on 2017/11/20.
 */
public class MoviesFragment extends BaseDatabindFragment<FragmentMoviesBinding> implements SwipeRefreshLayout.OnRefreshListener {
    private static String MOVE_TYPE_KEY = "moveType";

    private MoviesRecycleViewAdapter moviesRecycleViewAdapter;
    private MoviesViewModel moviesViewModel;

    public static MoviesFragment create(@TypeMovies.TypeMoviesAnnotation String moveType) {
        MoviesFragment moviesFragment = new MoviesFragment();
        Bundle bundle = new Bundle();
        bundle.putString(MOVE_TYPE_KEY, moveType);
        moviesFragment.setArguments(bundle);
        return moviesFragment;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_movies;
    }

    @Override
    protected void initView() {
        viewBinding.fragmentSwipeRefresh.setOnRefreshListener(this);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        moviesRecycleViewAdapter = new MoviesRecycleViewAdapter(getContext(), dm.widthPixels);
        moviesRecycleViewAdapter.setOnItemClikeListen((movie, view) -> {
            Intent intent = new Intent(getActivity(), MovieDetailsActivity.class);
            intent.putExtra(INTENT_MOVIE_FALG, movie);
            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    getActivity(), view, getString(R.string.transitions_name));
            startActivity(intent, optionsCompat.toBundle());
        });
        viewBinding.fragmentRecyclerview.setAdapter(moviesRecycleViewAdapter);

        moviesViewModel = ViewModelProviders.of(this).get(MoviesViewModel.class);
        viewBinding.setRetryCallback(() ->
                moviesViewModel.retry()
        );
        moviesViewModel.setMoveType(getArguments().getString(MOVE_TYPE_KEY));
        subscribeUi(moviesViewModel);
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void onClick(View view) {

    }

    /**
     * 绑定ViewModel与View,
     * 当数据改变时通知View
     *
     * @param moviesViewModel ViewModel
     */
    private void subscribeUi(MoviesViewModel moviesViewModel) {
        moviesViewModel.getmObservableMovies().observe(this, listResource -> {
            viewBinding.setRepoResource(listResource);
            if (listResource != null && listResource.data != null) {
                moviesRecycleViewAdapter.setMovies(listResource.data);
            }
            viewBinding.executePendingBindings();
        });
        moviesViewModel.getmRefreshing().observe(this, aBoolean -> {
            if (aBoolean != null) {
                viewBinding.fragmentSwipeRefresh.setRefreshing(aBoolean);
            }
        });
    }

    @Override
    public void onRefresh() {
        moviesViewModel.retry();
    }

}
