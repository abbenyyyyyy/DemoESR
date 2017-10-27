package com.abben.mvvmsample.binding;

import android.databinding.BindingAdapter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

/**
 * Created by abben on 2017/10/27.
 */

public class BindingApapters {

    @BindingAdapter("visibleGone")
    public static void showHide(View view, boolean show) {
        view.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter("refreshing")
    public static void isRefreshing(SwipeRefreshLayout swipeRefreshLayout, boolean refreshing){
        swipeRefreshLayout.setRefreshing(refreshing);
    }
}
