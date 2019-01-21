package com.abben.mvvmsamplejetpack.ui.movies

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.abben.mvvmsamplejetpack.MainActivity.Companion.INTENT_MOVIE_FALG
import com.abben.mvvmsamplejetpack.R
import com.abben.mvvmsamplejetpack.base.BaseDatabindFragment
import com.abben.mvvmsamplejetpack.bean.Movie
import com.abben.mvvmsamplejetpack.common.OnItemClickListener
import com.abben.mvvmsamplejetpack.common.RetryCallback
import com.abben.mvvmsamplejetpack.databinding.FragmentMoviesBinding
import com.abben.mvvmsamplejetpack.ui.moviedetail.MovieDetailsActivity
import com.abben.mvvmsamplejetpack.ui.movies.adapter.MoviesRecycleViewAdapter
import com.abben.mvvmsamplejetpack.vo.TypeMovies

/**
 *  @author abben
 *  on 2019/1/21
 */
class MoviesFragment : BaseDatabindFragment<FragmentMoviesBinding>()
    , SwipeRefreshLayout.OnRefreshListener {

    private var moviesRecycleViewAdapter: MoviesRecycleViewAdapter? = null
    private var moviesViewModel: MoviesViewModel? = null

    companion object {
        private const val MOVE_TYPE_KEY = "moveType"

        fun create(@TypeMovies moveType: String): MoviesFragment {
            val moviesFragment = MoviesFragment()
            val bundle = Bundle()
            bundle.putString(MOVE_TYPE_KEY, moveType)
            moviesFragment.arguments = bundle
            return moviesFragment
        }
    }

    override fun getContentViewId(): Int {
        return R.layout.fragment_movies
    }

    override fun initView() {
        viewBinding.fragmentSwipeRefresh.setOnRefreshListener(this)
        val dm = resources.displayMetrics
        moviesRecycleViewAdapter = MoviesRecycleViewAdapter(dm.widthPixels / 2 - 20)
        moviesRecycleViewAdapter!!.mOnItemClickListener = object : OnItemClickListener<Movie> {
            override fun onItemClick(t: Movie, view: View) {
                val intent = Intent(activity, MovieDetailsActivity::class.java)
                intent.putExtra(INTENT_MOVIE_FALG, t)
                val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    activity!!, view, getString(R.string.transitions_name)
                )
                startActivity(intent, optionsCompat.toBundle())
            }
        }


        viewBinding.fragmentRecyclerview.adapter = moviesRecycleViewAdapter

        moviesViewModel = ViewModelProviders.of(this).get(MoviesViewModel::class.java)
        viewBinding.retryCallback = object : RetryCallback {
            override fun retry() {
                moviesViewModel?.retry()
            }
        }
        moviesViewModel?.moveType?.value = arguments?.getString(MOVE_TYPE_KEY)
        subscribeUi(moviesViewModel)
    }

    override fun initListener() {
    }

    override fun onClick(p0: View?) {
    }

    override fun onRefresh() {
        moviesViewModel?.retry()
    }

    /**
     * 绑定ViewModel与View,
     * 当数据改变时通知View
     *
     * @param moviesViewModel ViewModel
     */
    private fun subscribeUi(moviesViewModel: MoviesViewModel?) {
        moviesViewModel?.mObservableMovies?.observe(this, Observer {
            viewBinding.repoResource = it
            it.data?.let { movies ->
                moviesRecycleViewAdapter?.setMovies(movies)
            }
            viewBinding.executePendingBindings()
        })
        moviesViewModel?.getmRefreshing()?.observe(this, Observer {
            viewBinding.fragmentSwipeRefresh.isRefreshing = it
        })
    }
}