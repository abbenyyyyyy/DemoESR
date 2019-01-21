package com.abben.mvvmsamplejetpack.base

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import io.reactivex.disposables.CompositeDisposable

/**
 *  @author abben
 *  on 2019/1/17
 */
open class BaseAndroidViewModel(baseApplication: Application) : AndroidViewModel(baseApplication) {
    var compositeDisposable: CompositeDisposable? = null
    val toastLiveData: MutableLiveData<String> = MutableLiveData()

    init {
        compositeDisposable = CompositeDisposable()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable?.clear()
    }
}