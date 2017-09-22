package com.abben.mvp_sample.moviedetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.abben.mvp_sample.R;
import com.abben.mvp_sample.bean.Movie;
import com.bumptech.glide.Glide;

import static com.abben.mvp_sample.MainActivity.INTENT_MOVIE_FALG;

/**
 * Created by abben on 2017/5/10.
 */
public class MovieDetailsActivity extends AppCompatActivity implements MovieDetailsView,View.OnClickListener{

    private MovieDetailsPresenter mMovieDetailsPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        Movie movie = (Movie) getIntent().getSerializableExtra(INTENT_MOVIE_FALG);
        mMovieDetailsPresenter = new MovieDetailsPresenter(this,movie);
        mMovieDetailsPresenter.startShow();
    }

    @Override
    public void setPaesenter(MovieDetailsPresenter paesenter) {
        mMovieDetailsPresenter = paesenter;
    }


    @Override
    public void showMovieDetails(Movie movie) {
        Toolbar appbar_layout_toolbar = findViewById(R.id.appbar_layout_toolbar);
        appbar_layout_toolbar.setTitle(movie.getName());
        appbar_layout_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ImageView image_of_movie = findViewById(R.id.image_of_movie);
        Glide.with(this).load(movie.getImageOfMovie()).into(image_of_movie);
        ImageView printscreen = findViewById(R.id.printscreen);
        Glide.with(this).load(movie.getPrintscreen()).into(printscreen);

        TextView summary_of_movie = findViewById(R.id.summary_of_movie);
        summary_of_movie.setText(movie.getSummaryOfMovie());
        TextView yun_password = findViewById(R.id.yun_password);
        yun_password.setText(movie.getYunPassword());

        FloatingActionButton link_baidupan = findViewById(R.id.link_baidupan);
        link_baidupan.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.link_baidupan:
                mMovieDetailsPresenter.linkToBaiduPan(this);
                break;
            default:
                break;
        }
    }
}
