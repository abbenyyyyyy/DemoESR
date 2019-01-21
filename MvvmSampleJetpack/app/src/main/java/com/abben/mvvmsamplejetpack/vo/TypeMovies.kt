package com.abben.mvvmsamplejetpack.vo

import androidx.annotation.StringDef

/**
 *  @author abben
 *  on 2019/1/17
 */
const val TYPE_ALL_MOVIES_ZH: String = "所有电影"
const val TYPE_CHINESE_MOVIES_ZH: String = "国产电影"
const val TYPE_EURAMERICAN_MOVIES_ZH: String = "欧美电影"
const val TYPE_JANPAN_AND_KOREA_MOVIES_ZH: String = "日韩电影"


@StringDef(
    TYPE_ALL_MOVIES_ZH,
    TYPE_CHINESE_MOVIES_ZH,
    TYPE_EURAMERICAN_MOVIES_ZH,
    TYPE_JANPAN_AND_KOREA_MOVIES_ZH
)
@Retention(AnnotationRetention.SOURCE)
annotation class TypeMovies {
    companion object {
        const val API_TYPE_ALL_MOVIES: String = "Movies"
        const val API_TYPE_CHINESE_MOVIES: String = "ChineseMovies"
        const val API_TYPE_EURAMERICAN_MOVIES: String = "EuramericanMovies"
        const val API_TYPE_JANPAN_AND_KOREA_MOVIES: String = "JanpanAndKoreaMovies"
    }
}