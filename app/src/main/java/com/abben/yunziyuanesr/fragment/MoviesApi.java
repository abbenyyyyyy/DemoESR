package com.abben.yunziyuanesr.fragment;

import com.abben.yunziyuanesr.bean.Movie;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Part;

/**
 * Created by Administrator on 2017/5/8.
 */

public interface MoviesApi {

    @GET("{type}.json")
    Observable<ArrayList<Movie>> fetchAllMovies(@Part String type);

}
