package com.abben.mvvmsample.util;

/**
 * Created by abben on 2017/10/24.
 */

public class Objects {
    public static boolean equals(Object o1, Object o2) {
        if (o1 == null) {
            return o2 == null;
        }
        return o2 != null && o1.equals(o2);
    }
}
