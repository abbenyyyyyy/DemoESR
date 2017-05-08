package com.abben.yunziyuanesr;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.abben.yunziyuanesr.bean.Movie;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/5/3.
 */
public class CustomRecycleViewAdapter extends RecyclerView.Adapter<CustomRecycleViewAdapter.CustomVH>{

    private ArrayList<Movie> movies;
    private Context context;
    private int imageViewWidth;



    public CustomRecycleViewAdapter(Context context, ArrayList<Movie> movies, int screenWidth){
        this.context = context;
        this.movies = movies;
        this.imageViewWidth = screenWidth / 2 - 20;
    }

    @Override
    public CustomVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycleview,parent,false);
        CustomVH customVH = new CustomVH(view);
        return customVH;
    }

    @Override
    public void onBindViewHolder(CustomVH holder, int position) {
        final ImageView myImageView = holder.movieCover;
        int imageViewHeight = imageViewWidth * Integer.parseInt(movies.get(position).getImageOfMovieHeight()) /
                Integer.parseInt(movies.get(position).getImageOfMovieWidth());
        LinearLayout.LayoutParams imageViewParams = new LinearLayout.LayoutParams(imageViewWidth,imageViewHeight);
        myImageView.setPadding(10,10,10,10);
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
        return movies.size();
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
