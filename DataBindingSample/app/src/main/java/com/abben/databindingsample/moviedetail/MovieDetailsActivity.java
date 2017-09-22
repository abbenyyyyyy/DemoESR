package com.abben.databindingsample.moviedetail;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.abben.databindingsample.R;
import com.abben.databindingsample.bean.Movie;
import com.abben.databindingsample.databinding.ActivityMovieDetailsBinding;

import static com.abben.databindingsample.MainActivity.INTENT_MOVIE_FALG;

/**
 * Created by abben on 2017/5/10.
 */
public class MovieDetailsActivity extends AppCompatActivity implements View.OnClickListener{
    private ActivityMovieDetailsBinding activityMovieDetailsBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        Movie movie = (Movie) getIntent().getSerializableExtra(INTENT_MOVIE_FALG);

        initView(movie);
    }

    private void initView(Movie movie) {

        activityMovieDetailsBinding = DataBindingUtil.setContentView(this,R.layout.activity_movie_details);
        activityMovieDetailsBinding.appbarLayoutToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        activityMovieDetailsBinding.linkBaidupan.setOnClickListener(this);

        activityMovieDetailsBinding.setMovie(movie);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.link_baidupan:
                Intent intent= new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse(activityMovieDetailsBinding.getMovie().getBaiduyun());
                intent.setData(content_url);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
