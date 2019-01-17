package com.infun.mvvmsamplejetpack.binding

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

/**
 *  @author abben
 *  on 2019/1/17
 */
class BindingApapters {

    @BindingAdapter("visibleGone")
    fun View.showHide(show: Boolean) {
        visibility = if (show) VISIBLE else GONE
    }

    @BindingAdapter("imageUrl")
    fun ImageView.bingImage(url: String) {
        Glide.with(context).load(url).into(this)
    }

}