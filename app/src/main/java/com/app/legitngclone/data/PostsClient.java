package com.app.legitngclone.data;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.app.legitngclone.pojo.Articles;
import com.app.legitngclone.ui.main.MainActivity;
import com.ncornette.cache.OkCacheControl;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class PostsClient {
    //http://newsapi.org/v2/
    private static final String BASE_URL = "https:///newsapi.org/v2/";
    private PostInterface postInterface;
    private static PostsClient INSTANCE;
    int cacheSize = 10 * 1024 * 1024;

    public PostsClient() {
        Cache cache = new Cache(MainActivity.file,cacheSize);
        OkCacheControl.NetworkMonitor networkMonitor = new OkCacheControl.NetworkMonitor() {
            @Override
            public boolean isOnline() {
                return MainActivity.check;
            }
        };
        OkHttpClient okHttpClient = OkCacheControl.on(new OkHttpClient.Builder())
                .overrideServerCachePolicy(1, TimeUnit.MINUTES)
                .forceCacheWhenOffline(networkMonitor)
                .apply()
                .cache(cache)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        postInterface = retrofit.create(PostInterface.class);
    }

    public static PostsClient getINSTANCE() {
        if(null == INSTANCE){
            INSTANCE = new PostsClient();
        }
        return INSTANCE;
    }
    public Call<Articles> getGeneralPosts(int page, int limit){
        return postInterface.getGeneralPosts(page,limit);
    }
    public Call<Articles> getPoliticsPosts(int page, int limit){
        return postInterface.getPoliticsPosts( page, limit);
    }

    public Call<Articles> getSportsPosts(int page, int limit){
        return postInterface.getSportsPosts( page, limit);
    }

    public Call<Articles> getHealthPosts(int page, int limit){
        return postInterface.getHealthPosts( page, limit);
    }

    public Call<Articles> getEntertainmentPosts(int page, int limit){
        return postInterface.getEntertainmentPosts( page, limit);
    }

    public Call<Articles> getSciencePosts(int page, int limit){
        return postInterface.getSciencePosts( page, limit);
    }

    public Call<Articles> getTechnologyPosts(int page, int limit){
        return postInterface.getTechnologyPosts( page, limit);
    }

    public Call<Articles> getGlobalPosts(int page, int limit){
        return postInterface.getGlobalPosts( page, limit);
    }

    public Call<Articles> getBusinessPosts(int page, int limit){
        return postInterface.getBusinessPosts( page, limit);
    }

}
