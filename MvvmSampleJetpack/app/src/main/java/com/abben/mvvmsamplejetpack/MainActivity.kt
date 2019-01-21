package com.abben.mvvmsamplejetpack

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.abben.mvvmsamplejetpack.databinding.ActivityMainBinding
import com.abben.mvvmsamplejetpack.ui.movies.MoviesFragment
import com.abben.mvvmsamplejetpack.vo.TYPE_ALL_MOVIES_ZH
import com.abben.mvvmsamplejetpack.vo.TYPE_CHINESE_MOVIES_ZH
import com.abben.mvvmsamplejetpack.vo.TYPE_EURAMERICAN_MOVIES_ZH
import com.abben.mvvmsamplejetpack.vo.TYPE_JANPAN_AND_KOREA_MOVIES_ZH
import com.google.android.material.tabs.TabLayout

/**
 *  @author abben
 *  on 2019/1/17
 */
class MainActivity : AppCompatActivity() {

    companion object {
        const val INTENT_MOVIE_FALG = "INTENT_MOVIE_FALG"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityMainBinding: ActivityMainBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_main
        )
        initView(activityMainBinding)
    }

    private fun initView(activityMainBinding: ActivityMainBinding) {
        val fragments = listOf<Fragment>(
            MoviesFragment.create(TYPE_ALL_MOVIES_ZH),
            MoviesFragment.create(TYPE_EURAMERICAN_MOVIES_ZH),
            MoviesFragment.create(TYPE_JANPAN_AND_KOREA_MOVIES_ZH),
            MoviesFragment.create(TYPE_CHINESE_MOVIES_ZH)
        )

        val customFragmentPagerAdapter = CustomFragmentPagerAdapter(supportFragmentManager, fragments)
        activityMainBinding.mainViewpager.adapter = customFragmentPagerAdapter

        activityMainBinding.slidingTabs.setupWithViewPager(activityMainBinding.mainViewpager)
        activityMainBinding.slidingTabs.tabMode = TabLayout.MODE_FIXED
        activityMainBinding.slidingTabs.visibility = View.VISIBLE
    }
}
