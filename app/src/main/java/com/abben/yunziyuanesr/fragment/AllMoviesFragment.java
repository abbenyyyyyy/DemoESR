package com.abben.yunziyuanesr.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.abben.yunziyuanesr.CustomRecycleViewAdapter;
import com.abben.yunziyuanesr.MovieDetailsActivity;
import com.abben.yunziyuanesr.R;
import com.abben.yunziyuanesr.bean.Movie;
import com.abben.yunziyuanesr.contract.AllMoviesContract;

import java.util.ArrayList;

/**
 * Created by abben on 2017/5/3.
 */
public class AllMoviesFragment extends Fragment implements AllMoviesContract.View,SwipeRefreshLayout.OnRefreshListener{
    private AllMoviesContract.Presenter mPresenter;
    private CustomRecycleViewAdapter customRecycleViewAdapter;
    private SwipeRefreshLayout mSwipeRefresh;

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
    public void onPause() {
        super.onPause();
        mPresenter.clearCompositeDisposable();
    }

    private View initView(LayoutInflater inflater, ViewGroup container){
        View view = inflater.inflate(R.layout.fragment_first,container,false);
        mSwipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.fragment_swipe_refresh);
        mSwipeRefresh.setOnRefreshListener(this);
        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.fragment_recyclerview);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        DisplayMetrics dm = getResources().getDisplayMetrics();
        customRecycleViewAdapter = new CustomRecycleViewAdapter(getContext(),
        dm.widthPixels);
        customRecycleViewAdapter.setOnItemClikeListen(new CustomRecycleViewAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int position, Movie movie) {
                Intent intent = new Intent(getActivity(), MovieDetailsActivity.class);
                startActivity(intent);
            }
        });
        mRecyclerView.setAdapter(customRecycleViewAdapter);

        return view;
    }

    @Override
    public void setPresenter(AllMoviesContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void showLoading() {
        mSwipeRefresh.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        mSwipeRefresh.setRefreshing(false);
    }

    @Override
    public void showTip(String msg) {
        Toast.makeText(getActivity(),msg,Toast.LENGTH_LONG).show();
    }

    @Override
    public void showAllMovies(ArrayList<Movie> allMovies) {
        customRecycleViewAdapter.setMovies(allMovies);
        hideLoading();
    }

    @Override
    public void onRefresh() {
        mPresenter.subscribeAllMovies();
    }
}
