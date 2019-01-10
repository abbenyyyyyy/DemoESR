package com.abben.mvvmsample.binding;

//import android.databinding.BindingAdapter;
import androidx.databinding.BindingAdapter;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by abben on 2017/10/27.
 */
public class BindingApapters {

    @BindingAdapter("visibleGone")
    public static void showHide(View view, boolean show) {
        view.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter("imageUrl")
    public static void bingImage(ImageView imageView, String url){
        Glide.with(imageView.getContext()).load(url).into(imageView);
    }

}
