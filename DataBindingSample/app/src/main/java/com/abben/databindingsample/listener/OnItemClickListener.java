package com.abben.databindingsample.listener;

import android.view.View;

/**
 * Created by abben on 2017/9/5.
 */

public interface OnItemClickListener<T> {

    void onItemClick(T t, View view);

}
