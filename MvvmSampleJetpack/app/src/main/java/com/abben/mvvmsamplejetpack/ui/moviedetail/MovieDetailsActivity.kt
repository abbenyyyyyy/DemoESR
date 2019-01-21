package com.abben.mvvmsamplejetpack.ui.moviedetail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.abben.mvvmsamplejetpack.MainActivity
import com.abben.mvvmsamplejetpack.R
import com.abben.mvvmsamplejetpack.bean.Movie
import com.abben.mvvmsamplejetpack.databinding.ActivityMovieDetailsBinding

/**
 *  @author abben
 *  on 2019/1/21
 */
class MovieDetailsActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var activityMovieDetailsBinding: ActivityMovieDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMovieDetailsBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_movie_details
        )
        val movie = intent.getSerializableExtra(MainActivity.INTENT_MOVIE_FALG) as Movie
        initView(movie, activityMovieDetailsBinding)
    }

    private fun initView(movie: Movie, activityMovieDetailsBinding: ActivityMovieDetailsBinding) {
        activityMovieDetailsBinding.appbarLayoutToolbar.setNavigationOnClickListener { finish() }

        activityMovieDetailsBinding.linkBaidupan.setOnClickListener(this)

        activityMovieDetailsBinding.movie = movie
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.link_baidupan -> {
                val intent = Intent()
                intent.action = "android.intent.action.VIEW"
                val contentUrl = Uri.parse(activityMovieDetailsBinding.movie?.baiduyun)
                intent.data = contentUrl
                startActivity(intent)
            }
        }
    }
}