package com.abben.mvp_sample;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.abben.mvp_sample.bean.Movie;
import com.abben.mvp_sample.listener.OnItemClickListener;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by abben on 2017/5/3.
 */
public class CustomRecycleViewAdapter extends RecyclerView.Adapter<CustomRecycleViewAdapter.CustomVH>{

    private ArrayList<Movie> movies;
    private Context context;
    private int imageViewWidth;
    private OnItemClickListener<Movie> mOnItemClickListener;

    public CustomRecycleViewAdapter(Context context, int screenWidth){
        this.context = context;
        this.imageViewWidth = screenWidth / 2 - 20;
    }

    @Override
    public CustomVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycleview,parent,false);
        return new CustomVH(view);
    }

    public void setMovies(ArrayList<Movie> movies){
        this.movies = movies;
        notifyDataSetChanged();
    }

    public void setOnItemClikeListen(OnItemClickListener<Movie> onItemClikeListen){
        this.mOnItemClickListener = onItemClikeListen;
    }

    @Override
    public void onBindViewHolder(final CustomVH holder, int position) {
        if(mOnItemClickListener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = holder.getAdapterPosition();
                    mOnItemClickListener.onItemClick(movies.get(position),view);
                }
            });
        }

        final ImageView myImageView = holder.movieCover;
        int imageViewHeight = imageViewWidth * Integer.parseInt(movies.get(position).getImageOfMovieHeight()) /
                Integer.parseInt(movies.get(position).getImageOfMovieWidth());
        LinearLayout.LayoutParams imageViewParams = new LinearLayout.LayoutParams(imageViewWidth,imageViewHeight);
        final int imagePaddingLeftOrRight = 10;
        myImageView.setPadding(imagePaddingLeftOrRight,10,imagePaddingLeftOrRight,10);
        myImageView.setLayoutParams(imageViewParams);

        holder.movieName.setText(movies.get(position).getName());

        Glide.with(context)
                .load(movies.get(position).getImageOfMovie())
                .fitCenter()
                .crossFade()
                .into(myImageView);
    }

    @Override
    public int getItemCount() {
        return movies == null? 0 : movies.size();
    }

    class CustomVH extends RecyclerView.ViewHolder{
        private ImageView movieCover;
        private TextView movieName;

        private CustomVH(View itemView) {
            super(itemView);
            movieCover = itemView.findViewById(R.id.movieCover);
            movieName = itemView.findViewById(R.id.movieName);
        }
    }
}
