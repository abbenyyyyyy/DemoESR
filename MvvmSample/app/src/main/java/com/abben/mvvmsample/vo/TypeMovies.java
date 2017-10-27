package com.abben.mvvmsample.vo;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by abben on 2017/10/27.
 */

public class TypeMovies {

    public final static String TYPE_ALL_MOVIES = "Movies";
    public final static String TYPE_CHINESE_MOVIES = "ChineseMovies";
    public final static String TYPE_EURAMERICAN_MOVIES = "EuramericanMovies";
    public final static String TYPE_JANPAN_AND_KOREA_MOVIES = "JanpanAndKoreaMovies";

    @StringDef({TYPE_ALL_MOVIES, TYPE_CHINESE_MOVIES,TYPE_EURAMERICAN_MOVIES,TYPE_JANPAN_AND_KOREA_MOVIES})
    @Retention(RetentionPolicy.SOURCE)
    public @interface TypeMoviesAnnotation {
    }
}
