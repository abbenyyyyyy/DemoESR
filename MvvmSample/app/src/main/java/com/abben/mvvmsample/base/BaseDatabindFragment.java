package com.abben.mvvmsample.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @author abben
 * on 2018/7/30
 */
public abstract class BaseDatabindFragment<T extends ViewDataBinding> extends Fragment implements View.OnClickListener {
    protected T viewBinding;
    protected CompositeDisposable compositeDisposable;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        compositeDisposable = new CompositeDisposable();
        viewBinding = DataBindingUtil.inflate(inflater, getContentViewId(), container, false);
        return viewBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initListener();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }
    }

    protected abstract int getContentViewId();

    protected abstract void initView();

    protected abstract void initListener();
}
