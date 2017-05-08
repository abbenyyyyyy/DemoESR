package com.abben.yunziyuanesr.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abben.yunziyuanesr.CustomRecycleViewAdapter;
import com.abben.yunziyuanesr.MainActivity;
import com.abben.yunziyuanesr.R;
import com.abben.yunziyuanesr.bean.Movie;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/5/3.
 */
public class AllMoviesFragment extends Fragment {
    private ArrayList<Movie> movies;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first,container,false);

        initView(view);
        return view;
    }

    private void initView(View view){
        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.fragment_recyclerview);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        DisplayMetrics dm = getResources().getDisplayMetrics();
        CustomRecycleViewAdapter customRecycleViewAdapter = new CustomRecycleViewAdapter(getContext(),((MainActivity)getActivity()).getAllMovies()
        ,dm.widthPixels);
        mRecyclerView.setAdapter(customRecycleViewAdapter);
    }
}
