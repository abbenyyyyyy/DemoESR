package com.abben.mvvmsample.util;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

/**
 * 一个在View中的值的持有者，如果View被回收，那么解除值的持有
 * Created by abben on 2017/10/24.
 */
public class AutoClearedValue<T> {
    private T value;

    public AutoClearedValue(final Fragment fragment, T value) {
        final FragmentManager fragmentManager = fragment.getFragmentManager();
        fragmentManager.registerFragmentLifecycleCallbacks(new FragmentManager.FragmentLifecycleCallbacks() {
            @Override
            public void onFragmentDestroyed(FragmentManager fm, Fragment f) {
                if (f == fragment) {
                    AutoClearedValue.this.value = null;
                    fragmentManager.unregisterFragmentLifecycleCallbacks(this);
                }
            }
        },false);
        this.value = value;
    }

    public T getValue(){
        return value;
    }
}
