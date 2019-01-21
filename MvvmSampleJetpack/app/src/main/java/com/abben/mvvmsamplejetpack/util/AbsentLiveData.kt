package com.abben.mvvmsamplejetpack.util

import androidx.lifecycle.LiveData

/**
 *  A LiveData class that has {@code null} value.
 *  @author abben
 *  on 2019/1/18
 */
class AbsentLiveData<T : Any?> private constructor() : LiveData<T>() {

    init {
        postValue(null)
    }

    companion object {
        fun <T> create(): LiveData<T> {
            return AbsentLiveData()
        }
    }

}