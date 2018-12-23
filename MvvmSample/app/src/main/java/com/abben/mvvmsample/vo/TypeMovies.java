package com.abben.mvvmsample.vo;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by abben on 2017/10/27.
 */
public class TypeMovies {

    public final static String API_TYPE_ALL_MOVIES = "Movies";
    public final static String API_TYPE_CHINESE_MOVIES = "ChineseMovies";
    public final static String API_TYPE_EURAMERICAN_MOVIES = "EuramericanMovies";
    public final static String API_TYPE_JANPAN_AND_KOREA_MOVIES = "JanpanAndKoreaMovies";

    public final static String TYPE_ALL_MOVIES_ZH = "所有电影";
    public final static String TYPE_CHINESE_MOVIES_ZH = "国产电影";
    public final static String TYPE_EURAMERICAN_MOVIES_ZH = "欧美电影";
    public final static String TYPE_JANPAN_AND_KOREA_MOVIES_ZH = "日韩电影";

    @StringDef({TYPE_ALL_MOVIES_ZH, TYPE_CHINESE_MOVIES_ZH, TYPE_EURAMERICAN_MOVIES_ZH, TYPE_JANPAN_AND_KOREA_MOVIES_ZH})
    @Retention(RetentionPolicy.SOURCE)
    public @interface TypeMoviesAnnotation {
    }
}
