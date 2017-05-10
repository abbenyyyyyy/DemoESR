package com.abben.yunziyuanesr;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.abben.yunziyuanesr.bean.Movie;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by abben on 2017/5/3.
 */
public class CustomRecycleViewAdapter extends RecyclerView.Adapter<CustomRecycleViewAdapter.CustomVH>{

    private ArrayList<Movie> movies;
    private Context context;
    private int imageViewWidth;
    private OnItemClickListener mOnItemClickListener;
    private final int imagePaddingLeftOrRight = 10;

    public interface OnItemClickListener{
        void OnItemClick(View view, int position, Movie movie);
    }

    public CustomRecycleViewAdapter(Context context, int screenWidth){
        this.context = context;
        this.imageViewWidth = screenWidth / 2 - 20;
    }

    @Override
    public CustomVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycleview,parent,false);
        CustomVH customVH = new CustomVH(view);
        return customVH;
    }

    public void setMovies(ArrayList<Movie> movies){
        this.movies = movies;
        notifyDataSetChanged();
    }

    public void setOnItemClikeListen(OnItemClickListener onItemClikeListen){
        this.mOnItemClickListener = onItemClikeListen;
    }

    @Override
    public void onBindViewHolder(final CustomVH holder, int position) {
        if(mOnItemClickListener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = holder.getLayoutPosition();
                    mOnItemClickListener.OnItemClick(view,position,movies.get(position));
                }
            });
        }

        final ImageView myImageView = holder.movieCover;
        int imageViewHeight = imageViewWidth * Integer.parseInt(movies.get(position).getImageOfMovieHeight()) /
                Integer.parseInt(movies.get(position).getImageOfMovieWidth());
        LinearLayout.LayoutParams imageViewParams = new LinearLayout.LayoutParams(imageViewWidth,imageViewHeight);
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

        public CustomVH(View itemView) {
            super(itemView);
            movieCover = (ImageView) itemView.findViewById(R.id.movieCover);
            movieName = (TextView) itemView.findViewById(R.id.movieName);
        }
    }
}
