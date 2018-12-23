package com.abben.mvvmsample.ui.movies.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.abben.mvvmsample.R;
import com.abben.mvvmsample.bean.Movie;
import com.abben.mvvmsample.common.OnItemClickListener;
import com.abben.mvvmsample.databinding.ItemRecycleviewBinding;
import com.bumptech.glide.Glide;

import java.util.List;

import static com.bumptech.glide.request.RequestOptions.fitCenterTransform;

/**
 * Created by abben on 2017/5/3.
 */
public class MoviesRecycleViewAdapter extends RecyclerView.Adapter<MoviesRecycleViewAdapter.CustomVH> {

    private List<Movie> movies;
    private Context context;
    private int imageViewWidth;
    private OnItemClickListener<Movie> mOnItemClickListener;

    public MoviesRecycleViewAdapter(Context context, int screenWidth) {
        this.context = context;
        this.imageViewWidth = screenWidth / 2 - 20;
    }

    @Override
    public CustomVH onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemRecycleviewBinding itemRecycleviewBinding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.item_recycleview, parent, false);
        return new CustomVH(itemRecycleviewBinding);
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    public void setOnItemClikeListen(OnItemClickListener<Movie> onItemClikeListen) {
        this.mOnItemClickListener = onItemClikeListen;
    }

    @Override
    public void onBindViewHolder(final CustomVH holder, int position) {
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(view ->
                    mOnItemClickListener.onItemClick(movies.get(holder.getLayoutPosition()), view)
            );
        }

        holder.binding.setMovie(movies.get(position));

        ImageView myImageView = holder.binding.movieCover;
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

        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return movies == null ? 0 : movies.size();
    }

    class CustomVH extends RecyclerView.ViewHolder {

        private ItemRecycleviewBinding binding;

        private CustomVH(ItemRecycleviewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }
}
