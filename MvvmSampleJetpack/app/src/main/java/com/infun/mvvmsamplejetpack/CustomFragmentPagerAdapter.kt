package com.infun.mvvmsamplejetpack

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**
 *  @author abben
 *  on 2019/1/17
 */
class CustomFragmentPagerAdapter(
    private val fm: FragmentManager, private val mFragments: List<Fragment>
) : FragmentPagerAdapter(fm) {

    private val pageTitles = arrayOf("所有电影", "欧美电影", "日韩电影", "国产电影")

    override fun getItem(position: Int): Fragment {
        return mFragments[position]
    }

    override fun getCount(): Int {
        return mFragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return pageTitles[position]
    }
}