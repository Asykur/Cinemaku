package com.asykurkhamid.submission.Http;

import com.asykurkhamid.submission.Pojo.DataMovieJSON;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Services {

    @GET("search/movie?")
    Call<APIResponse<List<DataMovieJSON>>> getSearchMovie(@Query("api_key") String api,
                                                          @Query("language") String language,
                                                          @Query("query") String query,
                                                          @Query("page") String page,
                                                          @Query("include_adult") String adult);
    @GET("movie/now_playing?")
    Call<APIResponse<List<DataMovieJSON>>> getNowPlaying(@Query("api_key") String api,
                                                         @Query("page") String page,
                                                         @Query("language") String language);
    @GET("movie/upcoming?")
    Call<APIResponse<List<DataMovieJSON>>> getUpComing(@Query("api_key") String api,
                                                       @Query("page") String page,
                                                       @Query("language") String language);

}
