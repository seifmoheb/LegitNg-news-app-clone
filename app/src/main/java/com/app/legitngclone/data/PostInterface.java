package com.app.legitngclone.data;

import com.app.legitngclone.pojo.Articles;
import com.app.legitngclone.ui.home.HomeFragment;
import com.app.legitngclone.ui.home.onef.oneFragment;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PostInterface {

    @GET("top-headlines?country=ng&category=general&apiKey=1dcf11bb58394a36b3a4b9bac0fd3e95")
    public Call<Articles> getGeneralPosts(
            @Query("page") int page,
            @Query("limit") int limit
    );

    @GET("top-headlines?country=ng&category=business&apiKey=1dcf11bb58394a36b3a4b9bac0fd3e95")
    public Call<Articles> getBusinessPosts(
            @Query("page") int page,
            @Query("limit") int limit
    );

    @GET("top-headlines?country=ng&category=politics&apiKey=1dcf11bb58394a36b3a4b9bac0fd3e95")
    public Call<Articles> getPoliticsPosts(
            @Query("page") int page,
            @Query("limit") int limit
    );

    @GET("top-headlines?country=ng&category=sports&apiKey=1dcf11bb58394a36b3a4b9bac0fd3e95")
    public Call<Articles> getSportsPosts(
            @Query("page") int page,
            @Query("limit") int limit
    );

    @GET("top-headlines?country=ng&category=health&apiKey=1dcf11bb58394a36b3a4b9bac0fd3e95")
    public Call<Articles> getHealthPosts(
            @Query("page") int page,
            @Query("limit") int limit
    );

    @GET("top-headlines?country=ng&category=entertainment&apiKey=1dcf11bb58394a36b3a4b9bac0fd3e95")
    public Call<Articles> getEntertainmentPosts(
            @Query("page") int page,
            @Query("limit") int limit
    );

    @GET("top-headlines?country=ng&category=science&apiKey=1dcf11bb58394a36b3a4b9bac0fd3e95")
    public Call<Articles> getSciencePosts(
            @Query("page") int page,
            @Query("limit") int limit
    );

    @GET("top-headlines?country=ng&category=technology&apiKey=1dcf11bb58394a36b3a4b9bac0fd3e95")
    public Call<Articles> getTechnologyPosts(
            @Query("page") int page,
            @Query("limit") int limit
    );

    @GET("top-headlines?language=en&apiKey=1dcf11bb58394a36b3a4b9bac0fd3e95")
    public Call<Articles> getGlobalPosts(
            @Query("page") int page,
            @Query("limit") int limit
    );



}
