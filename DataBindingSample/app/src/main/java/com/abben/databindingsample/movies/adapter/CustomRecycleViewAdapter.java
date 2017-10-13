package com.abben.databindingsample.movies.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.abben.databindingsample.R;
import com.abben.databindingsample.bean.Movie;
import com.abben.databindingsample.databinding.ItemRecycleviewBinding;
import com.abben.databindingsample.listener.OnItemClickListener;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import static com.bumptech.glide.request.RequestOptions.fitCenterTransform;

/**
 * Created by abben on 2017/5/3.
 */
public class CustomRecycleViewAdapter extends RecyclerView.Adapter<CustomRecycleViewAdapter.CustomVH> {

    private ArrayList<Movie> movies;
    private Context context;
    private int imageViewWidth;
    private OnItemClickListener<Movie> mOnItemClickListener;

    public CustomRecycleViewAdapter(Context context, int screenWidth) {
        this.context = context;
        this.imageViewWidth = screenWidth / 2 - 20;
    }

    @Override
    public CustomVH onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemRecycleviewBinding itemRecycleviewBinding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.item_recycleview, parent, false);
        return new CustomVH(itemRecycleviewBinding);
    }

    public void setMovies(ArrayList<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    public void setOnItemClikeListen(OnItemClickListener<Movie> onItemClikeListen) {
        this.mOnItemClickListener = onItemClikeListen;
    }

    @Override
    public void onBindViewHolder(final CustomVH holder, int position) {
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = holder.getAdapterPosition();
                    mOnItemClickListener.onItemClick(movies.get(position), view);
                }
            });
        }

        ItemRecycleviewBinding itemRecycleviewBinding = (ItemRecycleviewBinding) holder.getViewDataBinding();

        itemRecycleviewBinding.setMovie(movies.get(position));
//        itemRecycleviewBinding.executePendingBindings();


        ImageView myImageView = itemRecycleviewBinding.movieCover;
        int imageViewHeight = imageViewWidth * Integer.parseInt(movies.get(position).getImageOfMovieHeight()) /
                Integer.parseInt(movies.get(position).getImageOfMovieWidth());
        LinearLayout.LayoutParams imageViewParams = new LinearLayout.LayoutParams(imageViewWidth, imageViewHeight);
        final int imagePaddingLeftOrRight = 10;
        myImageView.setPadding(imagePaddingLeftOrRight, 10, imagePaddingLeftOrRight, 10);
        myImageView.setLayoutParams(imageViewParams);

        Glide.with(context)
                .load(movies.get(position).getImageOfMovie())
                .apply(fitCenterTransform())
                .into(myImageView);
    }

    @Override
    public int getItemCount() {
        return movies == null ? 0 : movies.size();
    }

    class CustomVH extends RecyclerView.ViewHolder {

        private ViewDataBinding viewDataBinding;

        private CustomVH(ViewDataBinding viewDataBinding) {
            super(viewDataBinding.getRoot());
            this.viewDataBinding = viewDataBinding;
        }

        public ViewDataBinding getViewDataBinding() {
            return viewDataBinding;
        }

    }
}
