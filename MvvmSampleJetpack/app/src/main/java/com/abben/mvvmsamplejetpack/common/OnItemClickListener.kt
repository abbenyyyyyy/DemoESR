package com.abben.mvvmsamplejetpack.common

import android.view.View

/**
 *  @author abben
 *  on 2019/1/18
 */
interface OnItemClickListener<T> {
    fun onItemClick(t: T, view: View)
}