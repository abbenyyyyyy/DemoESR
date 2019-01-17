package com.infun.mvvmsamplejetpack.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable

/**
 *  @author abben
 *  on 2019/1/17
 */
abstract class BaseDatabindFragment<T : ViewDataBinding> : Fragment(), View.OnClickListener {
    protected lateinit var viewBinding: T
    protected var compositeDisposable: CompositeDisposable? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        super.onCreateView(inflater, container, savedInstanceState)
        compositeDisposable = CompositeDisposable()
        viewBinding = DataBindingUtil.inflate(inflater, getContentViewId(), container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initListener()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable?.clear()
    }

    protected abstract fun getContentViewId(): Int

    protected abstract fun initView()

    protected abstract fun initListener()
}