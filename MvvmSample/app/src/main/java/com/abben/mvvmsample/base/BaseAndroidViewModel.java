package com.abben.mvvmsample.base;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @author abben
 * on 2018/12/23
 */
public abstract class BaseAndroidViewModel extends AndroidViewModel {
    protected CompositeDisposable compositeDisposable;
    protected MutableLiveData<String> toastLiveData = new MutableLiveData<>();

    public BaseAndroidViewModel(@NonNull Application application) {
        super(application);
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }

    public MutableLiveData<String> getToastLiveData() {
        return toastLiveData;
    }
}
